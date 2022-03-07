package com.zmy.preparedstatement.curd;

import com.zmy.jdbc.util.JDBCUtils;
import com.zmy.statement.crud.Student;
import com.zmy.statement.crud.User;
import javafx.scene.media.VideoTrack;
import org.junit.Test;

import javax.xml.transform.Source;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *  使用PreparedStatement实现针对不同表的通用查询操作
 * @author Sam
 * @create 2021-11-19 18:11
 */
public class PreparedStatementQueryTest {

    @Test
    public void testGetForList(){

        String sql =  "select s_id id,s_name name,s_birth birth,s_sex sex from student where s_id<?";
        Scanner sc = new Scanner(System.in);
        List<Student> list = getForlist(Student.class, sql,"8");
        list.forEach(System.out::println);
        System.out.println("********************************************");
        String sql1 = "select * from user_table where balance<?";
        List<User> users = getForlist(User.class, sql1,"5000");
        users.forEach(System.out::println);
    }

    public <T> List<T> getForlist(Class<T> clazz,String sql,Object ... args){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i =0;i<args.length;i++){
                ps.setString(i+1,(String) args[i]);
            }
            // 获取结果集
            rs = ps.executeQuery();
            // 获取元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过元数据获取列数
            int columnCount = rsmd.getColumnCount();
            // 创建集合对象
            ArrayList<T> list = new ArrayList<>();
            while (rs.next()) {//判断是否有数据
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //获取每一列的数据
                    Object columnValue = rs.getObject(i + 1);
                    // 获取结果集每列的列名
                    //getColumnName:获取表的列明
//                    String columnName = rsmd.getColumnName(i+1);
                    //getColumnName:获取表列的别名
                    String columnName = rsmd.getColumnLabel(i + 1);
                    // 通过反射，给student对象指定columnName属性赋值columnValue
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                list.add(t);
            }
            return list;
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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,ps,rs);
        }
        return null;
    }
    @Test
    public void testGetInstance(){
        String sql = "select s_id id,s_name name,s_birth birth,s_sex sex from student where s_id=?";
        Student student = getInstance(Student.class, sql, "10");
        System.out.println(student);

    }

    public <T>T getInstance(Class<T> clazz,String sql,Object ... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i =0;i<args.length;i++){
                ps.setString(i+1,(String) args[i]);
            }
            // 获取结果集
            rs = ps.executeQuery();
            // 获取元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过元数据获取列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()) {//判断是否有数据
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //获取每一列的数据
                    Object columnValue = rs.getObject(i + 1);
                    // 获取结果集每列的列名
                    //getColumnName:获取表的列明
//                    String columnName = rsmd.getColumnName(i+1);
                    //getColumnName:获取表列的别名
                    String columnName = rsmd.getColumnLabel(i + 1);
                    // 通过反射，给student对象指定columnName属性赋值columnValue
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                return t;
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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,ps,rs);
        }
        return null;
    }

}
