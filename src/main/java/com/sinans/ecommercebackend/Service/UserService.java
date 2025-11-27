package com.sinans.ecommercebackend.Service;

import com.sinans.ecommercebackend.Controller.Users.LoginRequest;
import com.sinans.ecommercebackend.Controller.Users.UserDTO;
import com.sinans.ecommercebackend.Controller.Users.UserRegisterDTO;
import com.sinans.ecommercebackend.Mappers.Mapper;
import com.sinans.ecommercebackend.Persistence.Role;
import com.sinans.ecommercebackend.Persistence.User.UserEntity;
import com.sinans.ecommercebackend.Persistence.User.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;
    private Mapper<UserEntity, UserDTO> mapper;
    private JwtService jwtService;
    public UserService(UserRepository userRepository, Mapper<UserEntity, UserDTO> mapper, JwtService jwtService) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.jwtService = jwtService;
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
                .build();
        userRepository.save(userEntity);
        return mapper.EntityToDTO(userEntity);
    }

    public String login(LoginRequest loginRequest) {

        UserEntity user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        boolean isPasswordMatch = encoder.matches(loginRequest.getPassword(), user.getPassword());

        if (!isPasswordMatch) {
            throw new RuntimeException("Invalid username or password");
        }
        return jwtService.generateToken(new UserPrincipal(user));
    }


}
