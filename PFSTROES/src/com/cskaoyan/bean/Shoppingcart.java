package com.cskaoyan.bean;

import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/15 11:07
 **/
public class Shoppingcart {
    int sid;
    int uid;
    User user;
    List<Shoppingitem> shoppingitems;
    public Shoppingcart(){
    }
    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Shoppingitem> getShoppingitems() {
        return shoppingitems;
    }

    public void setShoppingitems(List<Shoppingitem> shoppingitems) {
        this.shoppingitems = shoppingitems;
    }
}
