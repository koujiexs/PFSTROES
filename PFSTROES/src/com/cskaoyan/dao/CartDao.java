package com.cskaoyan.dao;

import com.cskaoyan.bean.Product;
import com.cskaoyan.bean.Shoppingcart;
import com.cskaoyan.bean.Shoppingitem;
import com.cskaoyan.utils.SJK;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/15 11:37
 **/
public class CartDao {
    public Shoppingcart findCart(int uid) {
        Shoppingcart shoppingcart = null;
        try {
            shoppingcart = SJK.queryRunner.query("select * from `shoppingcar` where uid = ?;", new BeanHandler<Shoppingcart>(Shoppingcart.class), uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (shoppingcart == null) {
            try {
                SJK.queryRunner.update("INSERT INTO `shoppingcar` values ( NULL ,?  );", uid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                shoppingcart = SJK.queryRunner.query("select * from `shoppingcar` where uid = ?;", new BeanHandler<Shoppingcart>(Shoppingcart.class), uid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        int sid = shoppingcart.getSid();
        List<Shoppingitem> shoppingitems = null;
        try {
            shoppingitems = SJK.queryRunner.query("select * from `shoppingitem` where sid = ?;", new BeanListHandler<Shoppingitem>(Shoppingitem.class), sid);
            for (Shoppingitem shoppingitem:shoppingitems) {
                shoppingitem.setProduct(SJK.queryRunner.query("select * from product where pid =?", new BeanHandler<Product>(Product.class), shoppingitem.getPid()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        shoppingcart.setShoppingitems(shoppingitems);
        return shoppingcart;
    }

    public boolean addCart(String uid, String pid,String snum) {
        int uid2 = Integer.parseInt(uid);
        int snum2=Integer.parseInt(snum);
        boolean pd = false;
        Shoppingcart shoppingcart = findCart(uid2);
        List<Shoppingitem> shoppingitems = shoppingcart.getShoppingitems();
        for (Shoppingitem item : shoppingitems) {
            if (pid.equals(item.getPid())) {
                item.setSnum(item.getSnum() + snum2);
                try {
                    SJK.queryRunner.update("UPDATE `shoppingitem` SET snum=? where itemid=?",item.getSnum(),item.getItemid());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                pd = true;
                break;
            }
        }
        if(pd==false){
            try {
                SJK.queryRunner.update("INSERT INTO `shoppingitem` values ( NULL ,? ,?,? );", shoppingcart.getSid(),pid,snum2);
                shoppingitems.add(SJK.queryRunner.query("select * from `shoppingitem` where pid=?;", new BeanHandler<Shoppingitem>(Shoppingitem.class),pid));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean delItem(String uid, String itemid) {

        try {
            SJK.queryRunner.update("DELETE from `shoppingitem` where itemid =?",itemid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
