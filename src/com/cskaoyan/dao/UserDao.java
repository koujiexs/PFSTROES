package com.cskaoyan.dao;

import com.cskaoyan.bean.User;
import com.cskaoyan.utils.SJK;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.SimpleFormatter;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/13 22:18
 **/
public class UserDao {
    public User isUserUsernameAvailable(String username) {
        User user = null;
        try {
            user = SJK.queryRunner.query("select * from `user` where username = ?;", new BeanHandler<User>(User.class), username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean register(User user) {
        int i = 0;
        try {
            i = SJK.queryRunner.update("INSERT INTO `user` values ( NULL ,? ,? ,? ,? ,? ,?, ? ,?);", user.getUsername(), user.getNickname(), user.getPassword(), user.getEmail(), user.getBirthday(), user.getUpdatetime(), user.getStatus(), user.getUuid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (i > 0) {
            return true;

        } else {
            return false;
        }
    }

    public User login(String username, String password) {
        User user = null;
        try {
            user = SJK.queryRunner.query("select * from `user` where username = ? and password = ? ;", new BeanHandler<User>(User.class), username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public boolean VerifyEmail(String uid, String uuid) {
        int i = 0;
        try {
            i = SJK.queryRunner.update("UPDATE `user` SET `status`=? where uid=? and uuid=?;", 1, uid, uuid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (i > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean update(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        if(email==null){
            try {
                SJK.queryRunner.update("UPDATE `user` SET nickname=?,birthday=? where uid=?;",user.getNickname(),user.getBirthday(),user.getUid());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else {
            try {
                SJK.queryRunner.update("UPDATE `user` SET nickname=?,birthday=?,email=?,uuid=?,`status`=? where uid=?;",user.getNickname(),user.getBirthday(),user.getEmail(),user.getUuid(),user.getStatus(),user.getUid());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;

    }

    public List<User> findAllUser() {
        List<User> users=null;
        try {
            users=SJK.queryRunner.query("select * from `user`;",new BeanListHandler<User>(User.class));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
