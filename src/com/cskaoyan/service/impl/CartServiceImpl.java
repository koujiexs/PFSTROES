package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Shoppingcart;
import com.cskaoyan.dao.CartDao;
import com.cskaoyan.service.CartService;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/15 11:35
 **/
public class CartServiceImpl implements CartService {
    CartDao cartDao=new CartDao();
    @Override
    public Shoppingcart findCart(int uid) {
        return cartDao.findCart(uid);
    }

    @Override
    public boolean addCart(String uid, String pid,String snum) {
        return cartDao.addCart(uid,pid,snum);
    }

    @Override
    public boolean delItem(String uid, String itemid) {
        return cartDao.delItem(uid, itemid);
    }
}
