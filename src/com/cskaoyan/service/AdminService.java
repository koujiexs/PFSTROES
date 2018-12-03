package com.cskaoyan.service;

import com.cskaoyan.bean.Admin;

import java.util.List;

public interface AdminService {
    public boolean addAdmin(String username, String password);

    public List<Admin> findAllAdmin();

    public boolean updateAdmin(String username, String password);

    Admin login(String username, String password);
}
