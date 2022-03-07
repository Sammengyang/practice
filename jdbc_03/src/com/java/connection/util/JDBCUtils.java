package com.java.connection.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.zmy.dao.Users;
import com.zmy.dao.junit.UserDAOImpl;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *@Description
 *@author Sam  Email:superdouble@yeah.net
 *@create 2021-11-22 21:17
 *@version
 */
public class JDBCUtils {

    /**
     * 使用C3P0的数据库连接池技术
     *
     * @return
     * @throws Exception
     */
    // 数据库连接池只需要提供一个
    private static ComboPooledDataSource cpds = new ComboPooledDataSource("helloc3p0");
    public static Connection getConnectionByC3P0() throws Exception {
        Connection conn = cpds.getConnection();
        return conn;
    }

    /**
     * 使用DBCP数据库连接池技术获取数据库连接
     *
     * @return
     * @throws Exception
     */
    private static DataSource source;
    static {
        Properties pros= new Properties();
        try {
            //加载配置文件
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("DBCP.properties");
            pros.load(is);
            source = BasicDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnectionByDBCP() throws Exception {
        Connection conn = source.getConnection();
        return conn;
    }

    /**
     *使用Druid数据库连接池技术获取数据库连接
     *
     * @return
     * @throws Exception
     */
    private static DataSource source1;
    static {
        Properties pros = new Properties();
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("durid.properties");
        try {
            pros.load(is);
            source1 = DruidDataSourceFactory.createDataSource(pros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Connection getConnectionByDruid() throws Exception {
        Connection conn = source1.getConnection();
        return conn;
    }
    @Test
    public void getUser() {
        UserDAOImpl dao = new UserDAOImpl();
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnectionByDruid();
            Users zmy = dao.getUserByuser(conn, "zmy");
            System.out.println(zmy);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            com.zmy.util.JDBCUtils.closResource(conn,null);
        }
    }
}
