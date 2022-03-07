package com.zmy.dao.junit;

import com.zmy.dao.Users;
import com.zmy.util.JDBCUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Sam  Email:superdouble@yeah.net
 * @Description
 * @create 2021-11-22 17:22
 */
class UserDAOImplTest {
    UserDAOImpl dao = new UserDAOImpl();
    @org.junit.jupiter.api.Test
    void insert() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Users sam = new Users("Sam", "123456", 8888);
            dao.insert(conn,sam);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void deleteByUser() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            dao.deleteByUser(conn,"Sam");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void updateByUser() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Users zmy = new Users("zmy", "zmy123456.", 16000);
            dao.updateByUser(conn,zmy);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void getUserByuser() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Users zmy = dao.getUserByuser(conn, "zmy");
            System.out.println(zmy);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void getAll() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            List<Users> list = dao.getAll(conn);
            list.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void getCount() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            Long count = dao.getCount(conn);
            System.out.println(count);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,null);
        }
    }

    @org.junit.jupiter.api.Test
    void getMaxBalance() {
        Connection conn = null;
        try {
            conn = JDBCUtils.getConnection();
            int maxBalance = dao.getMaxBalance(conn);
            System.out.println(maxBalance);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,null);
        }
    }
}