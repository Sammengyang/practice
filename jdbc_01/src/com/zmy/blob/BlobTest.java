package com.zmy.blob;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader;
import com.zmy.jdbc.util.JDBCUtils;
import org.junit.Test;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;

/**
 *@Description 测试使用 PreparedStatement操作 Blob 类型的数据
 *@author Sam  Email:superdouble@yeah.net
 *@create 2021-11-20 11:06
 *@version
 */
public class BlobTest {

    /**
     * 向数据表student插入Blob类型的字段
     */
    @Test
    public void testInsert(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "alter table student add photo MEDIUMBLOB";
            ps = conn.prepareStatement(sql);
            ps.execute();
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
     * 向数据表中插入图片
     */
    @Test
    public void testUpdate(){
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "update student set photo=? where s_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(2,"4");
            FileInputStream field = new FileInputStream("t3.jpg");
            ps.setBlob(1,field);
            ps.execute();
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
     * 查询数据表中的Blob类型的字段
     */
    @Test
    public void testQuery(){
        Connection conn = null;
        PreparedStatement ps = null;
        InputStream is = null;
        ResultSet rs =null;
        FileOutputStream  fos = null;
        try {
            conn = JDBCUtils.getConnection();
            String sql = "select * from student where s_id=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1,"4");

            //获取元数据
            rs = ps.executeQuery();
            if (rs.next()){
//                int id = rs.getInt(1);
//                String name = rs.getString(2);
//                Date birth = rs.getDate(3);
//                String sex = rs.getString(4);
                // 方式二：
                int id = rs.getInt("s_id");
                String name = rs.getString("s_name");
                Date birth = rs.getDate("s_birth");
                String sex = rs.getString("s_sex");
                Student student = new Student(id,name,birth,sex);
                System.out.println(student);

                //将数据库的文件下载下来，保存到本地
                Blob photo = rs.getBlob("photo");
                is = photo.getBinaryStream();
                fos = new FileOutputStream("same.jpg");
                byte[] buffer = new byte[1024];
                int len;
                while ((len=is.read(buffer)) !=-1){
                    fos.write(buffer,0,len);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            JDBCUtils.closResource(conn,ps,rs);
            if (is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos!=null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
