package com.example.demo0826.service;

import com.example.demo0826.entity.User;

import java.io.UnsupportedEncodingException;

/*
 * Token服务接口
 * 对应的实现类在service->impl文件夹下。
 * */
public interface TokenService {
    //获得token字符串。
    public String getToken(User user);
    //验证token是否过期。
    public Boolean verifyToken(String token) throws UnsupportedEncodingException;

}
