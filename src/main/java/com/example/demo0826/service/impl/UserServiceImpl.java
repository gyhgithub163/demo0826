package com.example.demo0826.service.impl;

import com.example.demo0826.dao.UserMapping;
import com.example.demo0826.entity.User;
import com.example.demo0826.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
//服务层实现类需加@Service注解，他的作用是将类标识为一个服务，自动在IOC容器中进行注册。
//服务层实现类需实现服务层接口
@Service
public class UserServiceImpl implements UserService {
    //一般来说Controller层和Service层是有关联的，service层和dao层是有关联的。
    // 我们使用@Autowired注解或@Resource注解后，可以在controller层中装配Service，在Service层中装配Reponsitory(叫法是dao层或持久层)
    @Resource
    UserMapping userDao;

    //实现了用户数据插入
    @Override
    public int serUserInsert(User user) {
        return userDao.insert(user);
    }
    //实现了用户数据删除
    @Override
    public int serUserDelete(User user) {
        return  userDao.delete(user);
    }
    //实现了用户数据更新
    //@Override
    public int serUserUpdate(User user) {

    return userDao.update(user);
    }
    //实现了用户数据查询
    @Override
    public User serUserSelect(int id) {
        User user=userDao.selectById(id);
        return user;
    }
}
