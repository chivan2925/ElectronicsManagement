package org.example.electronics.service.admin.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.electronics.dto.request.admin.status.AdminUpdateReturnRequestStatusRequestDTO;
import org.example.electronics.dto.response.admin.returnrequest.AdminDetailReturnRequestResponseDTO;
import org.example.electronics.dto.response.admin.returnrequest.AdminReturnRequestResponseDTO;
import org.example.electronics.entity.ReturnRequestEntity;
import org.example.electronics.entity.enums.ReturnRequestStatus;
import org.example.electronics.mapper.ReturnRequestMapper;
import org.example.electronics.repository.ReturnRequestRepository;
import org.example.electronics.repository.StaffRepository;
import org.example.electronics.service.admin.AdminReturnRequestService;
import org.example.electronics.service.admin.AdminWarehouseTransactionService;
import org.example.electronics.util.DateTimeUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminReturnRequestServiceImpl implements AdminReturnRequestService {

    private final ReturnRequestMapper returnRequestMapper;
    private final ReturnRequestRepository returnRequestRepository;
    private final StaffRepository staffRepository;
    private final AdminWarehouseTransactionService adminWarehouseTransactionService;

    @Transactional
    @Override
    public AdminReturnRequestResponseDTO updateStatusReturnRequest(Integer returnRequestId, AdminUpdateReturnRequestStatusRequestDTO requestDTO, Integer staffId) {
        ReturnRequestEntity existingReturnRequestEntity = returnRequestRepository.findById(returnRequestId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy yêu cầu trả hàng với id: " + returnRequestId
                ));

        existingReturnRequestEntity.setStatus(requestDTO.status());
        existingReturnRequestEntity.setHandledByStaff(staffRepository.getReferenceById(staffId));

        if (requestDTO.status() == ReturnRequestStatus.APPROVED) {
            existingReturnRequestEntity.setResolvedAt(LocalDateTime.now());

            adminWarehouseTransactionService.autoCreateReturnWarehouseTransaction(existingReturnRequestEntity, staffId);
        }

        return returnRequestMapper.toResponseDTO(existingReturnRequestEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AdminReturnRequestResponseDTO> getAllReturnRequests(String keyword, ReturnRequestStatus status, LocalDate fromDate, LocalDate toDate, Pageable pageable) {
        LocalDateTime startDateTime = DateTimeUtils.getStartOfDay(fromDate);
        LocalDateTime endDateTime = DateTimeUtils.getEndOfDay(toDate);

        String finalKeyword = StringUtils.hasText(keyword) ? keyword.trim() : null;

        Page<ReturnRequestEntity> returnRequestEntityPage = returnRequestRepository.findAllReturnRequestsWithFilter(finalKeyword, status, startDateTime, endDateTime, pageable);

        return returnRequestEntityPage.map(returnRequestMapper::toResponseDTO);
    }

    @Transactional(readOnly = true)
    @Override
    public AdminDetailReturnRequestResponseDTO getReturnRequestById(Integer id) {
        ReturnRequestEntity existingReturnRequestEntity = returnRequestRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Không tìm thấy yêu cầu trả hàng với id: " + id
                ));

        return returnRequestMapper.toDetailResponseDTO(existingReturnRequestEntity);
    }
}
