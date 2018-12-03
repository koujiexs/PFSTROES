package com.cskaoyan.service;

import com.cskaoyan.bean.Shoppingcart;

public interface CartService {
    Shoppingcart findCart(int uid);

    boolean addCart(String uid, String pid,String snum);

    boolean delItem(String uid, String itemid);
}
