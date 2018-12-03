package com.cskaoyan.service;

import com.cskaoyan.bean.Product;
import com.cskaoyan.utils.PageCategoryInfo;

import java.util.List;

public interface ProductService {
    public boolean addProduct(Product product);
    public List<Product> findAllProduct();

    public boolean deleteOne(String pid);

    public Product findProductByUpdate(String pid);

    public boolean updateProduct(Product product);

    public PageCategoryInfo findOneProduct(String num);

    List<Product> SearchProduct(String id, String cid, String name, String minprice, String maxprice);

    Product findProductByPid(String pid);

    List<Product> findProductByCid(String cid);

    List<Product> findProductsByName(String pname);

    List<Product> topProducts();
}
