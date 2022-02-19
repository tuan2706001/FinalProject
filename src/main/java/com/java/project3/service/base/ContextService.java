//package com.java.project3.service.base;
//
//import com.java.project3.domain.User;
//import com.java.project3.dto.base.RequestContext;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.authentication.RememberMeAuthenticationToken;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class ContextService {
//    public User getCurrentUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication instanceof UsernamePasswordAuthenticationToken) {
//            if (authentication.isAuthenticated()) {
//                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                Object principal = auth.getPrincipal();
//                if (principal instanceof User) {
//                    return (User) principal;
//                } else if (principal instanceof RequestContext) {
//                    RequestContext requestContext = (RequestContext) auth.getPrincipal();
//                    return requestContext.getUser();
//                } else {
//                    return null;
//                }
//            }
//        } else if (authentication instanceof RememberMeAuthenticationToken) {
//            if (authentication.isAuthenticated()) {
//                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                Object principal = auth.getPrincipal();
//                if (principal instanceof User) {
//                    return (User) principal;
//                } else if (principal instanceof RequestContext) {
//                    RequestContext requestContext = (RequestContext) auth.getPrincipal();
//                    return requestContext.getUser();
//                } else {
//                    return null;
//                }
//            }
//        } else if (authentication instanceof AnonymousAuthenticationToken) {
//            AnonymousAuthenticationToken anonymousAuthenticationToken = (AnonymousAuthenticationToken) authentication;
//            return null;
//        }
//        return null;
//    }
//}
