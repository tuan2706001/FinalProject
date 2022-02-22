package com.java.project3.security;

import com.java.project3.constant.Enum;
import com.java.project3.domain.User;
import com.java.project3.repository.UserRepository;
import com.java.project3.service.base.ContextService;
import com.java.project3.service.base.GenIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        return bCryptPasswordEncoder;
    }

    @Configuration
    @Order(2) // Cấu hình security cho web
    public class FormLoginConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private DataSource dataSource;
        //        @Autowired
//        UserDetail userDetail;
        @Autowired
        UserRepository userRepository;
        @Autowired
        GenIdService genIdService;
        @Autowired
        ContextService contextService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .authorizeRequests(authz -> authz
                            .antMatchers(
                                    "/",
                                    "/dang-nhap",
                                    "/logout",
                                    "/forgot-password/**",
                                    "/dang-ky/**"
                            ).permitAll() // các url như trên sẽ được cho phép truy cập, không cần login
                            .anyRequest().authenticated() // các request đến các url còn lại sẽ phải loggin
                    )
                    .csrf().disable()

                    //region cấu hình đăng nhập bằng form
                    .formLogin()
                    .loginProcessingUrl("/j_spring_security_check")
                    .loginPage("/dang-nhap")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .failureUrl("/dang-nhap")
                    .successHandler(new AuthenticationSuccessHandler() {

                        private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                            Authentication authentication) throws IOException {
                            User user = (User) authentication.getPrincipal();
                            if (user.getRole().equals(Enum.QUAN_LY.value)) {
                                redirectStrategy.sendRedirect(request, response, "/trang-chu");
                            } else if (user.getRole().equals(Enum.GIAO_VU.value)) {
                                redirectStrategy.sendRedirect(request, response, "/bac-si/trang-chinh");
                            } else if (user.getRole().equals(Enum.SINH_VIEN.value)) {
                                redirectStrategy.sendRedirect(request, response, "/cham-soc-khach-hang/trang-chinh");
                            } else {
                                redirectStrategy.sendRedirect(request, response, "/");
                            }
                        }
                    })
                    .permitAll()
                    .and()
                    .logout().logoutUrl("/logout").logoutSuccessUrl("/").permitAll();
            //endregion

//                    //option này cứ mỗi lần vào là lại gia hạn đăng nhập thêm 2 tuần tính từ lúc mở trang
//                    .and()
//                    .rememberMe().key("remember-me")
//                    .userDetailsService(userDetail)
//                    .tokenRepository(persistentTokenRepository());
        }


        @Override
        public void configure(WebSecurity web) throws Exception {
            // Allow swagger, css, js, webjar to be accessed without authentication
            web.ignoring()
                    .antMatchers("/configuration/**")//
                    .antMatchers("/webjars/**")//
                    .antMatchers("/public")//
                    .antMatchers("/css/**")
                    .antMatchers("/js/**")
                    .antMatchers("/images/**")
                    .antMatchers("/auth/**")
                    .antMatchers("/file/**")
                    .antMatchers("/ckeditor/**")
                    .antMatchers("/favicon.ico")
                    .antMatchers("/resources/**", "/error")
                    .and()
                    .ignoring();
        }

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
            db.setDataSource(dataSource);
            return db;
        }
    }
}
