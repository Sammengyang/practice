package com.zmy.java;

import org.junit.Test;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Sam  Email:superdouble@yeah.net
 * @Description
 * @create 2022-02-15 19:18
 */
public class TestConnection1{
    /*
        使用PreparedStatement实现数据库增删改
     */
    @Test // 添加测试
    public void testAdd(){
        String sql = "insert into student (s_id,s_name,s_birth,s_sex)values(?,?,?,?)";
        JDBCUtils.update(sql,12,"sam","2020-12-10","男");
    }
    @Test // 删除测试
    public void testDel(){
        String sql = "delete from student where s_id=?";
        JDBCUtils.update(sql,12);
    }
    @Test // 修改测试
    public void testAlter(){
        String sql = "update student set s_name=?where s_id=?";
        JDBCUtils.update(sql,"sam",11);
    }
    @Test
    public void testQuery(){
        String sql = "select s_id,s_name,s_birth,s_sex from student where s_id=?";
        student student = null;

        System.out.println(student);
    }

}
