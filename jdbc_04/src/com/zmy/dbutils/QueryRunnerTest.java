package com.zmy.dbutils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 *@Description dbutils 是数据库工具类，封装了数据库的增删改查操作
 *@author Sam  Email:superdouble@yeah.net
 *@create 2021-11-23 9:32
 *@version
 */
public class QueryRunnerTest {

    // 增删改
    @Test
    public void QueryRunnerTest() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnectionByDruid();
            String sql = "insert into user_table (user,password,balance) values (?,?,?)";
            // 返回值影响了几条数据
            int count = runner.update(conn, sql, "Sam", "123456", "6666");
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn);
        }
    }
    // 查询操作

    /**
     * BeanHandler :是ResultSetHandler接口的实现类，用于封装表中的一条记录
     */
    @Test
    public void QueryTest() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnectionByDruid();
            String sql = "select user,password,balance from user_table where user=?";
            BeanHandler<Users> handler = new BeanHandler<>(Users.class);
            Users zmy = runner.query(conn, sql, handler, "zmy");
            System.out.println(zmy);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn);
        }
    }

    /**
     * BeanListHandler :是ResultSetHandler接口的实现类，用于封装表中的多条记录
     */
    @Test
    public void QueryTest2() {

        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnectionByDruid();
            String sql = "select user,password,balance from user_table where balance<?";
            BeanListHandler<Users> handler = new BeanListHandler<>(Users.class);
            List<Users> list = runner.query(conn, sql, handler, "8888");
            list.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn);
        }
    }

    /**
     * MapHandler :是ResultSetHandler接口的实现类，对应表中的一条记录
     * 将字段与字段对一个的值作为map的键值对
     */
    @Test
    public void QueryTest3() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnectionByDruid();
            String sql = "select user,password,balance from user_table where user=?";
            MapHandler handler = new MapHandler();
            Map<String, Object> map = runner.query(conn, sql, handler, "zmy");
            System.out.println(map);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn);
        }
    }

    /**
     * MapListHandler : 是ResultSetHandler接口的实现类，对应表中的多条记录
     * 将字段与字段对一个的值作为maplist元素的键值对
     */
    @Test
    public void QueryTest4() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnectionByDruid();
            String sql = "select user,password,balance from user_table where balance<?";
            MapListHandler handler = new MapListHandler();
            List<Map<String, Object>> mapList = runner.query(conn, sql, handler, "8888");
            mapList.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn);
        }
    }


    @Test
    public void QueryTest5() {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnectionByDruid();

            String sql = "select count(*) from user_table";

            ScalarHandler handler = new ScalarHandler();
            Long count = (Long) runner.query(conn, sql, handler);
            System.out.println(count);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn);
        }
    }

    //自定义ResultSetHandr
    @Test
    public void QueryTest6() throws SQLException {
        Connection conn = null;
        try {
            QueryRunner runner = new QueryRunner();
            conn = JDBCUtils.getConnectionByDruid();

            String sql = "select user,password,balance from user_table where user=?";

            ResultSetHandler<Users> handler = new ResultSetHandler<Users>() {
                @Override
                public Users handle(ResultSet resultSet) throws SQLException {

                    return null;
                }
            };
            Users query = runner.query(conn, sql, handler, "zmy");
            System.out.println(query);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DbUtils.close(conn);
        }
    }
}


