package com.zmy.jdbc;

import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @author Sam
 * @create 2021-11-18 14:41
 */
public class JdbcDemo01 {
    /**
     * 连接数据库
     */
    // 方式一:
    @Test
    public void testConnetions1() throws SQLException {

        // 1. 获取Driver实现类的对象
        Driver driver = new com.mysql.jdbc.Driver();
        // 2. 提供要连接的数据库
        String url = "jdbc:mysql://localhost:3306/javatest";
        // 3. 用户名和密码封装在properties
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","123456");
        // 4. 获取连接
        Connection conn = driver.connect(url,info);
        System.out.println(conn);


        //5.定义sql语句
        String sql = "update teacher set t_name = \"张三\" where t_id = 1";
        //6.获取执行sql的对象
        Statement stmt = conn.createStatement();
        //7.执行sql
        int count = stmt.executeUpdate(sql);
        //8.处理结果
        System.out.println(count);
        //9.释放资源
        stmt.close();
        conn.close();
    }
    // 方式二:对方式一的迭代：在如下程序中不使用第三方的api，
    // 使得程序具有更好的可以移植性
    @Test
    public void testConnection2() throws Exception {
        // 1. 获取Driver实现类的对象，使用反射
        Class aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();
        // 2. 提供要连接的数据库
        String url = "jdbc:mysql://localhost:3306/javatest";
        // 3. 提供连接需要的用户名和密码
        Properties info = new Properties();
        info.setProperty("user","root");
        info.setProperty("password","123456");
        // 4.获取连接
        Connection conn = driver.connect(url, info);
        System.out.println(conn);


        //5.定义sql语句
        String sql = "update teacher set t_name = 100 where t_id = 1";
        //6.获取执行sql的对象
        Statement stmt = conn.createStatement();
        //7.执行sql
        int count = stmt.executeUpdate(sql);
        //8.处理结果
        System.out.println(count);
        //9.释放资源
        stmt.close();
        conn.close();
    }
    // 方式三: 使用DriverManager替换Driver
    @Test
    public void testConnection3() throws Exception {

        // 1. 获取Driver实现类的对象
        Class aClass = Class.forName("com.mysql.jdbc.Driver");
        Driver driver = (Driver) aClass.newInstance();
        // 2. 提供数据库url、用户名、密码
        String url = "jdbc:mysql://localhost:3306/javatest";
        String user = "root";
        String password = "123456";
        // 3.注册驱动
        DriverManager.registerDriver(driver);
        // 4. 获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);


        //5.定义sql语句
        String sql = "update teacher set t_name = \"张三\" where t_id = 1";
        //6.获取执行sql的对象
        Statement stmt = conn.createStatement();
        //7.执行sql
        int count = stmt.executeUpdate(sql);
        //8.处理结果
        System.out.println(count);
        //9.释放资源
        stmt.close();
        conn.close();
    }
    // 方式四:在方式三的基础上进行优化，可以知识加载驱动，不用显式注册驱动
    @Test
    public void testConnection4() throws Exception {

        // 1. 提供数据库url、userName、password
        String url = "jdbc:mysql://localhost:3306/javatest";
        String user = "root";
        String password = "123456";
        // 2. 加载Driver驱动
        Class.forName("com.mysql.jdbc.Driver");
        // 相较于方式三，可以省略以下操作
        /**
         * 原因：在mysql的Driver实现类中，声明了如下操作
         * static {
         *         try {
         *             DriverManager.registerDriver(new Driver());
         *         } catch (SQLException var1) {
         *             throw new RuntimeException("Can't register driver!");
         *         }
         *     }
         */
//        Driver driver = (Driver) aClass.newInstance();
//        // 注册驱动
//        DriverManager.registerDriver(driver);
        // 3. 获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);


        //4. 定义sql语句
        String sql = "update teacher set t_name = \"张三\" where t_id = 1";
        //5.获取执行sql的对象
        Statement stmt = conn.createStatement();
        //6.执行sql
        int count = stmt.executeUpdate(sql);
        //7.处理结果
        System.out.println(count);
        //8.释放资源
        stmt.close();
        conn.close();
    }
    //
    //

    /** 方法五：将数据库连接需要的4个基本信息生命在配置文件中，
     *          通过读取配置文件的方式，获取连接
     *
     *  优点:
     *   1. 实现了数据和代码的分离，实现了解耦
     *   2. 如果需要修改配置文件，可以避免程序重新打包
     *
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @Test
    public void getConnerction5() throws IOException, ClassNotFoundException, SQLException {
        // 1. 读取配置文件的4个基本信息(Driver实现类的对象，url（资源定位符）、userName、password)
        InputStream fis = JdbcDemo01.class.getClassLoader().getResourceAsStream("jdbc.properties");
        Properties pros = new Properties();
        pros.load(fis);
        // 读取配置文件
        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");
        // 2.加载驱动
        Class.forName(driverClass);
        // 3. 获取连接
        Connection conn = DriverManager.getConnection(url, user, password);

        //4. 定义sql语句
        String sql = "update teacher set t_name = 100 where t_id = 1";
        //5.获取执行sql的对象
        Statement stmt = conn.createStatement();
        //6.执行sql
        int count = stmt.executeUpdate(sql);
        //7.处理结果
        System.out.println(count);
        //8.释放资源
        stmt.close();
        conn.close();

    }

}
