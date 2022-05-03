package com.example.demo.config;

import com.example.demo.model.User;
import com.example.demo.service.DefaultUserService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final DefaultUserService userService;

    public CustomUserDetailsService(DefaultUserService defaultUserService) {
        this.userService = defaultUserService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userEntity = userService.findByLogin(username);
        return CustomUserDetails.fromUserEntityToCustomUserDetails(userEntity);
    }
}
