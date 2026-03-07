package org.example.electronics.service;

import org.example.electronics.dto.request.CreateReviewRequestDTO;
import org.example.electronics.dto.response.ReviewResponseDTO;
import org.example.electronics.entity.ProductEntity;
import org.example.electronics.entity.ReviewEntity;
import org.example.electronics.entity.UserEntity;
import org.example.electronics.entity.enums.OrderStatus;
import org.example.electronics.entity.enums.ReviewStatus;
import org.example.electronics.repository.OrderRepository;
import org.example.electronics.repository.ProductRepository;
import org.example.electronics.repository.ReviewRepository;
import org.example.electronics.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public ReviewService(ReviewRepository reviewRepository,
                         ProductRepository productRepository,
                         UserRepository userRepository,
                         OrderRepository orderRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
    }

    // ========== User ==========

    @Transactional
    public ReviewResponseDTO createReview(String email, CreateReviewRequestDTO request) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));

        ProductEntity product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        // Validate user bought this product (i.e. has a COMPLETED order containing a variant of this product)
        boolean hasPurchased = orderRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).stream()
                .filter(o -> o.getStatus() == OrderStatus.COMPLETED)
                .flatMap(o -> o.getOrderDetails().stream())
                .anyMatch(d -> d.getVariant().getProduct().getId().equals(product.getId()));

        if (!hasPurchased) {
            throw new RuntimeException("Bạn cần mua và nhận thành công sản phẩm này để có thể đánh giá");
        }

        ReviewEntity review = ReviewEntity.builder()
                .product(product)
                .user(user)
                .star(request.star())
                .content(request.content())
                .status(ReviewStatus.PENDING)
                .build();

        reviewRepository.save(review);
        return toResponseDTO(review);
    }

    public List<ReviewResponseDTO> getProductReviews(Integer productId) {
        // Return only APPROVED reviews for public view
        return reviewRepository.findByProductId(productId).stream()
                .filter(r -> r.getStatus() == ReviewStatus.APPROVED)
                .map(this::toResponseDTO)
                .toList();
    }

    // ========== Admin ==========

    public List<ReviewResponseDTO> getAllReviews() {
        return reviewRepository.findAll().stream().map(this::toResponseDTO).toList();
    }

    @Transactional
    public ReviewResponseDTO approveReview(Integer id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá"));

        if (review.getStatus() == ReviewStatus.APPROVED) {
            return toResponseDTO(review); // Already approved
        }

        review.setStatus(ReviewStatus.APPROVED);

        // Update product ratings
        ProductEntity product = review.getProduct();
        int newCount = product.getRatingCount() + 1;
        float newStar = ((product.getRatingStar() * product.getRatingCount()) + review.getStar()) / newCount;

        product.setRatingCount(newCount);
        product.setRatingStar(newStar);

        productRepository.save(product);
        reviewRepository.save(review);

        return toResponseDTO(review);
    }

    public ReviewResponseDTO rejectReview(Integer id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đánh giá"));

        if (review.getStatus() == ReviewStatus.APPROVED) {
            // Need to deduct from product stats if reverting an APPROVED review?
            // Since requirements only said approve/reject from pending, assume simple reject:
            ProductEntity product = review.getProduct();
            int newCount = product.getRatingCount() - 1;
            float newStar = newCount == 0 ? 0 : ((product.getRatingStar() * product.getRatingCount()) - review.getStar()) / newCount;

            product.setRatingCount(newCount);
            product.setRatingStar(newStar);
            productRepository.save(product);
        }

        review.setStatus(ReviewStatus.REJECTED);
        reviewRepository.save(review);

        return toResponseDTO(review);
    }

    // ========== Helpers ==========

    private ReviewResponseDTO toResponseDTO(ReviewEntity r) {
        return new ReviewResponseDTO(
                r.getId(),
                r.getProduct().getId(),
                r.getProduct().getName(),
                r.getUser().getEmail(),
                r.getUser().getName(),
                r.getStar(),
                r.getContent(),
                r.getStatus(),
                r.getCreatedAt()
        );
    }
}
