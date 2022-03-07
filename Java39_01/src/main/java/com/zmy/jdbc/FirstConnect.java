package com.zmy.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author Sam  Email:superdouble@yeah.net
 * @Description
 * @create 2022-03-04 10:53
 */
public class FirstConnect {
    public static void main(String[] args) throws Exception {
        // 1. 加载驱动类
//        Driver driver = new com.mysql.jdbc.Driver();  获取driver实现类对象
        Class.forName("com.mysql.jdbc.Driver");
        // 2. 建立连接
        /**
         * url: 数据库的地址
         * name:数据库的用户名
         * password:数据库密码
         */
        String url = "jdbc:mysql://localhost:3306/javatest?rewriteBatchedStatements=true";
        String name = "root";
        String password = "123456";
        Connection conn = DriverManager.getConnection(url,name,password);
        System.out.println(conn);
    }
}
