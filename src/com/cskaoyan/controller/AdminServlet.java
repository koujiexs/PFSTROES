package com.cskaoyan.controller;

import com.cskaoyan.bean.Admin;
import com.cskaoyan.service.AdminService;
import com.cskaoyan.service.impl.AdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminServlet",urlPatterns = "/AdminServlet")
public class AdminServlet extends HttpServlet {
    AdminService adminService =new AdminServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        if(password.equals(password1)){
            adminService.addAdmin(username,password);
            response.getWriter().println("添加成功");
            response.setHeader("Refresh", "2;url=/admin/admin/addAdmin.jsp");
        }else {
            response.getWriter().println("密码不一致，请重试");
            response.setHeader("Refresh", "2;url=/admin/admin/addAdmin.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "findAllAdmin":
                findAllAdmin(request,response);
                break;
            case "updateAdmin":
                updateAdmin(request,response);
                break;
            case "login":
                login(request,response);
                break;
            case "lgout":
                lgout(request,response);
                break;
        }
    }

    private void lgout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.getSession().removeAttribute("admin");
        response.getWriter().println("退出成功");
        response.setHeader("Refresh", "2;url=/admin/index.jsp");
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();
        Admin admin=adminService.login(username,password);
        if(admin!=null){
            session.setAttribute("admin",admin);
            response.sendRedirect("/admin/main.jsp");
        }else {
            response.getWriter().println("密码错误，请重试");
            response.setHeader("Refresh", "2;url=/admin/index.jsp");
        }
    }


    private void findAllAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        List<Admin> admins= adminService.findAllAdmin();
        session.setAttribute("records",admins);
        response.sendRedirect("/admin/admin/adminList.jsp");
    }
    private void updateAdmin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String aid = request.getParameter("aid");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String password1 = request.getParameter("password1");
        if(password.equals(password1)){
            adminService.updateAdmin(username,password);
            response.getWriter().println("修改成功");
            response.setHeader("Refresh", "2;url=/AdminServlet?op=findAllAdmin&num=1");
        }else {
            response.getWriter().println("密码不一致，请重试");
            response.setHeader("Refresh", "2;url=/AdminServlet?op=findAllAdmin&num=1");
        }
    }
}
