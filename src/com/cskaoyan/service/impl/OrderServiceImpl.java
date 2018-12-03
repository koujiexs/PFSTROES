package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Order;
import com.cskaoyan.dao.OrderDao;
import com.cskaoyan.service.OrderService;

import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/15 16:29
 **/
public class OrderServiceImpl implements OrderService {
    OrderDao orderDao=new OrderDao();
    @Override
    public boolean placeOrder(Order order) {
        return orderDao.placeOrder(order);
    }

    @Override
    public List<Order> findAllOrder() {
        return orderDao.findAllOrder();
    }

    @Override
    public List<Order> myoid(int uid) {
        return orderDao.myoid(uid);
    }

    @Override
    public boolean cancelOrder(int oid, int state) {
        return orderDao.cancelOrder(oid,state);
    }

    @Override
    public boolean delOrder(int oid) {
        return orderDao.delOrder(oid);
    }

    @Override
    public Order orderDetail(int oid) {
        return orderDao.orderDetail(oid);
    }

    @Override
    public boolean delItemid(int itemid) {
        return orderDao.delItemid(itemid);
    }
}
