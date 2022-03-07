package com.zmy.dao;

import com.zmy.util.JDBCUtils;
import jdk.nashorn.internal.scripts.JD;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *@Description 封装了对数据表的操作
 *@author Sam  Email:superdouble@yeah.net
 *@create 2021-11-22 16:00
 *@version
 */
public class BaseDAO {

    public int update(Connection conn, String sql, String ...args) {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                ps.setString(i+1,args[i]);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closResource(null,ps);
        }
        return 0;
    }

    //
    public <T> T getInstance(Connection conn, Class<T> clazz, String sql, String ...args) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        // 预编译sql语句
        ps = conn.prepareStatement(sql);
        //填充占位符
        for(int i=0;i<args.length;i++){
            ps.setString(i+1,args[i]);
        }
        // 执行 ,获取结果集
        rs = ps.executeQuery();
        // 获取元数据
        ResultSetMetaData rsmd = rs.getMetaData();
        // 获取数据条数
        int colCount = rsmd.getColumnCount();
        while (rs.next()){
            T t = clazz.newInstance();
            for (int i=0;i<colCount;i++){
                //获取每列的数据
                Object colValue = rs.getObject(i+1);
                //获取列名
                String colName = rsmd.getColumnLabel(i+1);
                // 通过反射给clazz对象colName属性赋值colValue
                Field field = clazz.getDeclaredField(colName);
                field.setAccessible(true);
                field.set(t,colValue);
            }
            return t;
        }
        return null;
    }
    public <T> List<T> getForlist(Connection conn,Class<T> clazz, String sql, Object ... args){

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
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
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(null,ps,rs);
        }
        return null;
    }

    // 用于查询特殊值的方法
    public <E>E getValue(Connection conn,String sql,String ...args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                ps.setString(i+1,args[i]);
            }

            rs = ps.executeQuery();
            if (rs.next()){
                return (E) rs.getObject(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(null,ps,rs);
        }
        return null;
    }
}
