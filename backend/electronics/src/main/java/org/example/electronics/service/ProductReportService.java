package org.example.electronics.service;

import org.example.electronics.dto.request.CreateReportRequestDTO;
import org.example.electronics.dto.request.HandleReportRequestDTO;
import org.example.electronics.dto.response.ReportResponseDTO;
import org.example.electronics.entity.ProductEntity;
import org.example.electronics.entity.ReportEntity;
import org.example.electronics.entity.StaffEntity;
import org.example.electronics.entity.UserEntity;
import org.example.electronics.entity.enums.HandlingMeasureType;
import org.example.electronics.entity.enums.ProductStatus;
import org.example.electronics.repository.ProductRepository;
import org.example.electronics.repository.ReportRepository;
import org.example.electronics.repository.StaffRepository;
import org.example.electronics.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductReportService {

    private final ReportRepository reportRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final StaffRepository staffRepository;

    public ProductReportService(ReportRepository reportRepository,
                                ProductRepository productRepository,
                                UserRepository userRepository,
                                StaffRepository staffRepository) {
        this.reportRepository = reportRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.staffRepository = staffRepository;
    }

    @Transactional
    public ReportResponseDTO createReport(String userEmail, CreateReportRequestDTO request) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        ReportEntity report = new ReportEntity();
        report.setUser(user);
        report.setProduct(product);
        report.setReason(request.reason());
        report.setProofJson(request.proofJson());
        
        ReportEntity saved = reportRepository.save(report);
        return mapToDTO(saved);
    }

    public List<ReportResponseDTO> getMyReports(String userEmail) {
        UserEntity user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        
        return reportRepository.findByUserIdOrderByCreatedAtDesc(user.getId())
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public Page<ReportResponseDTO> getAllReports(Pageable pageable) {
        return reportRepository.findAll(pageable).map(this::mapToDTO);
    }

    public ReportResponseDTO getReportById(Integer id) {
        ReportEntity report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy báo cáo"));
        return mapToDTO(report);
    }

    @Transactional
    public ReportResponseDTO handleReport(Integer id, String staffEmail, HandleReportRequestDTO request) {
        ReportEntity report = reportRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy báo cáo"));

        StaffEntity staff = staffRepository.findByEmail(staffEmail)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        report.setStatus(request.status());
        report.setHandlingMeasures(request.handlingMeasures());
        report.setHandledByStaff(staff);

        // Crucial Logic: Apply handling measure to product
        ProductEntity product = report.getProduct();
        if (request.handlingMeasures() == HandlingMeasureType.TEMPORARY_HIDE_PRODUCT) {
            product.setStatus(ProductStatus.HIDDEN);
        } else if (request.handlingMeasures() == HandlingMeasureType.PERMANENT_DELETE_PRODUCT) {
            product.setStatus(ProductStatus.DELETED);
        }
        productRepository.save(product);

        ReportEntity saved = reportRepository.save(report);
        return mapToDTO(saved);
    }

    private ReportResponseDTO mapToDTO(ReportEntity report) {
        String staffName = report.getHandledByStaff() != null ? report.getHandledByStaff().getName() : null;
        return new ReportResponseDTO(
                report.getId(),
                report.getProduct().getId(),
                report.getProduct().getName(),
                report.getUser().getName(),
                staffName,
                report.getReason(),
                report.getProofJson(),
                report.getStatus().name(),
                report.getHandlingMeasures().name(),
                report.getCreatedAt(),
                report.getUpdatedAt()
        );
    }
}
