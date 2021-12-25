//package com.java.project3.configuration;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.CacheControl;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//import org.springframework.web.servlet.resource.VersionResourceResolver;
//
//import java.util.concurrent.TimeUnit;
//
//@Configuration
//public class MvcInterceptorConfig implements WebMvcConfigurer {
//
//    /**
//     * Function description
//     * <p>
//     * .addFixedVersionStrategy("v1.0.1", "/**") Add version number method for manual
//     * .addContentVersionStrategy("/**") md5 mode
//     * </p>
//     *
//     * @param registry registry
//     * @return void
//     * @author wandoupeas
//     * @date 2019-11-06
//     * @since 2019-11-06
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/js/**")
//                .addResourceLocations("classpath:/static/js/")
//                .setCachePeriod(31536000)
//                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
//                .resourceChain(false)
//                .addResolver(new VersionResourceResolver()
//                        // .addFixedVersionStrategy("v1.0.1", "/**")
//                        .addContentVersionStrategy("/**")
//                );
//        registry.addResourceHandler("/css/**")
//                .addResourceLocations("classpath:/static/css/")
//                .setCachePeriod(31536000)
//                .setCacheControl(CacheControl.maxAge(365, TimeUnit.DAYS))
//                .resourceChain(false)
//                .addResolver(new VersionResourceResolver()
//                        // .addFixedVersionStrategy("v1.0.1", "/**")
//                        .addContentVersionStrategy("/**")
//                );
//    }
//}