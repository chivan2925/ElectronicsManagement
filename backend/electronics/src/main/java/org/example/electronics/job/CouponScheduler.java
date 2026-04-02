package org.example.electronics.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.electronics.entity.enums.CouponStatus;
import org.example.electronics.repository.CouponRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class CouponScheduler {

    private final CouponRepository couponRepository;

    @Scheduled(fixedRate = 60_000)
    @Transactional
    public void scanAndExpireCoupons() {
        LocalDateTime now = LocalDateTime.now();

        int updatedCouponsCount = couponRepository.updateExpiredCoupons(
                now,
                CouponStatus.EXPIRED,
                CouponStatus.VALID
        );

        if(updatedCouponsCount > 0) {
            log.info("Coupon Scheduler: Đã tự động chuyển trạng thái của {} coupon sang EXPIRED do hết hạn", updatedCouponsCount);
        }
    }
}
