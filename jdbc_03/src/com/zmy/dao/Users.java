package com.zmy.dao;

/**
 * ORM编程思想（）
 * 一个数据表对应一个Java类
 * 表中的一条记录对应java类的一个对象
 * 表中的字段对应Java类对象的一个属性
 *
 *
 * @author Sam
 * @create 2021-11-19 15:03
 */
public class Users {
    private String user;
    private String password;
    private int balance;

    public Users() {
    }
    public Users(String user, String password, int balance) {
        this.user = user;
        this.password = password;
        this.balance = balance;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Users{" +
                "user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", balance=" + balance +
                '}';
    }
}
