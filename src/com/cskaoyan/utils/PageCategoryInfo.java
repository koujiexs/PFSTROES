package com.cskaoyan.utils;

import com.cskaoyan.bean.Category;

import java.util.List;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/9 22:06
 **/
public class PageCategoryInfo<T> {
    List<T> pageList;
    int ZongJLnum;
    int ZYnum;
    int DQnum;
    int QYnum;
    int naxtNum;

    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    public int getZongJLnum() {
        return ZongJLnum;
    }

    public void setZongJLnum(int zongJLnum) {
        ZongJLnum = zongJLnum;
    }

    public int getZYnum() {
        return ZYnum;
    }

    public void setZYnum(int ZYnum) {
        this.ZYnum = ZYnum;
    }

    public int getDQnum() {
        return DQnum;
    }

    public void setDQnum(int DQnum) {
        this.DQnum = DQnum;
    }

    public int getQYnum() {
        return QYnum;
    }

    public void setQYnum(int QYnum) {
        this.QYnum = QYnum;
    }

    public int getNaxtNum() {
        return naxtNum;
    }

    public void setNaxtNum(int naxtNum) {
        this.naxtNum = naxtNum;
    }
}
