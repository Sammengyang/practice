package com.zmy.dao.junit;

import com.zmy.dao.BaseDAO;
import com.zmy.dao.Users;

import java.sql.Connection;
import java.util.List;

/**
 * @author Sam  Email:superdouble@yeah.net
 * @Description
 * @create 2021-11-22 16:51
 */
public class UserDAOImpl extends BaseDAO implements UserDAO {
    @Override
    public void insert(Connection conn, Users user) {
        String sql ="insert into user_table values(?,?,?)";
        update(conn,sql,user.getUser(),user.getPassword(), String.valueOf(user.getBalance()));
    }

    @Override
    public void deleteByUser(Connection conn, String user) {
        String sql = "delete from user_table where user=?";
        update(conn,sql,user);
    }

    @Override
    public void updateByUser(Connection conn, Users user) {
        String sql = "update user_table set password=?,balance=? where user=?";
        update(conn,sql,user.getPassword(),String.valueOf(user.getBalance()),user.getUser());
    }

    @Override
    public Users getUserByuser(Connection conn, String user) throws Exception {
        String sql = "select * from user_table where user=?";
        Users use = (Users) getInstance(conn, Users.class, sql, user);
        return use;
    }

    @Override
    public List<Users> getAll(Connection conn) {
        String sql="select * from user_table";
        List<Users> list = getForlist(conn, Users.class, sql);
        return list;
    }

    @Override
    public Long getCount(Connection conn) {
        String sql ="select count(*) from user_table";
        return getValue(conn, sql);
    }

    @Override
    public int getMaxBalance(Connection conn) {
        String sql = "select max(balance) from user_table";
        return getValue(conn,sql);
    }
}
