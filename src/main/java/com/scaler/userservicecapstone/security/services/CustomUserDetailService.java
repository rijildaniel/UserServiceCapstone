package com.scaler.userservicecapstone.security.services;

import com.scaler.userservicecapstone.models.User;
import com.scaler.userservicecapstone.repositories.UserRepository;
import com.scaler.userservicecapstone.security.models.CustomUserDetail;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Username not found");
        }

        User user = userOptional.get();

        return new CustomUserDetail(user);
    }
}
