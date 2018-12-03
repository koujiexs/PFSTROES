package com.cskaoyan.controller;

import com.cskaoyan.bean.*;
import com.cskaoyan.service.CartService;
import com.cskaoyan.service.OrderService;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.service.impl.CartServiceImpl;
import com.cskaoyan.service.impl.OrderServiceImpl;
import com.cskaoyan.service.impl.ProductServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "OrderServlet", urlPatterns = "/user/OrderServlet")
public class OrderServlet extends HttpServlet {
    OrderService orderService = new OrderServiceImpl();
    CartService cartService = new CartServiceImpl();
    ProductService productService = new ProductServiceImpl();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op) {
            case "placeOrder":
                placeOrder(request, response);
                break;
        }

    }

    private void placeOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean pd = true;
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Shoppingcart shoppingcart = (Shoppingcart) session.getAttribute("shoppingcart");
        List<Shoppingitem> shoppingitems = shoppingcart.getShoppingitems();
        Order order = new Order();
        List<Orderitem> orderitems = new ArrayList<>();
        String recipients = request.getParameter("recipients");
        String tel = request.getParameter("tel");
        String address = request.getParameter("address");
        String[] pids = request.getParameterValues("pid");
        Map<String, Object> map = new HashMap<>();
        for (String pid : pids) {
            Orderitem orderitem = new Orderitem();
            orderitem.setPid(pid);
            Product product = productService.findProductByPid(pid);

            for (Shoppingitem shoppingitem : shoppingitems) {
                if (shoppingitem.getPid().equals(pid)) {
                    orderitem.setBuynum(shoppingitem.getSnum());
                    if ((product.getPnum() - orderitem.getBuynum()) < 0) {
                        pd = false;
                    }
                    cartService.delItem(String.valueOf(user.getUid()), String.valueOf(shoppingitem.getItemid()));
                    break;
                }
            }
            orderitems.add(orderitem);
        }
        if (pd) {
            double money = Double.valueOf(request.getParameter("money"));
            int state = 1;
            int uid = ((User) session.getAttribute("user")).getUid();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String ordertime = (df.format(new Date()));
            map.put("orderitems", orderitems);
            map.put("money", money);
            map.put("recipients", recipients);
            map.put("tel", tel);
            map.put("address", address);
            map.put("state", state);
            map.put("ordertime", ordertime);
            map.put("uid", uid);
            try {
                BeanUtils.populate(order, map);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            orderService.placeOrder(order);
            response.getWriter().println("下单成功");
            response.setHeader("Refresh", "2;url=/user/OrderServlet?op=myoid");
        } else {
            response.getWriter().println("库存不够，请等待补货");
            response.setHeader("Refresh", "2;url=/user/CartServlet?op=findCart");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op) {
            case "myoid":
                myoid(request, response);
                break;
            case "cancelOrder":
                cancelOrder(request, response);
                break;
        }
    }

    private void cancelOrder(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int oid = Integer.valueOf(request.getParameter("oid"));
        int state = Integer.valueOf(request.getParameter("state"));
        orderService.cancelOrder(oid, state);
        response.getWriter().println("取消成功");
        response.setHeader("Refresh", "2;url=/user/OrderServlet?op=myoid");
    }

    private void myoid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Order> orders = orderService.myoid(user.getUid());
        request.setAttribute("orders", orders);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/user/myOrders.jsp");
        requestDispatcher.forward(request, response);
    }
}
