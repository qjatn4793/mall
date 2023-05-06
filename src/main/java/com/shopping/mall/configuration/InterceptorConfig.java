package com.shopping.mall.configuration;

import com.shopping.mall.configuration.interceptor.UserLoginCheckInterceptor;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@AllArgsConstructor
@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
    UserLoginCheckInterceptor userLoginCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userLoginCheckInterceptor)
                .addPathPatterns("/userMypage")
                .addPathPatterns("/buy")
                .addPathPatterns("/uploadProfileImg")
                .addPathPatterns("/paymentHistory");

    }
}
