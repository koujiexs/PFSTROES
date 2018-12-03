package com.cskaoyan.service;

import com.cskaoyan.bean.Category;
import com.cskaoyan.dao.CategoryDao;
import com.cskaoyan.utils.PageCategoryInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CategoryService {
    public boolean addCategory(Category category );
    public List<Category> findAllCategory();
    public boolean deleteCategory(String cid);
    public boolean updateCategory(String cid ,String cname);

    public PageCategoryInfo findPageCategory(String num);
}
