package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.Product;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.CategoryServiceImpl;
import com.cskaoyan.service.impl.ProductServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "MainServlet",urlPatterns = "/MainServlet")
public class MainServlet extends HttpServlet {
    ProductService productService =new ProductServiceImpl();
    CategoryService categoryService =new CategoryServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Product> allProduct = productService.findAllProduct();
        List<Category> allCategory = categoryService.findAllCategory();
        List<Product> topProducts =productService.topProducts();
        ServletContext servletContext = request.getServletContext();
        servletContext.setAttribute("categories",allCategory);
        request.setAttribute("topProducts",topProducts);
        request.setAttribute("hotProducts",allProduct);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
        requestDispatcher.forward(request,response);
    }
}
