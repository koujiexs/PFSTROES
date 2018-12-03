package com.cskaoyan.dao;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.Product;
import com.cskaoyan.utils.SJK;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/9 15:54
 **/
public class ProductDao {
    public boolean addProduct(Product product) {
        try {
            SJK.queryRunner.update("INSERT into product values (?,?,?,?,?,?,?,?);", product.getPid(), product.getPname(), product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getImgurl(), product.getDesc());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public List<Product> findAllProduct() {
        try {
            List<Product> query = SJK.queryRunner.query("select * from product", new BeanListHandler<Product>(Product.class));
            for (Product p : query) {
                int cid = p.getCid();
                Category query1 = SJK.queryRunner.query("select * from category where cid =?", new BeanHandler<Category>(Category.class), cid);
                p.setCategory(query1);
            }
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteOne(String pid) {
        try {
            SJK.queryRunner.update("DELETE from product where pid =?", pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public Product findProductByUpdate(String pid) {
        Product product = null;
        try {
            product = SJK.queryRunner.query("select * from product where pid =?", new BeanHandler<Product>(Product.class), pid);
            int cid = product.getCid();
            Category query1 = SJK.queryRunner.query("select * from category where cid =?", new BeanHandler<Category>(Category.class), cid);
            product.setCategory(query1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public boolean updateProduct(Product product) {

        if (product.getImgurl() != null) {
            try {
                SJK.queryRunner.update("UPDATE product SET pname = ? , estoreprice = ? , markprice = ? , pnum = ? , cid = ? , imgurl = ?,`desc`=?  where pid=?;", product.getPname(), product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getImgurl(), product.getDesc(), product.getPid());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                SJK.queryRunner.update("UPDATE product SET pname = ? , estoreprice = ? , markprice = ? , pnum = ? , cid = ?，`desc`=?   where pid=?;", product.getPname(), product.getEstoreprice(), product.getMarkprice(), product.getPnum(), product.getCid(), product.getDesc(), product.getPid());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public List<Product> findOneProduct(int limit, int offset) {
        List<Product> query = null;
        try {
            query = SJK.queryRunner.query("select * from product limit ? offset ?", new BeanListHandler<Product>(Product.class), limit, offset);
            for (Product p : query) {
                int cid = p.getCid();
                Category query1 = SJK.queryRunner.query("select * from category where cid =?", new BeanHandler<Category>(Category.class), cid);
                p.setCategory(query1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    public int findTotalNumber() {
        Long query = 0L;
        try {
            query = (Long) SJK.queryRunner.query("select count(*) from product", new ScalarHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query.intValue();
    }

    public List<Product> SearchProduct(String id, String cid, String name, String minprice, String maxprice) {
        List<Product> productList = null;
        String sql = "select * from product where 1=1";
        ArrayList mysql = new ArrayList();
        if (id != null && !id.isEmpty()) {
            sql = sql + " and pid=?";
            mysql.add(id);
        }
        if (cid != null && !cid.isEmpty()) {
            sql = sql + " and cid=?";
            mysql.add(cid);
        }
        if (name != null && !name.isEmpty()) {
            sql = sql + " and pname like ?";
            mysql.add(name);
        }
        if (minprice != null && !minprice.isEmpty()) {
            sql = sql + " and estoreprice > ?";
            mysql.add(minprice);
        }
        if (maxprice != null && !maxprice.isEmpty()) {
            sql = sql + " and estoreprice < ?";
            mysql.add(maxprice);
        }
        Object[] Canshu = mysql.toArray();
        try {
            productList = SJK.queryRunner.query(sql, new BeanListHandler<Product>(Product.class), Canshu);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public Product findProductByPid(String pid) {
        Product product = null;
        try {
            product = SJK.queryRunner.query("select * from product where pid =?", new BeanHandler<Product>(Product.class), pid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    public List<Product> findProductByCid(String cid) {
        List<Product> productList = null;
        try {
            productList = SJK.queryRunner.query("select * from product where cid =?", new BeanListHandler<Product>(Product.class), cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> findProductsByName(String pname) {
        List<Product> productList = null;
        try {
            productList = SJK.queryRunner.query("select * from product where pname like ?", new BeanListHandler<Product>(Product.class), "%" + pname + "%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    public List<Product> topProducts() {
        List<Product> productList = null;
        try {
            productList = SJK.queryRunner.query("select * from product where pid like ?", new BeanListHandler<Product>(Product.class), "k%");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}