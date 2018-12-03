package com.cskaoyan.controller;

import com.cskaoyan.bean.User;
import com.cskaoyan.service.UserService;
import com.cskaoyan.service.impl.UserServiceImpl;
import com.cskaoyan.utils.MD5Util;
import com.cskaoyan.utils.SendJMail;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@WebServlet(name = "UserServlet",urlPatterns = "/user/UserServlet")
public class UserServlet extends HttpServlet {
    UserService userService =new UserServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "register":
                register(request,response);
                break;
            case "login":
                login(request,response);
                break;
            case "update":
                update(request,response);
                break;
        }

    }

    private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        User user2 =new User();
        Map<String,Object> map=new HashMap<>();
        String username = request.getParameter("username");
        String nickname = request.getParameter("nickname");
        String email = request.getParameter("email");
        String birthday = request.getParameter("birthday");
        map.put("uid",user.getUid());
        map.put("username",username);
        map.put("nickname",nickname);
        if(!user.getEmail().equals(email)){
            map.put("email",email);
            map.put("uuid",MD5Util.getMD5(email));
            map.put("status",0);
        }
        map.put("birthday",birthday);
        try {
            BeanUtils.populate(user2, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        userService.update(user2);
        User user3=userService.login(user.getUsername(),user.getPassword());
        session.setAttribute("user",user3);
        response.getWriter().println("更改成功");
        response.setHeader("Refresh", "2;url=/user/personal.jsp");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = MD5Util.getMD5(username,request.getParameter("password"));
        String verifyCode = request.getParameter("verifyCode");
        HttpSession session = request.getSession();
        String word= (String) session.getAttribute("checkcode_session");
        if(word.equals(verifyCode)){
        User user=userService.login(username,password);
        if(user!=null){

            session.setAttribute("user",user);
            response.getWriter().println("登录成功，即将跳转到首页");
            response.setHeader("Refresh", "2;url=/index.jsp");
        }else {
            response.getWriter().println("登录失败，请重试");
            response.setHeader("Refresh", "2;url=/user/login.jsp");
        }}else {
            response.getWriter().println("验证码错误，请重试");
            response.setHeader("Refresh", "2;url=/user/login.jsp");
        }
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
        response.setHeader("Refresh", "2;url=/user/login.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "logout":
                logout(request,response);
                break;
            case "sendVerifyEmail":
                sendVerifyEmail(request,response);
                break;
            case "VerifyEmail":
                VerifyEmail(request,response);
                break;
        }
    }

    private void VerifyEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uid = request.getParameter("uid");
        String uuid = request.getParameter("uuid");
        if(userService.VerifyEmail(uid,uuid)){
        response.getWriter().println("验证成功");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        user.setStatus("1");
        response.setHeader("Refresh", "2;url=/user/personal.jsp");
        }
    }

    private void sendVerifyEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        SendJMail.sendMail(user.getEmail(),"http://localhost/user/UserServlet?op=VerifyEmail&uid="+user.getUid()+"&uuid="+MD5Util.getMD5(user.getEmail()));
        response.getWriter().println("已发送");
        response.setHeader("Refresh", "2;url=/user/personal.jsp");
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        response.getWriter().println("推出成功，即将跳转到首页");
        response.setHeader("Refresh", "2;url=/index.jsp");
    }
}
