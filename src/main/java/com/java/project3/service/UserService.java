package com.java.project3.service;

import com.java.project3.repository.UserRepository;
import com.java.project3.service.base.GenIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    GenIdService genIdService;
}
