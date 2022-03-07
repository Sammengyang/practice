package com.zmy.transaction;


import com.zmy.util.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.channels.NonWritableChannelException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * @author Sam  Email:superdouble@yeah.net
 * @Description
 * @create 2021-11-20 21:42
 */
public class ConnectionTest {

    @Test
    public void testConnection() throws Exception {
        String sql = "select s_id id,s_name name,s_birth birth,s_sex sex from student where s_id>?";
        List<Student> stuList = query(Student.class, sql, "6");
        stuList.forEach(System.out::println);

    }

    public <T> List<T> query(Class<T> clazz, String sql, String ... args) throws Exception {
        // 加载配置文件
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            InputStream fis = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
            Properties pros = new Properties();
            pros.load(fis);
            // 获取配置文件
            String user = pros.getProperty("user");
            String password = pros.getProperty("password");
            String url = pros.getProperty("url");
            String driverClass = pros.getProperty("driverClass");
            // 加载驱动
            Class.forName(driverClass);
            // 获取连接
            conn = DriverManager.getConnection(url,user,password);
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i=0;i<args.length;i++){
                ps.setString(i+1,args[i]);
            }
            // 执行sql语句并返回结果集
            rs = ps.executeQuery();
            // 获取元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // 获取结果集列数
            int colCount = rsmd.getColumnCount();
            // 创建集合
            ArrayList<T> list = new ArrayList<>();
            while (rs.next()){
                T t = clazz.newInstance();
                for (int i=0;i<colCount;i++){
                    // 获取每一列的数据
                    Object colValue = rs.getObject(i + 1);
                    // 获取列名
                    String colName = rsmd.getColumnLabel(i + 1);
                    // 通过反射给clazz对象的colName属性赋值colValue
                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(t,colValue);
                }
                list.add(t);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,ps,rs);
        }
        return null;
    }
}
