package com.cskaoyan.dao;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.utils.SJK;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/10 11:08
 **/
public class AdminDao {
    public boolean addAdmin(String username, String password) {
        try {
            SJK.queryRunner.update("INSERT INTO `admin` values ( NULL , ? , ? );",username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<Admin> findAllAdmin() {
        List<Admin> query=null;
        try {
            query = SJK.queryRunner.query("select * from `admin`;", new BeanListHandler<Admin>(Admin.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    public boolean updateAdmin(String username, String password) {
        try {
            int update = SJK.queryRunner.update("UPDATE `admin` SET password=? where username=?;", password,username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Admin login(String username, String password) {
        Admin query=null;
        try {
            query = SJK.queryRunner.query("select * from `admin` where username = ? and password = ? ;", new BeanHandler<Admin>(Admin.class), username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }
}
