package org.example.electronics.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.electronics.repository.InvalidatedTokenRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
@Slf4j
public class TokenCleanupScheduler {

    private final InvalidatedTokenRepository invalidatedTokenRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    public void cleanupExpiredTokens() {
        log.info("Bắt đầu dọn dẹp các token đã hết hạn trong Invalidated...");
        invalidatedTokenRepository.deleteAllExpiredTokens(new Date());
        log.info("Hoàn tất dọn dẹp token rác");
    }
}
