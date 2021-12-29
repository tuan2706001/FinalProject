package com.java.project3.security;

import com.bkh.vnoip.domain.user.User;
import com.bkh.vnoip.repository.RoleRepository;
import com.bkh.vnoip.repository.UserRepository;
import com.bkh.vnoip.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetail implements UserDetailsService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameAndEnabled(username, true);
        if (user == null || user.getPassword() == null) {
            throw new UsernameNotFoundException("Tài khoản '" + username + "' không tồn tại hoặc chưa được kích hoạt!");
        }
        return user;
    }
}
