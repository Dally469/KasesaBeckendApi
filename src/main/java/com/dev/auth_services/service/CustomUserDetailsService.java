package com.dev.auth_services.service;

import com.dev.auth_services.custom.CustomUserDetails;
import com.dev.auth_services.entity.User;
import com.dev.auth_services.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    public UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> credential = userRepository.findUserByUsername(username);
        return credential.map(
                CustomUserDetails::new
        ).orElseThrow(() -> new UsernameNotFoundException("Username not found in system" + username));
    }
}
