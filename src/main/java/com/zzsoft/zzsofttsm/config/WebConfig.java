package com.zzsoft.zzsofttsm.config;

import com.zzsoft.zzsofttsm.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 登录拦截
//        registry.addInterceptor(new LoginInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/shrioLogin","/register","/login.html","/register.html")
//                .excludePathPatterns("/login","/login2","/logout")
//                .excludePathPatterns("/css/**","/js/**","/img/**","/layui/**","/resource/**")
//                .excludePathPatterns("/swagger-resources/**","/v2/**","/webjars/**","/swagger-ui.html/**");

    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDateConverter());     // 添加自定义时间转换器
    }
}

