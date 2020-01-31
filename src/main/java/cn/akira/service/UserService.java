package cn.akira.service;

import cn.akira.pojo.ResponseData;
import cn.akira.pojo.User;

import java.util.List;

//@Service("userService")
public interface UserService {
    /**
     * 根据User信息去检查数据库是否存在用户以及登录信息是否正确
     *
     * @param user user from view
     * @return user from db
     */
    User getUser(User user) throws Exception;

    User getUserSessionCols(User sessionUser) throws Exception;
}