//package com.java.project3.security;
//
//import com.java.project3.domain.User;
//import com.java.project3.dto.base.ResponseDto;
//import com.java.project3.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import javax.servlet.http.Cookie;
//import java.util.Optional;
//
//@Service
//public class UserDetail implements UserDetailsService {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
////        ResponseDto responseDto = new ResponseDto();
//        Optional<User> user = userRepository.findByEmail(email);
//        if (user.isPresent()) {
//            return user.get();
//        }
////        Cookie jwtCookie = new Cookie("emailUser", user.get().getEmail());
////        add
////        return null;
//        throw new UsernameNotFoundException("Tài khoản '"+email+"' không tồn tại!");
//    }
//}
