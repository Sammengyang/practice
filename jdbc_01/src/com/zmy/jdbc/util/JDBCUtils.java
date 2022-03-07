package com.zmy.jdbc.util;

import com.zmy.preparedstatement.curd.PreparedStatementUpdateTest;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 将连接数据库进行封装
 * @author Sam
 * @create 2021-11-18 22:21
 */
public class JDBCUtils {
    // 获取数据库连接
    public static Connection getConnection() throws SQLException, IOException, ClassNotFoundException {
        //1.加载配置文件
        InputStream fis = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(fis);
        //2.读取配置文件
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");
        //3.加载驱动
        Class.forName(driverClass);
        //4.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    /**
     * 关闭Connection和Statement操作
     * @param conn
     * @param ps
     */
    public static void closResource(Connection conn, Statement ps){
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源
     * @param conn 数据库连接
     * @param ps
     * @param resultSet
     */
    public static void closResource(Connection conn, Statement ps, ResultSet resultSet){
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } try {
            if (resultSet != null)
                resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
