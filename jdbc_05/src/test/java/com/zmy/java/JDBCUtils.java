package com.zmy.java;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;

/**
 * @author Sam  Email:superdouble@yeah.net
 * @Description
 * @create 2022-02-15 19:39
 */
public class JDBCUtils {
    /**
     * 获取数据库连接
     * @return
     */
    public static Connection getConnection(){
        Connection con = null;
        try {
            // 加载配置文件
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(is);
            // 读取配置文件
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");
            // 加载驱动
            Class.forName(driverClass);
            con = DriverManager.getConnection(url, user, password);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * 关闭 Collection 和Statement操作
     * @param con
     * @param ps
     */
    public static void closResource(Connection con , Statement ps){
        try {
            if (ps!=null)
                ps.close();
            if (con!=null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭资源
     * @param con
     * @param ps
     * @param rs
     */
    public static void closeResource(Connection con, Statement ps, ResultSet rs){
        try {
            if (ps!=null)
                ps.close();
            if (con!=null)
                con.close();
            if (rs!=null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 通用的增删改查方法
     * @param sql
     * @param args
     */
    public static void update(String sql,Object...args){
        Connection con = null;
        PreparedStatement ps = null;
        try {
            // 获取数据库连接
            con = JDBCUtils.getConnection();
            // 获取PreparedStatement的实例（预编译sql语句）
            ps = con.prepareStatement(sql);
            // 填充占位符
            for (int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            // 4.执行sql语句
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(con,ps);
        }

    }

    /**
     * 对数据库进行查询操作
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T>T getInstance(Class<T> clazz,String sql,Object...args){
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            // 获取数据库连接
            con = JDBCUtils.getConnection();
            ps = con.prepareStatement(sql);
            // 填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setString(i+1,(String) args[i]);
            }
            // 获取结果集
            resultSet = ps.executeQuery();
            // 获取元数据
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 通过元数据获取列数
            int columnCount = metaData.getColumnCount();
            if (resultSet.next()){ // 判断是否有数据
                T t = clazz.newInstance();
                // 获取每一列数据
                for (int i = 0; i < columnCount; i++) {
                    Object colValue = resultSet.getObject(i + 1);
                    // 获取结果集的列名
                    // 获取结果集每列的列名
                    //getColumnName:获取表的列明
                    //String columnName = rsmd.getColumnName(i+1);
                    //getColumnName:获取表列的别名
                    String colName = metaData.getColumnLabel(i + 1);
                    // 通过反射，给student对象指定colName属性赋值
                    Field filed = clazz.getDeclaredField(colName);
                    filed.setAccessible(true);
                    filed.set(t,colValue);
                }
                return t;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(con,ps,resultSet);
        }
        return null;
    }
}
