package com.cskaoyan.controller;

import com.cskaoyan.bean.Shoppingcart;
import com.cskaoyan.bean.User;
import com.cskaoyan.service.CartService;
import com.cskaoyan.service.impl.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "CartServlet",urlPatterns = "/user/CartServlet")
public class CartServlet extends HttpServlet {
    CartService cartService =new CartServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "findCart":
                findCart(request,response);
                break;
            case "addCart":
                addCart(request,response);
                break;
            case "delItem":
                delItem(request,response);
                break;
        }
    }

    private void delItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uid = request.getParameter("uid");
        String itemid=request.getParameter("itemid");
        cartService.delItem(uid,itemid);
        response.sendRedirect("/user/CartServlet?op=findCart");
    }

    private void addCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uid = request.getParameter("uid");
        String pid = request.getParameter("pid");
        String snum=request.getParameter("snum");
       if(snum == null || snum.length() == 0){
            snum="1";
        }
        cartService.addCart(uid,pid,snum);
        response.sendRedirect("/user/CartServlet?op=findCart");
    }

    private void findCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(user!=null) {
            Shoppingcart shoppingcart = cartService.findCart(user.getUid());
            session.setAttribute("shoppingcart", shoppingcart);
            response.sendRedirect("/user/shoppingcart.jsp");
        }else {
            response.getWriter().println("请先登录");
            response.setHeader("Refresh", "2;url=/user/login.jsp");
        }
    }
}
