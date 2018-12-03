package com.cskaoyan.utils;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;

/**
 * Demo class
 *
 * @Author lyboy
 * @Date 2018/11/7 16:11
 **/
public class SJK {
    public static BasicDataSource dataSource;
    public static QueryRunner queryRunner;

    static {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/store?serverTimezone=GMT&useSSL=false&allowPublicKeyRetrieval=true");
        dataSource.setUsername("root");
        dataSource.setPassword("asdzxcvb");
        queryRunner = new QueryRunner(dataSource);
    }
}
