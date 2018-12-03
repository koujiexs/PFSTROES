package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Category;
import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.utils.PageCategoryInfo;

import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/7 16:26
 **/
public class CategoryServiceImpl implements com.cskaoyan.service.CategoryService {
    CategoryDao categoryDao =new CategoryDao();
    private static final int PAGE_COUNT=3;
    @Override
    public  boolean addCategory(Category category ){
        return categoryDao.addCategory(category);
    }
    @Override
    public  List<Category> findAllCategory(){
        return categoryDao.findAllCategory();
    }

    @Override
    public boolean deleteCategory(String cid) {
        categoryDao.deleteCategory(cid);
        return true;
    }

    @Override
    public boolean updateCategory(String cid, String cname) {
        categoryDao.updateCategory(cid,cname);
        return true;
    }

    @Override
    public PageCategoryInfo findPageCategory(String num) {
        PageCategoryInfo pageCategoryInfo=new PageCategoryInfo();
        int pageNum=Integer.parseInt(num);
        int limit =PAGE_COUNT;
        int offset=(pageNum-1)*PAGE_COUNT;
        List<Category> categories=categoryDao.findOnePageCategoryList(limit,offset);
        pageCategoryInfo.setDQnum(pageNum);
        int zjnum=categoryDao.findTotalNumber();
        pageCategoryInfo.setZongJLnum(zjnum);
        pageCategoryInfo.setPageList(categories);
        int ZYnum =zjnum/PAGE_COUNT+(zjnum%PAGE_COUNT==0?0:1);
        pageCategoryInfo.setZYnum(ZYnum);
        pageCategoryInfo.setQYnum(pageNum==1?pageNum:pageNum-1);
        pageCategoryInfo.setNaxtNum(pageNum==ZYnum?ZYnum:pageNum+1);



        return pageCategoryInfo;
    }
}
