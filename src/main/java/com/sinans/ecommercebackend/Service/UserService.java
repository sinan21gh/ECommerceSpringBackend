package com.sinans.ecommercebackend.Service;

import com.sinans.ecommercebackend.Controller.Users.LoginRequest;
import com.sinans.ecommercebackend.Controller.Users.UserDTO;
import com.sinans.ecommercebackend.Controller.Users.UserRegisterDTO;
import com.sinans.ecommercebackend.Mappers.Mapper;
import com.sinans.ecommercebackend.Persistence.Role;
import com.sinans.ecommercebackend.Persistence.User.UserEntity;
import com.sinans.ecommercebackend.Persistence.User.UserRepository;
import com.sinans.ecommercebackend.Persistence.Verification.VerificationEntity;
import com.sinans.ecommercebackend.Persistence.Verification.VerificationRepo;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    private UserRepository userRepository;
    private Mapper<UserEntity, UserDTO> mapper;
    private JwtService jwtService;
    private EmailVerification verification;
    private VerificationRepo verificationRepo;
    public UserService(UserRepository userRepository, Mapper<UserEntity, UserDTO> mapper, JwtService jwtService, EmailVerification verification,  VerificationRepo verificationRepo) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.jwtService = jwtService;
        this.verification = verification;
        this.verificationRepo = verificationRepo;
    }

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDTO register(UserRegisterDTO dto){
        if (userRepository.findByUsername(dto.getUsername()).isPresent() || userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Username or Email already exists");
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        dto.setPassword(encoder.encode(dto.getPassword()));
        UserEntity userEntity = UserEntity.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .role(Role.USER)
                .verified(false)
                .build();
        userRepository.save(userEntity);

        VerificationEntity verificationEntity = VerificationEntity.builder()
                .id(null)
                .user(userEntity)
                .token(UUID.randomUUID().toString())
                .expirationDate(Instant.now().plus(1, ChronoUnit.HOURS))
                .build();
        verificationRepo.save(verificationEntity);

        verification.sendVerificationEmail(userEntity.getEmail(), userEntity.getUsername(), verificationEntity.getToken());

        return mapper.EntityToDTO(userEntity);
    }

    public String login(LoginRequest loginRequest) {

        UserEntity user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!user.isVerified()){
            throw new RuntimeException("Verify your account");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPasswordMatch = encoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!isPasswordMatch) {
            throw new RuntimeException("Invalid username or password");
        }

        return jwtService.generateToken(new UserPrincipal(user));
    }

    public void verifyUser(String token){
        VerificationEntity verificationEntity = verificationRepo.findByToken(token).get();
        if (verificationEntity.getExpirationDate().isBefore(Instant.now()) && !verificationEntity.getUser().isVerified()) {
            VerificationEntity verificationEntity2 = VerificationEntity.builder()
                    .id(null)
                    .user(verificationEntity.getUser())
                    .token(UUID.randomUUID().toString())
                    .expirationDate(Instant.now().plus(1, ChronoUnit.HOURS))
                    .build();

            verificationRepo.deleteById(verificationEntity.getId());
            verificationRepo.save(verificationEntity2);

            verification.sendVerificationEmail(verificationEntity2.getUser().getEmail(), verificationEntity2.getUser().getUsername(), verificationEntity2.getToken());
        }
        else{
            UserEntity user = verificationEntity.getUser();
            user.setVerified(true);
            userRepository.save(user);
            verificationRepo.deleteById(verificationEntity.getId());
        }
    }

    public void resendVerificationEmail(String email){
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        verificationRepo.deleteByUser(user);

        VerificationEntity verificationEntity = VerificationEntity.builder()
                .id(null)
                .user(user)
                .token(UUID.randomUUID().toString())
                .expirationDate(Instant.now().plus(1, ChronoUnit.HOURS))
                .build();
        verificationRepo.save(verificationEntity);

        verification.sendVerificationEmail(email, verificationEntity.getUser().getUsername(), verificationEntity.getToken());
    }
}
