package com.cskaoyan.bean;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/15 15:50
 **/
public class Orderitem {
    int itemid;
    int oid;
    String pid;
    int buynum;

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public int getOid() {
        return oid;
    }

    public void setOid(int oid) {
        this.oid = oid;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }

}
