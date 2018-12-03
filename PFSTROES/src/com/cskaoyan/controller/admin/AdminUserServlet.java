package com.cskaoyan.controller.admin;

import com.cskaoyan.bean.User;
import com.cskaoyan.service.UserService;
import com.cskaoyan.service.impl.UserServiceImpl;
import com.cskaoyan.utils.MD5Util;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "AdminUserServlet",urlPatterns = "/UserServlet")
public class AdminUserServlet extends HttpServlet {
    UserService userService=new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "adduser":
                adduser(request,response);
        }
    }

    private void adduser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user =new User();
        Map<String,Object> map=new HashMap<>();
        String username = request.getParameter("username");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String password = MD5Util.getMD5(username,request.getParameter("password"));
        String birthday = request.getParameter("birthday");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String updatetime=(df.format(new Date()));
        map.put("status",0);
        map.put("username",username);
        map.put("nickname",nickname);
        map.put("email",email);
        map.put("password",password);
        map.put("birthday",birthday);
        map.put("updatetime",updatetime);
        map.put("uuid", UUID.randomUUID());
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        userService.register(user);
        response.getWriter().println("注册成功，即将跳转到登录页");
        response.setHeader("Refresh", "2;url=/UserServlet?op=findAllUser&num=1");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "findAllUser":
                findAllUser(request,response);
                break;
        }
    }

    private void findAllUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> users=userService.findAllUser();
        request.setAttribute("records",users);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/user/userList.jsp");
        requestDispatcher.forward(request,response);
    }
}
