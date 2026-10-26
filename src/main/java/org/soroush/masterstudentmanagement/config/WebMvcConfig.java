
package org.soroush.masterstudentmanagement.config;

import lombok.RequiredArgsConstructor;

import org.soroush.masterstudentmanagement.interceptor.RequestLoggingInterceptor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final RequestLoggingInterceptor requestLoggingInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(requestLoggingInterceptor)
                .addPathPatterns(
                        "/students",
                        "/students/**",
                        "/subjects",
                        "/subjects/**",
                        "/masters",
                        "/masters/**"
                );
    }
}
