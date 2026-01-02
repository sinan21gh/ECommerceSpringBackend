package com.sinans.ecommercebackend.Persistence.Verification;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class verify {
    private final VerificationRepo verificationRepo;

    @Async
    @Scheduled(cron = "0 */30 03 * * *")
    public void checkTokens() {
        verificationRepo.deleteAllByExpirationDateBefore(Instant.now());
    }
}
