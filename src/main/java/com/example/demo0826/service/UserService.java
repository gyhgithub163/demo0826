package com.example.demo0826.service;

import com.example.demo0826.entity.User;
/*
* 服务层
* 存放接口，有对应的实现类，在service->impl文件夹下。
* */
public interface UserService {
    //用户信息新建服务
    public int serUserInsert(User user);
    //用户信息删除服务
    public int serUserDelete(User user);
    //用户信息更新服务
    public int serUserUpdate(User user);
    //用户信息查询服务
    public User serUserSelect(int id);
}
