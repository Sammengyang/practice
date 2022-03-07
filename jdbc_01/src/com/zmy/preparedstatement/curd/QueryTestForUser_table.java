package com.zmy.preparedstatement.curd;

import com.zmy.jdbc.util.JDBCUtils;
import com.zmy.statement.crud.User;
import org.junit.Test;

import javax.swing.*;
import javax.xml.transform.Source;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author Sam
 * @create 2021-11-19 11:13
 */
public class QueryTestForUser_table {
    @Test
    public void test() {
        String sql = "select user,balance from user_table where user=?";
        User user = queryForUser_table(sql,"zmy");
        System.out.println(user);
    }
    /**
     *  对user_table通用的查询
     */
    public User queryForUser_table(String sql,Object ... args) {
        Connection conn =null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i=0;i<args.length;i++){
                ps.setString(i+1,(String) args[i]);
            }
            // 获取结果集
            rs = ps.executeQuery();
            // 获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过ResulSetMetaData获取结果集的列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){//判断是否有数据
                User u1 = new User();
                // 处理结果集一行数据的每一列
                for (int i=0;i<columnCount;i++){
                    Object columnValue = rs.getObject(i + 1);
                    //获取结果集的每个列的列名
                    String columnName = rsmd.getColumnName(i + 1);
                    //给user对象的指定columnName属性，复制为columnValue（通过反射）
                    Field field = User.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(u1,columnValue);
                }
                return u1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,ps,rs);
        }
        return null;

    }





    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            //1.加载配置文件
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
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
            String sql = "select user,password,balance from user_table where user=?";
            ps = conn.prepareStatement(sql);
            //填充占位符
            ps.setString(1,"zmy");

            //执行,并返回结果集
            resultSet = ps.executeQuery();
            //处理结果集
            if (resultSet.next()) { // 判断结果集的下一条数据是否有数据，如果有返回true，没有返回true，指针并下移
                String userName = resultSet.getString(1);
                String psd = resultSet.getString(2);
                int balance = resultSet.getInt(3);
                // 输出
                System.out.println("userName:" + userName + "password:" + psd + "balance:" + balance);
                // 方式二：
                Object[] users = new Object[]{userName, psd, balance};
                System.out.println(users);
                //方式三:集合  ：将数据封装为对象
                User u1 = new User(userName, psd, balance);
                System.out.println(u1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 关闭资源
        try {
            if (ps != null)
                ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (resultSet != null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
