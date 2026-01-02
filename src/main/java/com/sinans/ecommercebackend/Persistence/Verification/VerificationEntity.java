package com.sinans.ecommercebackend.Persistence.Verification;

import com.sinans.ecommercebackend.Persistence.User.UserEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class VerificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    @NonNull
    private UserEntity user;

    private Instant expirationDate;

    @NotBlank
    private String token;
}
