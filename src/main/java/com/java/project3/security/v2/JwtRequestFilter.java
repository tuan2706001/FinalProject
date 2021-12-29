package com.java.project3.security.v2;

import com.bkh.vnoip.constant.VINET_URL;
import com.bkh.vnoip.security.UserDetail;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.var;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Optional;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetail jwtUserDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request)
            throws ServletException {

        Optional<String> existPublicUrl = Arrays.stream(VINET_URL.PUBLIC).filter(x -> {
            String removeUrl = x.replace("/**", "");
            return request.getRequestURI().startsWith(removeUrl);
        }).findAny();
        if (existPublicUrl.isPresent()) {
            return true;
        } else return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        String jwtToken = null;
        String username = null;
        Long orgId = null;

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            Optional<Cookie> cookieOptional = Arrays.stream(cookies).filter(x -> x.getName().equals("jwt")).findFirst();
            if (cookieOptional.isPresent()) {
                jwtToken = cookieOptional.get().getValue();
                System.out.println("jwtToken:" + jwtToken);
                try {
                    username = jwtTokenUtil.getUsernameFromToken(jwtToken);
                } catch (IllegalArgumentException e) {
                    System.out.println("Unable to get JWT Token");
                } catch (ExpiredJwtException e) {
                    Cookie sessionCookie = new Cookie("jwt", null);
                    sessionCookie.setPath("/");
                    sessionCookie.setMaxAge(0);
                    response.addCookie(sessionCookie);
                    System.out.println("JWT Token has expired");
                }
            } else {
                logger.warn("JWT Token does not in cookie");
            }
        } else {
            logger.warn("Cookie is null");
        }


        // Verification
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);


            if (cookies != null){
                Comparator<Cookie> comparator = Comparator.comparing(Cookie::getMaxAge);
                var z = Arrays.stream(cookies).filter(p->p.getName().equals("orgId"))
                        .max(comparator);
                if(z.isPresent()) {
                    orgId = Long.parseLong(z.get().getValue());
                };
            }

            // JWT validation is managed using Spring Security
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                System.out.println("jwtToken validateToken");
                HashMap<String, Object> detail = new HashMap<>();
                detail.put("WebAuthenticationDetailsSource", new WebAuthenticationDetailsSource().buildDetails(request));
                detail.put("currentOrgId", orgId);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken
                        .setDetails(detail);
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
