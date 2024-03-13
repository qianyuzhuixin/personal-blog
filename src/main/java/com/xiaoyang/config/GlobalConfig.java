package com.xiaoyang.config;

import com.xiaoyang.interceptor.AdminInterceptor;
import com.xiaoyang.interceptor.UserInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 全局配置
 * @Author: xiaomei
 * @Date: 2024/1/1 001
 */
@Configuration
public class GlobalConfig implements WebMvcConfigurer {

    // 管理员拦截器装配
    @Bean
    public HandlerInterceptor getAdminInterceptor() {
        return new AdminInterceptor();
    }

    // 用户拦截器装配
    @Bean
    public HandlerInterceptor getUserInterceptor() {
        return new UserInterceptor();
    }

    // 添加全局拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 管理员拦截器
        registry.addInterceptor(getAdminInterceptor()).addPathPatterns("/xyadmin/**")
                .excludePathPatterns("/xyadmin/login", "/xyadmin/logout", "/xyadmin/adminLogin");
        // 普通用户拦截器
        registry.addInterceptor(getUserInterceptor()).addPathPatterns("/user/**")
                .excludePathPatterns("/user/logInOrSignIn", "/user/logOut", "/user/login", "/user/signIn", "/user/showArticle", "/user/showComment", "/user/showRecoverComment");
    }

}
