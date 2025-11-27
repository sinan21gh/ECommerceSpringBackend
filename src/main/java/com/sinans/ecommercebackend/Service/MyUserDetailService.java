package com.sinans.ecommercebackend.Service;

import com.sinans.ecommercebackend.Persistence.User.UserEntity;
import com.sinans.ecommercebackend.Persistence.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity users = userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (users == null){
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(users);
    }
}
