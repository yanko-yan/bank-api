package com.yankoyan.spring.bank_api.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
    public UserDetails getCurrentUserDetails(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
    public String getCurrentUsername(){
        return getCurrentUserDetails().getUsername();
    }
}
