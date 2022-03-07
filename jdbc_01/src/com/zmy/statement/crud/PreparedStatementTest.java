package com.zmy.statement.crud;

import com.zmy.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Scanner;

/**
 *@Description
 *  演示PreparedStadement替换Statement，解决SQL注入问题
 *
 *@author Sam  Email:superdouble@yeah.net
 *@create 2021-11-19 20:46
 *@version
 */

public class PreparedStatementTest {


    @Test
    public void testLogin() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("请输入用户名：");
        String user = scanner.next();
        System.out.print("请输入密码：");
        String password = scanner.next();

        String sql = "SELECT user,password FROM user_table  WHERE `user`= ? AND `password`=?";
        User returnuser = getInstance(User.class,sql,user,password);
        if (returnuser != null){
            System.out.println("登录成功！");
        }else{
            System.out.println("登录失败！");
        }
    }

    public static <T> T getInstance(Class<T> clazz, String sql, Object... args){

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i=0;i<args.length;i++){
                ps.setString(i+1, (String) args[i]);
            }
            //获取结果集
            rs = ps.executeQuery();
            //获取元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过元数据获取列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                T t = clazz.newInstance();
                for (int i=0;i<columnCount;i++){
                    //获取每一列的数据
                    Object colunmValue = rs.getObject(i + 1);
                    // 获取每一列的列名
                    String columnName = rsmd.getColumnLabel(i + 1);
                    // 通过反射给T对象指定的columnName属性赋值columnvalue
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t,colunmValue);
                }
                return t;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,ps,rs);
        }
        return null;
    }

}
