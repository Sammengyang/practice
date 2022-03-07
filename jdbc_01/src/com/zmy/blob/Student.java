package com.zmy.blob;

import java.util.Date;

/**
 * @author Sam
 * @create 2021-11-19 16:18
 */

/**
 * ORM编程思想(object relational mapping)
 * -一个数据表对应一个java类
 * 表中的一条记录对应j ava类的一一个对象
 * 表中的一个字段对应java类的一一个属性(数据类型)
 */
public class Student {
    private int id;
    private String name;
    private Date birth;
    private String sex;

    public Student() {
    }

    public Student(int id, String name, Date birth, String sex) {
        this.id = id;
        this.name = name;
        this.birth = birth;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birth=" + birth +
                ", sex='" + sex + '\'' +
                '}';
    }
}
