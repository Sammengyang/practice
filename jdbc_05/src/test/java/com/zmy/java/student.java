package com.zmy.java;

import java.util.Date;

/**
 * @author Sam  Email:superdouble@yeah.net
 * @Description
 * @create 2022-02-15 21:12
 */
public class student {
    private int s_id;
    private String s_name;
    private Date s_birth;
    private String s_sex;

    public student() {
    }
    public student(int s_id, String s_name, Date s_birth, String s_sex) {
        this.s_id = s_id;
        this.s_name = s_name;
        this.s_birth = s_birth;
        this.s_sex = s_sex;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public Date getS_birth() {
        return s_birth;
    }

    public void setS_birth(Date s_birth) {
        this.s_birth = s_birth;
    }

    public String getS_sex() {
        return s_sex;
    }

    public void setS_sex(String s_sex) {
        this.s_sex = s_sex;
    }

    @Override
    public String toString() {
        return "student{" +
                "s_id=" + s_id +
                ", s_name='" + s_name + '\'' +
                ", s_birth=" + s_birth +
                ", s_sex='" + s_sex + '\'' +
                '}';
    }
}
