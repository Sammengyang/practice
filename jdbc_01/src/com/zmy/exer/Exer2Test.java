package com.zmy.exer;

import com.sun.xml.internal.bind.v2.model.core.ID;
import com.zmy.jdbc.util.JDBCUtils;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Sam  Email:superdouble@yeah.net
 * @Description
 * @create 2021-11-19 22:23
 */
public class Exer2Test {

    /**
     * 创建一个 examstudent 表
     */
    @Test
    public void testCreate() {
        String sql = "create table examstudent (FlowID int(10) key auto_increment," +
                "Type int(5),IDCard varchar(18),ExamCard varchar(15)," +
                "StudentName varchar(20),Location varchar(20),Grage int(10))";
        CreateTest(sql);
    }

    public void CreateTest(String sql) {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //连接数据库
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn, ps);
        }
    }

    /**
     * 向 examstudent 表中添加数据
     * FlowID,Type,IDCard,ExamCard,StudentName,Location,Grage
     */
    @Test
    public void updateTest() {
        Scanner sc = new Scanner(System.in);
        int num = 0;
        do {
            System.out.println("-----1.添加  2.删除------------");
            System.out.println("-----3.查询  0.退出------------");
            System.out.print("请选择>");
            num = sc.nextInt();
            switch (num) {
                case 1:
                    System.out.print("请输入序号：");
                    String flowid = sc.next();
                    System.out.print("请输入四级/六级:");
                    String type = sc.next();
                    System.out.print("请输入身份证号:");
                    String idCard = sc.next();
                    System.out.print("请输入准考证号:");
                    String ExamCard = sc.next();
                    System.out.print("请输入姓名:");
                    String name = sc.next();
                    System.out.print("请输入区域:");
                    String Location = sc.next();
                    System.out.print("请输入成绩:");
                    String grage = sc.next();
                    String sql = "insert into examstudent (FlowID,Type,IDCard,ExamCard,StudentName,Location,Grage)values(?,?,?,?,?,?,?)";
                    int update = update(sql, flowid, type, idCard, ExamCard, name, Location, grage);
                    if (update > 0) {
                        System.out.println("添加成功！");
                    } else {
                        System.out.println("添加失败！");
                    }
                    break;
                case 2:
                    System.out.print("请输入要删除学生的考号:");
                    String examCard = sc.next();
                    String Delesql = "delete from examstudent where ExamCard=?";
                    int delete = update(Delesql, examCard);
                    if (delete > 0) {
                        System.out.println("删除成功！");
                    } else {
                        System.out.println("查无此人！");
                    }
                    break;
                case 3:
                    String ch = null;
                    System.out.print("请选择要输入类型:\n" + "a:准考证号:\n" + "b:身份证号:\n");
                    ch = sc.next();
                    do {
                        switch (ch) {
                            case "a":
                                System.out.println("请输入准考证号:");
                                String examCard3 = sc.next();
                                String ECquerySql = "select * from examstudent where  ExamCard=?";
                                List<Student> EDquerylist = query(Student.class, ECquerySql, examCard3);
                                if (EDquerylist.size() > 0) {
                                    System.out.println("===========查询结果===========");
                                    EDquerylist.forEach(System.out::println);
                                } else {
                                    System.out.println("查无此人！");
                                }
                                ch = null;
                                break;
                            case "b":
                                System.out.println("请输入身份证号:");
                                String idCard3 = sc.next();
                                String IDquerySql = "select * from examstudent where  IDCard=?";
                                List<Student> IDquerylist = query(Student.class, IDquerySql, idCard3);
                                if (IDquerylist.size() > 0) {
                                    System.out.println("===========查询结果===========");
                                    IDquerylist.forEach(System.out::println);
                                } else {
                                    System.out.println("查无此人！");
                                }
                                ch = null;
                                break;
                            default:
                                throw new IllegalStateException("Unexpected value: " + ch);
                        }
                    } while (ch != null);
                    break;
                case 0:
                    System.out.println("退出！");
                    break;
                default:
                    System.out.println("错误！");
                    System.exit(0);
                    break;
            }
        } while (num > 0);
    }


    /**
     * 对数据库进行增、删、改操作
     *
     * @param sql
     * @param args
     * @return
     */
    public int update(String sql, String... args) {

//        //加载配置文件
//        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");
//        Properties pros = new Properties();
//        pros.load(is);
//        //获取配置文件
//        String user = pros.getProperty("user");
//        String  password = pros.getProperty("password");
//        String url = pros.getProperty("url");
//        String dricerClass = pros.getProperty("driverClass");
//        //加载驱动
//        Class.forName(dricerClass);
//        //获取连接
//        Connection conn = DriverManager.getConnection(url,user,password);
        // 获取连接
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, args[i]);
            }
            //执行
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


    /**
     * 对数据库进行查询，并返回符合要求的集合
     *
     * @param clazz
     * @param sql
     * @param args
     * @param <T>
     * @return
     */
    public <T> List<T> query(Class<T> clazz, String sql, String... args) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<T> list = null;

        try {
            // 获取连接
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            //填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setString(i + 1, args[i]);
            }
            //获取结果集
            rs = ps.executeQuery();
            // 获取元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            //通过元数据获取列数
            int columnCount = rsmd.getColumnCount();
            //创建集合对象
            list = new ArrayList<>();
            while (rs.next()) {
                //创建形参clazz类的对象
                T t = clazz.newInstance();
                for (int i = 0; i < columnCount; i++) {
                    //获取每一列的数据
                    Object colValue = rs.getObject(i + 1);
                    //获取每一列的列名
                    String colName = rsmd.getColumnLabel(i + 1);
                    //通过反射，给clazz类的对象指定列赋值
                    Field field = clazz.getDeclaredField(colName);
                    field.setAccessible(true);
                    field.set(t, colValue);
                }
                list.add(t);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            //关闭资源
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}


