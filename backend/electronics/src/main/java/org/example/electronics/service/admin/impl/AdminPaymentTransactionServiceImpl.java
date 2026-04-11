package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.electronics.config.payment.MomoConfig;
import org.example.electronics.config.payment.VNPayConfig;
import org.example.electronics.dto.response.admin.AdminPaymentTransactionResponseDTO;
import org.example.electronics.entity.PaymentTransactionEntity;
import org.example.electronics.entity.ReturnRequestEntity;
import org.example.electronics.entity.enums.PaymentProvider;
import org.example.electronics.entity.enums.PaymentTransactionStatus;
import org.example.electronics.entity.enums.PaymentTransactionType;
import org.example.electronics.mapper.PaymentTransactionMapper;
import org.example.electronics.repository.PaymentTransactionRepository;
import org.example.electronics.repository.ReturnRequestRepository;
import org.example.electronics.service.admin.AdminPaymentTransactionService;
import org.example.electronics.util.DateTimeUtils;
import org.example.electronics.util.payment.MomoUtils;
import org.example.electronics.util.payment.VNPayUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminPaymentTransactionServiceImpl implements AdminPaymentTransactionService {

    private final PaymentTransactionMapper paymentTransactionMapper;
    private final PaymentTransactionRepository paymentTransactionRepository;

    private final ReturnRequestRepository returnRequestRepository;

    private final VNPayConfig vnPayConfig;
    private final RestTemplate restTemplate;

    private final MomoConfig momoConfig;

    @Transactional(readOnly = true)
    @Override
    public Page<AdminPaymentTransactionResponseDTO> getAllPaymentTransactions(String keyword, PaymentTransactionType type, PaymentTransactionStatus status, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        LocalDateTime startDateTime = DateTimeUtils.getStartOfDay(fromDate);
        LocalDateTime endDateTime = DateTimeUtils.getEndOfDay(toDate);

        String finalKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;

        Page<PaymentTransactionEntity> entityPage = paymentTransactionRepository.findPaymentsWithFilter(finalKeyword, type, status, startDateTime, endDateTime, pageable);

        return entityPage.map(paymentTransactionMapper::toAdminResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminPaymentTransactionResponseDTO getPaymentTransactionById(Integer id) {
        PaymentTransactionEntity existingPaymentTransactionEntity = paymentTransactionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy giao dịch thanh toán với id: " + id
                ));

        return paymentTransactionMapper.toAdminResponseDTO(existingPaymentTransactionEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void processRefund(Integer returnRequestId, Integer staffId) {
        ReturnRequestEntity returnRequest = returnRequestRepository.findById(returnRequestId)
                .orElseThrow(() -> new RuntimeException(
                        "Không tìm thấy yêu cầu trả hàng với id: " + returnRequestId
                ));

        PaymentTransactionEntity originalPaymentTransaction = paymentTransactionRepository.findByOrderIdAndTypeAndStatus(
                returnRequest.getOrder().getId(),
                PaymentTransactionType.PAYMENT,
                PaymentTransactionStatus.SUCCESS
        ).orElseThrow(() -> new RuntimeException(
                "Đơn hàng này chưa thanh toán hoặc thanh toán lỗi, không thể hoàn tiền"
        ));

        if (originalPaymentTransaction.getProvider() == PaymentProvider.VNPAY) {
            processVNPayRefund(originalPaymentTransaction, returnRequest, staffId);
        }
        else if (originalPaymentTransaction.getProvider() == PaymentProvider.MOMO) {
            processMomoRefund(originalPaymentTransaction, returnRequest, staffId);
        }
        else if (originalPaymentTransaction.getProvider() == PaymentProvider.COD) {
            saveRefundTransaction(originalPaymentTransaction, returnRequest, "Hoàn tiền mặt trực tiếp");
        }
    }

    private void processVNPayRefund(PaymentTransactionEntity originalTx, ReturnRequestEntity returnRequest, Integer staffId) {
        try {
            // Định dạng thời gian theo chuẩn VNPay yyyyMMddHHmmss
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            String vnp_RequestId = VNPayUtils.getRandomNumber(8); // Tự viết thêm hàm này trong Utils để gen số ngẫu nhiên
            String vnp_Version = "2.1.0";
            String vnp_Command = "refund";
            String vnp_TmnCode = vnPayConfig.getVnp_TmnCode();
            String vnp_TransactionType = "02"; // 02: Hoàn toàn bộ tiền. (Nếu sếp muốn hoàn 1 phần thì dùng 03)
            String vnp_TxnRef = originalTx.getOrder().getId().toString();
            long amount = originalTx.getAmount().multiply(BigDecimal.valueOf(100)).longValue(); // VNPay bắt nhân 100
            String vnp_Amount = String.valueOf(amount);
            String vnp_OrderInfo = "Hoan tien cho don hang " + vnp_TxnRef;
            String vnp_TransactionNo = originalTx.getProviderPaymentId(); // RẤT QUAN TRỌNG: Mã giao dịch lúc khách quẹt thẻ
            String vnp_TransactionDate = originalTx.getPaymentTime().format(formatter); // Thời gian lúc mua hàng
            String vnp_CreateBy = "Admin_" + staffId;
            String vnp_CreateDate = LocalDateTime.now().format(formatter);
            String vnp_IpAddr = "127.0.0.1"; // Hoàn tiền ngầm trên server nên để IP server

            // Tạo chuỗi Hash (Tuyệt đối phải đúng thứ tự này theo tài liệu VNPay)
            String hashData = vnp_RequestId + "|" + vnp_Version + "|" + vnp_Command + "|" + vnp_TmnCode + "|" +
                    vnp_TransactionType + "|" + vnp_TxnRef + "|" + vnp_Amount + "|" + vnp_TransactionNo + "|" +
                    vnp_TransactionDate + "|" + vnp_CreateBy + "|" + vnp_CreateDate + "|" + vnp_IpAddr + "|" + vnp_OrderInfo;

            String vnp_SecureHash = VNPayUtils.hmacSHA512(vnPayConfig.getSecretKey(), hashData);

            // Gói vào JSON
            Map<String, String> requestData = new HashMap<>();
            requestData.put("vnp_RequestId", vnp_RequestId);
            requestData.put("vnp_Version", vnp_Version);
            requestData.put("vnp_Command", vnp_Command);
            requestData.put("vnp_TmnCode", vnp_TmnCode);
            requestData.put("vnp_TransactionType", vnp_TransactionType);
            requestData.put("vnp_TxnRef", vnp_TxnRef);
            requestData.put("vnp_Amount", vnp_Amount);
            requestData.put("vnp_OrderInfo", vnp_OrderInfo);
            requestData.put("vnp_TransactionNo", vnp_TransactionNo);
            requestData.put("vnp_TransactionDate", vnp_TransactionDate);
            requestData.put("vnp_CreateBy", vnp_CreateBy);
            requestData.put("vnp_CreateDate", vnp_CreateDate);
            requestData.put("vnp_IpAddr", vnp_IpAddr);
            requestData.put("vnp_SecureHash", vnp_SecureHash);

            // Bắn HTTP POST sang VNPay
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestData, headers);

            log.info("Bắt đầu gửi lệnh Hoàn tiền sang VNPay cho mã đơn: {}", vnp_TxnRef);

            // Dùng exchange để khai báo rõ kiểu Generic Map<String, String>
            ResponseEntity<Map<String, String>> responseEntity = restTemplate.exchange(
                    vnPayConfig.getVnp_RefundUrl(),
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {
                    }
            );
            Map<String, String> response = responseEntity.getBody();

            log.info("Kết quả VNPay trả về: {}", response);

            // Kiểm tra kết quả
            if (response != null && "00".equals(response.get("vnp_ResponseCode"))) {
                // THÀNH CÔNG: Ghi nhận 1 dòng giao dịch Hoàn tiền vào Database
                saveRefundTransaction(originalTx, returnRequest, response.toString());
            } else {
                // THẤT BẠI
                String errMsg = response != null ? response.get("vnp_Message") : "Lỗi mạng kết nối VNPay";
                throw new RuntimeException("Hoàn tiền VNPay thất bại: " + errMsg);
            }

        } catch (Exception e) {
            log.error("Lỗi khi xử lý Refund VNPay: ", e);
            throw new RuntimeException("Hệ thống lỗi khi xử lý hoàn tiền: " + e.getMessage());
        }
    }

    // ==========================================
    // 🚀 VÙNG LÕI: GỌI API Momo S2S HOÀN TIỀN
    // ==========================================
    private void processMomoRefund(PaymentTransactionEntity originalTx, ReturnRequestEntity returnRequest, Integer staffId) {
        try {
            // Momo yêu cầu 1 mã OrderId riêng biệt cho giao dịch hoàn tiền này (không được trùng với lúc mua)
            String refundOrderId = "REFUND_" + originalTx.getOrder().getId() + "_" + System.currentTimeMillis();
            String requestId = String.valueOf(System.currentTimeMillis());

            // Số tiền hoàn. Khác với VNPay (bắt nhân 100), Momo dùng tiền VNĐ chuẩn
            long amount = originalTx.getAmount().longValue();
            String description = "Hoan tien don hang " + originalTx.getOrder().getId() + " boi Admin_" + staffId;

            // providerPaymentId chính là transId (Mã giao dịch của Momo) lưu lúc khách thanh toán thành công
            Long transId = Long.parseLong(originalTx.getProviderPaymentId());

            // 1. Tạo chuỗi Hash (BẮT BUỘC sắp xếp theo Alphabet A-Z)
            String rawHash = "accessKey=" + momoConfig.getAccessKey() +
                    "&amount=" + amount +
                    "&description=" + description +
                    "&orderId=" + refundOrderId +
                    "&partnerCode=" + momoConfig.getPartnerCode() +
                    "&requestId=" + requestId +
                    "&transId=" + transId;

            // 2. Ký bảo mật bằng HMAC-SHA256
            String signature = MomoUtils.hmacSHA256(rawHash, momoConfig.getSecretKey());

            // 3. Gói data vào JSON Map
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("partnerCode", momoConfig.getPartnerCode());
            requestData.put("orderId", refundOrderId);
            requestData.put("requestId", requestId);
            requestData.put("amount", amount);
            requestData.put("transId", transId);
            requestData.put("lang", "vi");
            requestData.put("description", description);
            requestData.put("signature", signature);

            // 4. Bắn HTTP POST sang Momo
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestData, headers);

            log.info("Bắt đầu gửi lệnh Hoàn tiền sang Momo cho mã đơn gốc: {}", originalTx.getOrder().getId());

            // Dùng exchange để khai báo rõ kiểu Generic Map<String, Object>
            ResponseEntity<Map<String, Object>> responseEntity = restTemplate.exchange(
                    momoConfig.getRefundUrl(),
                    HttpMethod.POST,
                    entity,
                    new ParameterizedTypeReference<>() {}
            );
            Map<String, Object> response = responseEntity.getBody();

            log.info("Kết quả Momo trả về: {}", response);

            // 5. Đọc kết quả từ Momo (resultCode = 0 là thành công)
            if (response != null && Integer.valueOf(0).equals(response.get("resultCode"))) {
                // THÀNH CÔNG: Ghi nhận vào Database
                saveRefundTransaction(originalTx, returnRequest, response.toString());
            } else {
                // THẤT BẠI
                String errMsg = response != null ? (String) response.get("message") : "Lỗi mạng kết nối Momo";
                throw new RuntimeException("Hoàn tiền Momo thất bại: " + errMsg);
            }

        } catch (Exception e) {
            log.error("Lỗi khi xử lý Refund Momo: ", e);
            throw new RuntimeException("Hệ thống lỗi khi xử lý hoàn tiền Momo: " + e.getMessage());
        }
    }

    private void saveRefundTransaction(PaymentTransactionEntity originalPaymentTransaction, ReturnRequestEntity returnRequest, String note) {
        PaymentTransactionEntity refundTx = PaymentTransactionEntity.builder()
                .order(originalPaymentTransaction.getOrder())
                .returnRequest(returnRequest)
                .type(PaymentTransactionType.REFUND)
                .provider(originalPaymentTransaction.getProvider())
                .providerPaymentId(originalPaymentTransaction.getProviderPaymentId())
                .amount(originalPaymentTransaction.getAmount())
                .status(PaymentTransactionStatus.SUCCESS)
                .paymentTime(LocalDateTime.now())
                .note("Hoàn tiền cho yêu cầu trả hàng #" + returnRequest.getId() + ". " + note)
                .build();

        paymentTransactionRepository.save(refundTx);
    }
}
