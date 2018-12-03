package com.cskaoyan.controller;

import com.cskaoyan.bean.Product;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchProductServlet",urlPatterns = "/SearchProductServlet")
public class SearchProductServlet extends HttpServlet {
    ProductService productService =new ProductServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("id");
        String cid = request.getParameter("cid");
        String name = request.getParameter("name");
        String minprice = request.getParameter("minprice");
        String maxprice = request.getParameter("maxprice");
        List<Product> productList=productService.SearchProduct(id,cid,name,minprice,maxprice);
        HttpSession session = request.getSession();
        session.setAttribute("records",productList);
        response.sendRedirect("/admin/product/searchProductList.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
