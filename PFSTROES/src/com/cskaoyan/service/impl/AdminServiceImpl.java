package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.dao.AdminDao;
import com.cskaoyan.service.AdminService;

import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/10 11:07
 **/
public class AdminServiceImpl implements AdminService {
    AdminDao adminDao =new AdminDao();
    @Override
    public boolean addAdmin(String username, String password) {
        return adminDao.addAdmin(username,password);
    }

    @Override
    public List<Admin> findAllAdmin() {
        return adminDao.findAllAdmin();
    }

    @Override
    public boolean updateAdmin(String username, String password) {
        return adminDao.updateAdmin(username,password);
    }

    @Override
    public Admin login(String username, String password) {
        return adminDao.login(username,password);
    }
}
