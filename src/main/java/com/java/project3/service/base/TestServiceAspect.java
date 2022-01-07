//package com.java.project3.service.base;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//
//@Aspect
//@Configuration
//public class TestServiceAspect {
//    private Logger logger = LoggerFactory.getLogger(TestServiceAspect.class);
//
//    @Before("execution(* com.bkh.vnoip.service.*.*(..))")
//    public void before(JoinPoint joinPoint){
//        logger.info(" before called " + joinPoint.toString());
//    }
//}
