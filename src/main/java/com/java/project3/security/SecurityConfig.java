//package com.java.project3.security;
//
//import com.java.project3.service.CustomerUserDetailService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    CustomerUserDetailService customerUserDetailService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.authorizeRequests()
//                .antMatchers("/trang-chu","/vendor/**","/js/**","/css/**","/fonts/**","/image/**").permitAll()
//                .anyRequest().authenticated().and()
//                .formLogin().loginPage("/dang-nhap").permitAll()
//                .defaultSuccessUrl("/trang-chu")
//                .failureUrl("/dang-nhap?surcess=fail")
//                .loginProcessingUrl("/j_spring_security_check");
//    }
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
//
//        auth.userDetailsService(customerUserDetailService).passwordEncoder(passwordEncoder());
//    }
//}
