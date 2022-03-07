package com.zmy.preparedstatement.curd;

import com.zmy.jdbc.JdbcDemo01;
import com.zmy.jdbc.util.JDBCUtils;
import org.junit.Test;

import javax.lang.model.element.NestingKind;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 使用PreparedStatement来替换Statement，实现对数据表的增删改查操作
 * 增删改；查
 *
 * @author Sam
 * @create 2021-11-18 21:44
 */
public class PreparedStatementUpdateTest<args> {
    /**
     * PreparedStatement查询
     *
     *
     *
     */







    /**
     * 通用的增删改
     */
    @Test
    public void testUpdate(){
        //删除一条数据
//        String sql = "delete from user_table where user = ?";
//        update(sql,"zzz");
        //添加一条数据
//        String sql = "insert into user_table(user,password,balance) values(?,?,?)";
//        update(sql,"zmy","zmy990201.","12000");
        //修改一条数据
        String sql = "update user_table set balance=? where user=?";
        update(sql,"16000","zmy");// 参数必须是字符串形式
    }
    public void update(String sql,Object ... arges) { //sql的占位符和arges 数组长度一样
        Properties pros= null;
        Connection conn = null;
        try {
            //1.加载配置文件
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            pros = new Properties();
            pros.load(is);
            //2.获取配置文件
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");
            //3.加载驱动
            Class.forName(driverClass);
            //4.获取连接
            conn = DriverManager.getConnection(url, user, password);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
 //---------------------------------------------------------------------

//        conn = JDBCUtils.getConnection();
        // 5.预编译sql语句，返回PreparedStatement
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            //6.填充占位符
            for (int i=0;i<arges.length;i++){
                ps.setString(i+1, (String) arges[i]);
            }
            //7.执行
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
//-----------------------------------------------------------------------------
        //8.关闭资源
        if (conn !=null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (ps!=null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 增、删、改
     */
    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            // 向user_table中添加一条数据
            //1.加载配置文件
            InputStream fis = PreparedStatementUpdateTest.class.getClassLoader().getResourceAsStream("jdbc.properties");
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
            conn = DriverManager.getConnection(url, user, password);
//        System.out.println(conn); 测试连接成功

            // 5.预编译sql语句；返回PreparedStatement的实例
            String sql = "insert into user_table(user,password,balance) values (?,?,?)";//?:占位符
            ps = conn.prepareStatement(sql);
            //6.填充占位符
            ps.setString(1, "ABC");
            ps.setString(2, "abc123");
            ps.setString(3, "6000");

            //7.执行sql操作
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //8.资源关闭
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
    }

    /**
     * 修改user_table的一条数据
     */
    @Test
    public void testUpdate1() {
        Connection conn = null;
        Properties pros = null;
        PreparedStatement ps = null;
        try {
            //1.加载配置文件
            InputStream fis = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            pros = new Properties();
            pros.load(fis);
            //2.获取配置文件
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");
            //3.加载驱动
            Class.forName(driverClass);
            //4.获取连接
            conn = DriverManager.getConnection(url, user, password);
            //5.预编译sql语句
//            String sql = "update user_table SET user= ?,password= ?,balance= ? WHERE balance is null";
            String sql = "update user_table set user = ?,password = ?,balance = ? where user = ?";
            ps = conn.prepareStatement(sql);
            //6.填充占位符
            ps.setString(1, "zmy");
            ps.setString(2, "asdzxc");
            ps.setString(3, "6300");
            ps.setString(4, "ccc");
            //7.执行sql语句
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //8.关闭资源
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
    }

    /**
     * 删除测试
     */
    @Test
    public void testDelete() {
        Properties pros = null;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.加载配置文件
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");//jdbc.properties
            pros = new Properties();
            pros.load(is);
            //2.获取配置文件
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");
            //3.加载驱动
            Class.forName(driverClass);
            //4.获取连接
            conn = DriverManager.getConnection(url, user, password);
            //5.预编译sql语句
            String sql = "delete from user_table where user=?";
            ps = conn.prepareStatement(sql);
            //6.填充占位符
            ps.setString(1, "zmy");
            //7.执行sql语句
            ps.execute();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            //8.关闭资源
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
    }

}
