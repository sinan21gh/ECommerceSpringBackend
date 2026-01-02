package com.sinans.ecommercebackend.Controller.Users;

import com.sinans.ecommercebackend.Persistence.User.UserEntity;
import com.sinans.ecommercebackend.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ecommerce")
public class UserController {
    private final UserService userService;

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserEntity> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/users/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRegisterDTO userDTO) {
        UserDTO savedUser = userService.register(userDTO);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PostMapping("/users/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    @GetMapping(path = "users/verify")
    public void verifyUser(@RequestParam String token ) {
        userService.verifyUser(token);
    }

    @PostMapping(path =  "/users/resendverification")
    public void sendVerification(@RequestBody EmailSubmission email) {
        userService.resendVerificationEmail(email.getEmail());
    }
}
