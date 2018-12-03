package com.cskaoyan.service.impl;

import com.cskaoyan.bean.Product;
import com.cskaoyan.dao.ProductDao;
import com.cskaoyan.service.ProductService;
import com.cskaoyan.utils.PageCategoryInfo;

import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/9 15:52
 **/
public class ProductServiceImpl implements ProductService {
    ProductDao productDao =new ProductDao();
    private static final int PAGE_COUNT=10;
    @Override
    public boolean addProduct(Product product) {
        productDao.addProduct(product);
        return true;
    }

    @Override
    public List<Product> findAllProduct() {
        return productDao.findAllProduct();
    }

    @Override
    public boolean deleteOne(String pid) {
        productDao.deleteOne(pid);
        return true;
    }

    @Override
    public Product findProductByUpdate(String pid) {
        return productDao.findProductByUpdate(pid);
    }

    @Override
    public boolean updateProduct(Product product) {
        return productDao.updateProduct(product);
    }

    @Override
    public PageCategoryInfo findOneProduct(String num) {
        PageCategoryInfo pageCategoryInfo=new PageCategoryInfo();
        int pageNum=Integer.parseInt(num);
        int limit =PAGE_COUNT;
        int offset=(pageNum-1)*PAGE_COUNT;
        List<Product> productList=productDao.findOneProduct(limit,offset);
        return null;
    }

    @Override
    public List<Product> SearchProduct(String id, String cid, String name, String minprice, String maxprice) {
        return productDao.SearchProduct(id,cid,name,minprice,maxprice);
    }

    @Override
    public Product findProductByPid(String pid) {
        return productDao.findProductByPid(pid);
    }

    @Override
    public List<Product> findProductByCid(String cid) {
        return productDao.findProductByCid(cid);
    }

    @Override
    public List<Product> findProductsByName(String pname) {
        return productDao.findProductsByName(pname);
    }

    @Override
    public List<Product> topProducts() {
        return productDao.topProducts();
    }
}
