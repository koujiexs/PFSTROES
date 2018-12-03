package com.cskaoyan.controller;

import com.cskaoyan.bean.Product;
import com.cskaoyan.service.impl.ProductServiceImpl;
import com.cskaoyan.service.ProductService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AddProductServlet",urlPatterns = "/AddProductServlet")
public class AddProductServlet extends HttpServlet {
    ProductService productService =new ProductServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        Product product = new Product();
        Map<String,String> map=new HashMap<>();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = null;
        try {
            items = upload.parseRequest(request);
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        Iterator<FileItem> iter = items.iterator();
        while (iter.hasNext()) {
            FileItem item = iter.next();

            if (!item.isFormField()) {
                String fieldName = item.getName();
                String dz = getServletContext().getRealPath("product/img/" + fieldName);
                try {
                    item.write(new File(getServletContext().getRealPath("product/img/" + fieldName)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                HttpSession session = request.getSession();
                map.put("imgurl","/product/img/" + fieldName);
            } else {
                if (item.isFormField()) {
                    map.put(item.getFieldName(),item.getString("utf-8"));
                }
            }
        }
        String description = map.get("description");
        map.put("desc",description);
        try {
            BeanUtils.populate(product,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        productService.addProduct(product);
        response.getWriter().println("添加成功");
        response.setHeader("Refresh", "2;url=/admin/product/addProduct.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
