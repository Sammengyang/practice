package com.zmy.exer;

import com.zmy.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Sam  Email:superdouble@yeah.net
 * @Description
 * @create 2021-11-19 21:55
 */
// 练习一：
public class Exer1Test {

    @Test
    public void testInsert() {
        Scanner sc = new Scanner(System.in);
        System.out.print("请输入id：");
        String id = sc.next();
        System.out.print("请输入名字：");
        String name = sc.next();
        System.out.print("请输入性别：");
        String sex = sc.next();
        System.out.print("请输入出生日期：");
        String birth = sc.next();

        String sql ="insert into student(s_id,s_name,s_sex,s_birth)values(?,?,?,?)";
        int insertCount = update(sql, id, name, sex, birth);
        if (insertCount > 0){
            System.out.println("添加成功！");
        }else{
            System.out.println("添加失败！");
        }
    }

    public int update(String sql, Object... args) {

        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, (String) args[i]);
            }
            // 如果执行的是查询操作，有返回结果，此方法返回true
            // 如果执行的是增、删、改操作，无返回值
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn, ps);
        }

        return 0;
    }
}
