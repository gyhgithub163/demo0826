package com.example.demo0826.z_customTool.monitor.config;

import com.example.demo0826.z_customTool.monitor.interceptor.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
/*
拦截器配置文件
*/

//配置文件的作用
//1.拦截指定地址的请求
//2.将拦截器注入ioc容器
//3.自定义拦截器类必须实现WebMvcConfigurer接口
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");    // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
    }
    //将AuthenticationInterceptor注入到ioc容器。
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
}
