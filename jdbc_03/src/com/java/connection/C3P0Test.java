package com.java.connection;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.junit.Test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *@Description
 *@author Sam  Email:superdouble@yeah.net
 *@create 2021-11-22 20:23
 *@version
 */
public class C3P0Test {

    // 方式一：
    @Test
    public void testGetConnection1() throws Exception {
        // 获取c3p0数据库连接池
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        cpds.setDriverClass("com.mysql.jdbc.Driver");
        cpds.setJdbcUrl( "jdbc:mysql://localhost:3306/javatest" );
        cpds.setUser("root");
        cpds.setPassword("123456");
        //通过设置相关参数，对数据库连接池进行管理
        //设置初始数据库连接池中的连接数
        cpds.setInitialPoolSize(6);

        Connection conn = cpds.getConnection();
        System.out.println(conn);
    }
    // 方式二：
    @Test
    public void testGetConnection2() throws SQLException {
        ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
        Connection conn = cpds.getConnection();
        System.out.println(conn);

    }
}
