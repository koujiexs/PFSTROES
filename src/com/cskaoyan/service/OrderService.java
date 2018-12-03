package com.cskaoyan.service;

import com.cskaoyan.bean.Order;

import java.util.List;

public interface OrderService {
    boolean placeOrder(Order order);

    List<Order> findAllOrder();

    List<Order> myoid(int uid);

    boolean cancelOrder(int oid, int state);

    boolean delOrder(int oid);

    Order orderDetail(int oid);

    boolean delItemid(int itemid);
}
