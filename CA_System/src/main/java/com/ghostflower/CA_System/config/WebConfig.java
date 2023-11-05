package com.ghostflower.CA_System.config;

import com.ghostflower.CA_System.intercepter.LoginIntercepter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private LoginIntercepter loginIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录接口和注册接口不执行
        registry.addInterceptor(loginIntercepter).excludePathPatterns("/user/login","/user/register","/index/*","/ca/downloadtest","/user/*");



    }
}
