package com.cskaoyan.controller;

import com.cskaoyan.bean.Category;
import com.cskaoyan.bean.Product;
import com.cskaoyan.service.CategoryService;
import com.cskaoyan.service.impl.CategoryServiceImpl;
import com.cskaoyan.service.impl.ProductServiceImpl;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.utils.PageCategoryInfo;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@WebServlet(name = "ProductServlet", urlPatterns = "/ProductServlet")
public class ProductServlet extends HttpServlet {
    ProductService productService = new ProductServiceImpl();
    CategoryService categoryService=new CategoryServiceImpl();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateProduct(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String op = request.getParameter("op");
        switch (op) {
            case "findAllProduct":
                findAllProduct(request, response);
                break;
            case "deleteOne":
                String pid = request.getParameter("pid");
                deleteOne(pid, response);
                break;
            case "findProductByUpdate":
                String pid2 = request.getParameter("pid");
                findProductByUpdate(pid2, request, response);
                break;
            case "searchProduct":
                searchProduct(request,response);
                break;
            case "findProductByPid":
                findProductByPid(request,response);
                break;
            case "findProductByCid":
                findProductByCid(request,response);
                break;
            case "findProductsByName":
                findProductsByName(request,response);
                break;
        }
    }

    private void findProductsByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> allCategory = categoryService.findAllCategory();
        String pname = request.getParameter("pname");
        List<Product> products=productService.findProductsByName(pname);
        request.setAttribute("categories",allCategory);
        request.setAttribute("records",products);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/searchProducts.jsp");
        requestDispatcher.forward(request,response);
    }

    private void findProductByCid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        List<Category> allCategory = categoryService.findAllCategory();
        request.setAttribute("categories",allCategory);
        List<Product> productList=productService.findProductByCid(cid);
        request.setAttribute("records",productList);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/products.jsp");
        requestDispatcher.forward(request,response);
    }

    private void findProductByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        List<Category> allCategory = categoryService.findAllCategory();
        Product product =productService.findProductByPid(pid);
        request.setAttribute("categories",allCategory);
        request.setAttribute("product",product);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/productdetail.jsp");
        requestDispatcher.forward(request,response);
    }

    private void searchProduct(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        List<Category> categories = categoryService.findAllCategory();
        session.setAttribute("categories",categories);
        response.sendRedirect("/admin/product/searchProduct.jsp");
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletContext servletContext = this.getServletConfig().getServletContext();
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        factory.setRepository(repository);
        Product product = new Product();
        Map<String, String> map = new HashMap<>();
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
                if (!(fieldName.equals("") || fieldName == null)) {
                    String dz = getServletContext().getRealPath("product/img/" + fieldName);
                    try {
                        item.write(new File(getServletContext().getRealPath("product/img/" + fieldName)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    map.put("imgurl", "/product/img/" + fieldName);
                }
            } else {
                if (item.isFormField()) {
                    map.put(item.getFieldName(), item.getString("utf-8"));
                }
            }
        }
        try {
            BeanUtils.populate(product, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        productService.updateProduct(product);
    }

    private void findProductByUpdate(String pid2, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Product product = productService.findProductByUpdate(pid2);
        HttpSession session = request.getSession();
        CategoryServiceImpl categoryServiceImplJK = new CategoryServiceImpl();
        List<Category> categories = categoryServiceImplJK.findAllCategory();
        session.setAttribute("categories", categories);
        session.setAttribute("product", product);
        response.sendRedirect("/admin/product/updateProduct.jsp");
    }

    private void deleteOne(String pid, HttpServletResponse response) throws IOException {
        productService.deleteOne(pid);
        response.getWriter().println("删除成功");
        response.setHeader("Refresh", "2;url=/ProductServlet?op=findAllProduct&num=1");
    }

    private void findAllProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String num = request.getParameter("num");
        if(num!=null&&!num.isEmpty()){
            PageCategoryInfo pageCategoryInfo = productService.findOneProduct(num);
            session.setAttribute("pageinfo",pageCategoryInfo);
        }
        List<Product> allProduct = productService.findAllProduct();
        request.setAttribute("records", allProduct);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/admin/product/productList.jsp");
        requestDispatcher.forward(request,response);
    }
}
