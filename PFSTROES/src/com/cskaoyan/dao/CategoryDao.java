package com.cskaoyan.dao;

import com.cskaoyan.bean.Category;
import com.cskaoyan.utils.SJK;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/7 16:19
 **/
public class CategoryDao {


    public  boolean addCategory(Category category) {
        try {
            SJK.queryRunner.update("INSERT into category values (null,?);", category.getCname());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public  List<Category> findAllCategory(){
        try {
            List<Category> query = SJK.queryRunner.query("select cid,cname from category", new BeanListHandler<Category>(Category.class));
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void deleteCategory(String cid) {
        try {
            SJK.queryRunner.update("DELETE from category where cid=?",cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateCategory(String cid, String cname) {
        try {
            SJK.queryRunner.update("UPDATE category SET cname=? where cid=?",cname,cid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Category> findOnePageCategoryList(int limit, int offset) {
        List<Category> query=null;
        try {
            query = SJK.queryRunner.query("select cid,cname from category limit ? offset ?", new BeanListHandler<Category>(Category.class),limit,offset);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query;
    }

    public int findTotalNumber() {
        Long query=0L;
        try {
            query= (Long) SJK.queryRunner.query("select count(*) from category",new ScalarHandler());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return query.intValue();
    }
}
