package com.example.demo0826.z_customTool.monitor.interceptor;

import com.example.demo0826.service.TokenService;
import com.example.demo0826.service.UserService;
import com.example.demo0826.z_customTool.annotation.PassToken;
import com.example.demo0826.z_customTool.annotation.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
/*
拦截器实现类
* */
//本类目前实现了preHandle方法，他执行在控制层方法之前。
public class AuthenticationInterceptor implements HandlerInterceptor {
    public static String token;
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {

        //  String token = httpServletRequest.getHeader("access-token");// 从 http 请求头中取出 token
        //获得浏览器保存的cookie信息。
        Cookie[] cookies = httpServletRequest.getCookies();
        if(cookies != null && cookies.length > 0){
                for(Cookie cookie:cookies){
                    token=cookie.getValue();
            }
        }

        System.out.println("ClientToken:  "+token);

        // 如果不是映射到方法直接通过
        if(!(object instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();
        //检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new RuntimeException("无token，请重新登录");
                }else if(tokenService.verifyToken(token)==true){
                    throw new RuntimeException("token已过期，请重新登录");
                }

                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }
}
