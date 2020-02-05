package cn.akira.service.impl;

import cn.akira.mapper.UserMapper;
import cn.akira.pojo.User;
import cn.akira.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(User user) throws Exception {
        return this.getUserSessionCols(user);
    }

    @Override
    public User getUserSessionCols(User sessionUser) throws Exception {
        return userMapper.querySessionCols(sessionUser);
    }

    @Override
    public Integer queryCountByConditions(User user) {
        return userMapper.queryCountByConditions(user);
    }

    @Override
    public int insert(User user) throws Exception {
        return userMapper.insert(user);
    }
}
