package com.cskaoyan.bean;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/9 15:18
 **/
public class Product {
    String pid;
    String pname;
    double estoreprice;
    double markprice;
    int pnum;
    int cid;
    String imgurl;
    String desc;
    Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Product() {
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public double getEstoreprice() {
        return estoreprice;
    }

    public void setEstoreprice(double estoreprice) {
        this.estoreprice = estoreprice;
    }

    public double getMarkprice() {
        return markprice;
    }

    public void setMarkprice(double markprice) {
        this.markprice = markprice;
    }

    public int getPnum() {
        return pnum;
    }

    public void setPnum(int pnum) {
        this.pnum = pnum;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Product{" +
                "pid='" + pid + '\'' +
                ", pname='" + pname + '\'' +
                ", estoreprice=" + estoreprice +
                ", markprice=" + markprice +
                ", pnum=" + pnum +
                ", cid=" + cid +
                ", imgurl='" + imgurl + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }
}
