package com.cskaoyan.service;

import com.cskaoyan.bean.User;

import java.util.List;

public interface UserService {
    User isUserUsernameAvailable(String username);

    boolean register(User user);

    User login(String username, String password);

    boolean VerifyEmail(String uid,String uuid);

    boolean update(User user);

    List<User> findAllUser();
}
