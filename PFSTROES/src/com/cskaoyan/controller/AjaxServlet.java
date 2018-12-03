package com.cskaoyan.controller;

import com.cskaoyan.bean.User;
import com.cskaoyan.service.UserService;
import com.cskaoyan.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AjaxServlet",urlPatterns = "/admin/AjaxServlet")
public class AjaxServlet extends HttpServlet {
    UserService userService =new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String ajax = request.getParameter("ajax");
        switch (ajax){
            case "isUserUsernameAvailable":
                isUserUsernameAvailable(request,response);
                break;
        }
    }

    private void isUserUsernameAvailable(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        User user =userService.isUserUsernameAvailable(username);
        if(user==null){
            response.getWriter().print("true");
        }
    }
}
