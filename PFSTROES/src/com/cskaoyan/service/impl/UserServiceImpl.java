package com.cskaoyan.service.impl;

import com.cskaoyan.bean.User;
import com.cskaoyan.dao.UserDao;
import com.cskaoyan.service.UserService;

import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/13 22:17
 **/
public class UserServiceImpl implements UserService {
    UserDao userDao=new UserDao();
    @Override
    public User isUserUsernameAvailable(String username) {
        return userDao.isUserUsernameAvailable(username);
    }

    @Override
    public boolean register(User user) {
        return userDao.register(user);
    }

    @Override
    public User login(String username, String password) {
        return userDao.login(username,password);
    }

    @Override
    public boolean VerifyEmail(String uid,String uuid) {
        return userDao.VerifyEmail(uid,uuid);
    }

    @Override
    public boolean update(User user) {
        return userDao.update(user);
    }

    @Override
    public List<User> findAllUser() {
        return userDao.findAllUser();
    }
}
