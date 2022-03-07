package com.zmy.transaction;

import com.zmy.util.JDBCUtils;
import org.junit.Test;
import sun.util.resources.cldr.naq.CalendarData_naq_NA;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *@Description
 *@author Sam  Email:superdouble@yeah.net
 *@create 2021-11-22 9:05
 *@version
 */

/**
 *  1.什么叫数据库事物？
 *    事物：一组逻辑操作单元，使数据库一种状态变换到另一种状态。
 *        >一组逻辑单元，一个或多个DML操作
 *   2.事物的处理原则：
 *     保证所有事物都作为一个工作单元来执行，即使出现故障，都不能改变这种方式
 *     事物执行多个操作时，要么所有事物都被提交（commit），要么这些修改就永久保存
 *     要么数据库管理系统放弃所有修改，整个事物回滚（rollback）到最初状态
 *   3.数据一旦提交，就不能回滚
 *
 *   4.哪些操作会导致数据自动提交？
 *      > DDL操作一旦执行，都会自动提交
 *      >DML默认情况下；一旦执行，就会自动提交
 *          > set autocommit = false的方式去校DML操作自动提交
 *      >默认关闭连接时，会自动提交
 *
 */
public class TranscactionTest {

    @Test
    public void test1(){
        String sql1 = "update user_table set balance = balance-100 where user=?";
        String sql2 = "update user_table set balance = balance+100 where user=?";
//        update(sql1,"BB");
//        update(sql2,"zmy");
    }

    // 通用的增删改操作  version 2.0
    public int update(Connection conn, String sql, String ...args) throws Exception {
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            for (int i=0;i<args.length;i++){
                ps.setString(i+1,args[i]);
            }
            return ps.executeUpdate();
        }
        finally{
            JDBCUtils.closResource(null,ps);
        }

    }
    @Test
    public void testTransactinSelect() throws Exception{
        Connection conn = JDBCUtils.getConnection();
        // 获取当前的隔离级别
        System.out.println(conn.getTransactionIsolation());
        // 设置数据库的隔离级别
        conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        //取消自动提交数据
        conn.setAutoCommit(false);
        String sql = "select user,password,balance from user_table where user=?";
        User user = getInstance(conn, User.class, sql, "zmy");
        System.out.println(user);

    }

    @Test
    public void testTransactionUpdate() throws Exception{
        Connection conn = JDBCUtils.getConnection();
        System.out.println(conn.getTransactionIsolation());
        // 取消自动提交
        conn.setAutoCommit(false);
        String sql = "update user_table set balance =? where user=?";
        update(conn,sql,"15000","zmy");

        Thread.sleep(15000);
        System.out.println("修改结束");
    }
    /**
     * 通用的查询操作，用于返回数据表的一条记录（version 2.0 考虑事务）
     */
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
}


