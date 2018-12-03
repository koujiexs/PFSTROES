package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.service.impl.CategoryServiceImpl;
import com.cskaoyan.utils.PageCategoryInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet",urlPatterns = "/CategoryServlet")
public class CategoryServlet extends HttpServlet {
    CategoryService categoryServiceJK= new CategoryServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "addCategory":
                addCategory(request,response);
                break;
            case "findAllCategory":
                findAllCategory(request,response);
                break;
            case "deleteCategory":
                String cid = request.getParameter("cid");
                deleteCategory(cid,request,response);
                break;
            case "updateCategory":
                String cid2 =request.getParameter("cid");
                String cname=request.getParameter("cname");
                updateCategory(cid2,cname,request,response);
                break;
            case "findCategory":
                findCategory(request,response);
                break;
        }
    }

    private void findCategory(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        List<Category> categories = categoryServiceJK.findAllCategory();
        session.setAttribute("categories",categories);
        response.sendRedirect("/admin/product/addProduct.jsp");
    }

    private boolean updateCategory(String cid2, String cname,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        categoryServiceJK.updateCategory(cid2,cname);
        findAllCategory(request,response);
        return true;
    }

    private boolean deleteCategory(String cid,HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        categoryServiceJK.deleteCategory(cid);
        findAllCategory(request,response);
        return true;
    }

    private void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String num = request.getParameter("num");
        if(num!=null&&!num.isEmpty()){
            PageCategoryInfo pageCategoryInfo=categoryServiceJK.findPageCategory(num);
            session.setAttribute("pageinfo",pageCategoryInfo);
        }
//        List<Category> categories = categoryServiceJK.findAllCategory();
//        session.setAttribute("categories",categories);
        response.sendRedirect("/admin/category/categoryList.jsp");
    }


    private boolean addCategory(HttpServletRequest request, HttpServletResponse response) {
        String cname = request.getParameter("cname");
        try {
            response.getWriter().println("添加成功");
            response.setHeader("Refresh", "2;url=/admin/category/addCategory.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return categoryServiceJK.addCategory(new Category(0,cname));
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
