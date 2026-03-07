package org.example.electronics.service;

import org.example.electronics.dto.response.LowStockDTO;
import org.example.electronics.dto.response.RevenueReportDTO;
import org.example.electronics.dto.response.TopProductDTO;
import org.example.electronics.repository.OrderDetailRepository;
import org.example.electronics.repository.OrderRepository;
import org.example.electronics.repository.VariantRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReportService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final VariantRepository variantRepository;

    public ReportService(OrderRepository orderRepository,
                         OrderDetailRepository orderDetailRepository,
                         VariantRepository variantRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.variantRepository = variantRepository;
    }

    public List<RevenueReportDTO> getRevenue(LocalDateTime startDate, LocalDateTime endDate) {
        return orderRepository.getRevenueByDateRange(startDate, endDate);
    }

    public List<TopProductDTO> getTopSellingProducts(int limit) {
        return orderDetailRepository.getTopSellingProducts(PageRequest.of(0, limit));
    }

    public List<LowStockDTO> getLowStockVariants(int threshold) {
        return variantRepository.findByStockLessThan(threshold).stream()
                .map(v -> new LowStockDTO(
                        v.getId(),
                        v.getName(),
                        v.getProduct().getId(),
                        v.getProduct().getName(),
                        v.getStock()
                )).toList();
    }
}
