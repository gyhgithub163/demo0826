package com.example.demo0826.dao;
import com.example.demo0826.entity.User;
import org.apache.ibatis.annotations.Param;


/*
* 持久层
* 存放接口，实现类在resources的mapper目录下。
* 通过实现类的namespace的值产生相互关联  如:<mapper namespace="com.example.demo0826.dao.UserMapping">
* */
public interface UserMapping {
    //通过ID查询信息
    public User selectById(int id);
    //插入用户信息
    public int insert(User user);
    //更新用户信息
    public int update(User user);
    //删除用户信息
    public int delete(User user);
}
