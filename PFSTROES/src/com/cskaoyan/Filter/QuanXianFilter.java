package com.cskaoyan.Filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "QuanXianFilter",urlPatterns = "/admin/*")
public class QuanXianFilter implements Filter {
    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request= (HttpServletRequest) req;
        HttpServletResponse response=(HttpServletResponse) resp;
        String requestURI = request.getRequestURI();
        HttpSession session = request.getSession();
        if(session.getAttribute("admin")!=null) {
            chain.doFilter(req, resp);
        }else if(requestURI.startsWith("/admin/js/")||requestURI.startsWith("/admin/css/")||requestURI.startsWith("/admin/images/")||requestURI.equals("/admin/main.jsp")||requestURI.equals("/admin/index.jsp")||requestURI.startsWith("/AdminServlet")||requestURI.startsWith("/admin/AjaxServlet")){
            chain.doFilter(req, resp);
        }else {
            response.sendRedirect("/admin/index.jsp");
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {

    }

}
