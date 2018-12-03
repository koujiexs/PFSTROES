package com.cskaoyan.dao;

import com.cskaoyan.bean.Order;
import com.cskaoyan.bean.Orderitem;
import com.cskaoyan.bean.User;
import com.cskaoyan.utils.SJK;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/15 16:30
 **/
public class OrderDao {
    public boolean placeOrder(Order order) {
        order.setOid((int) System.currentTimeMillis());
        try {
            SJK.queryRunner.update("INSERT INTO `order` values ( ? ,? ,? ,? ,? ,? ,? ,? );",order.getOid(),order.getMoney(),order.getRecipients(),order.getTel(),order.getAddress(),order.getState(),order.getOrdertime(),order.getUid());

            for (Orderitem orderitem:order.getOrderitems()) {
                SJK.queryRunner.update("INSERT INTO `orderitem` values ( NULL ,? ,? ,? );",order.getOid(),orderitem.getPid(),orderitem.getBuynum());
                SJK.queryRunner.update("UPDATE product SET pnum = pnum-? where pid=?;",orderitem.getBuynum(),orderitem.getPid());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<Order> findAllOrder() {
        List<Order> orders = null;
        try {
            orders = SJK.queryRunner.query("select * from `order`;", new BeanListHandler<Order>(Order.class));
            for (Order order:orders) {
                User user = SJK.queryRunner.query("select * from `user` where uid =?;", new BeanHandler<User>(User.class), order.getUid());
                order.setUser(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public List<Order> myoid(int uid) {
        List<Order> orders = null;
        try {
            orders = SJK.queryRunner.query("select * from `order` where uid=?;", new BeanListHandler<Order>(Order.class),uid);
            for (Order order:orders) {
                User user = SJK.queryRunner.query("select * from `user` where uid =?;", new BeanHandler<User>(User.class), order.getUid());
                order.setUser(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean cancelOrder(int oid, int state) {
        try {
            Order order=SJK.queryRunner.query("select * from `order` where oid=?;",new BeanHandler<Order>(Order.class),oid);
            if(order.getState()==1&&state==0){
                //代码补充，事务处理
                List<Orderitem> orderitems = SJK.queryRunner.query("select * from `orderitem` where oid=?;", new BeanListHandler<Orderitem>(Orderitem.class), oid);
                for(Orderitem orderitem:orderitems){
                SJK.queryRunner.update("UPDATE product SET pnum = pnum+? where pid=?;",orderitem.getBuynum(),orderitem.getPid());}
            }
            SJK.queryRunner.update("UPDATE `order` SET state=? where oid=?",state,oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean delOrder(int oid) {
        try {
            SJK.queryRunner.update("DELETE from `orderitem` where oid =?",oid);
            SJK.queryRunner.update("DELETE from `order` where oid =?",oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Order orderDetail(int oid) {
        Order order=null;
        try {
            order=SJK.queryRunner.query("select * from `order` where oid=?;",new BeanHandler<Order>(Order.class),oid);
            order.setOrderitems(SJK.queryRunner.query("select * from `orderitem` where oid=?;",new BeanListHandler<Orderitem>(Orderitem.class),oid));
            User user = SJK.queryRunner.query("select * from `user` where uid =?;", new BeanHandler<User>(User.class), order.getUid());
            order.setUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public boolean delItemid(int itemid) {
        try {
            SJK.queryRunner.update("DELETE from `orderitem` where itemid =?",itemid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
