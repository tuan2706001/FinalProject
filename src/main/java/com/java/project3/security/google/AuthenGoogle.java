package com.java.project3.security.google;

import com.bkh.vnoip.domain.user.User;
import com.bkh.vnoip.repository.UserRepository;
import com.bkh.vnoip.security.v2.JwtTokenUtil;
import com.bkh.vnoip.service.user.UserService;
import com.bkh.vnoip.utils.BeanUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenGoogle implements AuthenticationSuccessHandler {
    JwtTokenUtil jwtTokenProvider;
    @Value("${cookie.domain.parrent}")
    String parrentCookieDomain;
    UserRepository userRepository;
    UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        jwtTokenProvider = BeanUtil.getBean(JwtTokenUtil.class);
        userService = BeanUtil.getBean(UserService.class);
        userRepository = BeanUtil.getBean(UserRepository.class);
        DefaultOidcUser defaultOidcUser = (DefaultOidcUser) authentication.getPrincipal();
        String email = defaultOidcUser.getEmail();
        String fullName = defaultOidcUser.getFullName();
        // Kiểm tra user với email
        User user = userRepository.findByEmailAndEnabled(email, true);
        if (user != null) {
            Authentication newAuth = new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        } else {
            userService.supportCreateUserFromGoogle(email, fullName);
            User user2 = userRepository.findByEmailAndEnabled(email, true);
            Authentication newAuth = new UsernamePasswordAuthenticationToken(user2, user2.getPassword(), user2.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }

        String jwtToken = jwtTokenProvider.generateTokenByUserName(email);
        Cookie cookie = new Cookie("jwt", jwtToken);
        cookie.setPath("/");
        cookie.setDomain("localhost");
        response.addCookie(cookie);
    }
}
