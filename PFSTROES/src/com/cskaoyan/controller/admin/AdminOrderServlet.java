package com.cskaoyan.controller.admin;

import com.cskaoyan.bean.Order;
import com.cskaoyan.service.OrderService;
import com.cskaoyan.service.impl.OrderServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminOrderServlet",urlPatterns = "/admin/OrderServlet")
public class AdminOrderServlet extends HttpServlet {
    OrderService orderService =new OrderServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op){
            case "findAllOrder":
                findAllOrder(request,response);
                break;
            case "orderDetail":
                orderDetail(request,response);
                break;
            case "delOrder":
                delOrder(request,response);
                break;
            case "delItemid":
                delItemid(request,response);
                break;
        }
    }

    private void delItemid(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int itemid = Integer.valueOf(request.getParameter("itemid"));
        orderService.delItemid(itemid);
        response.getWriter().println("删除成功");
        response.setHeader("Refresh", "2;url=/admin/OrderServlet?op=findAllOrder&num=1");
    }

    private void orderDetail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int oid = Integer.valueOf(request.getParameter("oid"));
        Order order=orderService.orderDetail(oid);
        request.setAttribute("orderitems",order.getOrderitems());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/order/orderDetails.jsp");
        requestDispatcher.forward(request,response);
    }

    private void delOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int oid = Integer.valueOf(request.getParameter("oid"));
        orderService.delOrder(oid);
        response.getWriter().println("删除成功");
        response.setHeader("Refresh", "2;url=/admin/OrderServlet?op=findAllOrder&num=1");
    }

    private void findAllOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Order> orders= orderService.findAllOrder();
        request.setAttribute("records",orders);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/order/orderList.jsp");
        requestDispatcher.forward(request,response);
    }
}
