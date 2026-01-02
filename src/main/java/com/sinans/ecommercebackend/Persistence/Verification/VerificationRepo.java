package com.sinans.ecommercebackend.Persistence.Verification;

import com.sinans.ecommercebackend.Persistence.User.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.time.Instant;
import java.util.Optional;

public interface VerificationRepo extends JpaRepository<VerificationEntity, Long> {
    Optional<VerificationEntity> findByToken(String token);

    @Modifying
    @Transactional
    void deleteAllByExpirationDateBefore(Instant expireDate);

    VerificationEntity findByUser(UserEntity user);

    VerificationEntity deleteByUser(UserEntity user);
}
