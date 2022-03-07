package com.zmy.dao.junit;

import com.zmy.dao.Users;

import java.sql.Connection;
import java.util.List;

/**
 *@Description 用与规范对于user表的常用操作
 *@author Sam  Email:superdouble@yeah.net
 *@create 2021-11-22 16:40
 *@version
 */
public interface UserDAO {

    /**
     * 将user对象添加到数据库中
     * @param conn
     * @param user
     */
    void insert(Connection conn, Users user) throws Exception;

    /**
     * 针对指定user从数据库表中删除
     * @param conn
     * @param user
     */
    void deleteByUser(Connection conn,String user);

    /**
     * 针对于user对象，修改数据表中的记录
     * @param conn
     * @param user
     */
    void updateByUser(Connection conn,Users user);

    /**
     * 根据指定的user查询数据表中的记录
     * @param conn
     * @param user
     * @return
     */
    Users getUserByuser(Connection conn,String user) throws Exception;

    /**
     *  查询表中所有记录的集合
     * @param conn
     * @return
     */
    List<Users> getAll(Connection conn);

    /**
     * 查询数据表中的数据条目数
     * @param conn
     * @return
     */
    Long getCount(Connection conn);

    /**
     * 返回数据表中最大存款
     * @param conn
     * @return
     */
    int getMaxBalance(Connection conn);
}
