package com.zmy.blob;

import com.zmy.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *@Description 使用PreparedStartment 实现数据批量操作
 *
 * update、delete本身就具有批量操作的效果
 * 此时的批量操作主要指的是批量插入，使用PreparedStatement如何实现更高效的批量插入？
 *
 * 在数据库中创建一个表，并向goods表中插入两万条数据
 * CREATE TABLE goods(
 * 	id INT PRIMARY KEY auto_increment,
 * 	name VARCHAR(25)
 * );
 * 方式一：statement
 *  Connection conn = JDBCUtil.getConnection();
 *  Statement st = conn.createStatement();
 *  for(int i=0;i<20000;i++){
 *      String sql = "insert into goods(name)values('name_"+i+"')";
 *      st.execute(sql)
 *  }
 *
 *
 *@author Sam  Email:superdouble@yeah.net
 *@create 2021-11-20 19:27
 *@version
 */
public class InsertTest {
    //批量插入，方式二：使用PreparedStatement
    @Test
    public void testInsert2(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);
            for (int i=0;i<100000;i++){
                ps.setString(1,"name_"+i);

                ps.execute();
            }
            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,ps);
        }
    }
    /**
     * 批量插入方式三：
     *  1.addBatch()
     *    executeBatch()
     *    clearBatch()
     *  2.默认情况下mysql不支持batch，需要配置文件的url后加?rewriteBatchedStatements=true
     *  3.
     */
    @Test
    public void testInsert3(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            String sql = "insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);
            for (int i=0;i<1000000;i++){
                ps.setString(1,"name_"+i);
                //1."攒"sql语句
                ps.addBatch();
                if (i%500 ==0){
                    // 2.执行batch
                    ps.executeBatch();
                    //3.清空batch
                    ps.clearBatch();
                }
            }
            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,ps);
        }
    }
    /**
     * 批量操作方式4：
     *
     */
    @Test
    public void testInsert4(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            long start = System.currentTimeMillis();
            conn = JDBCUtils.getConnection();
            //设置不允许自动提交数据
            conn.setAutoCommit(false);
            String sql = "insert into goods(name)values(?)";
            ps = conn.prepareStatement(sql);
            for (int i=0;i<1000000;i++){
                ps.setString(1,"name_"+i);
                //1."攒"sql语句
                ps.addBatch();
                if (i%500 ==0){
                    // 2.执行batch
                    ps.executeBatch();
                    //3.清空batch
                    ps.clearBatch();
                }
            }
            //提交数据
             conn.commit();
            long end = System.currentTimeMillis();
            System.out.println(end-start);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,ps);
        }
    }

}
