package com.zmy.preparedstatement.curd;

import com.zmy.jdbc.util.JDBCUtils;
import com.zmy.statement.crud.Student;
import com.zmy.statement.crud.User;
import org.junit.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;

/**
 *
 * @author Sam
 * @create 2021-11-19 16:17
 */
public class QueryForStudentTest {
    /**
     * 针对表的字段名与类的属性名不一致时：
     * 1.必须声明sql时，使用类的属性名来命名字段的别名
     * 2.使用ResultSetMetaData时，需要用getColunmnLabel()来替换getColunmnName()
     *    获取列的别名
     *  Ps:如果sql中没有给字段取别名，getColumnLabel()获取的就是列名
     */
    @Test
    public void querystudent(){
        String sql = "select s_id id,s_name name,s_birth birth,s_sex sex from student where s_id=?";
        Student student = queryForStudent(sql, "10");
        System.out.println(student);
    }

    public Student queryForStudent(String sql,Object ... args){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = JDBCUtils.getConnection();
            ps = conn.prepareStatement(sql);
            // 填充占位符
            for (int i =0;i<args.length;i++){
                ps.setString(i+1,(String) args[i]);
            }
            // 获取结果集
            rs = ps.executeQuery();
            // 获取元数据
            ResultSetMetaData rsmd = rs.getMetaData();
            // 通过元数据获取列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){//判断是否有数据
                Student student = new Student();
                for (int i=0;i<columnCount;i++){
                    //获取每一列的数据
                    Object columnValue = rs.getObject(i+1);
                    // 获取结果集每列的列名
                    //getColumnName:获取表的列明
//                    String columnName = rsmd.getColumnName(i+1);
                    //getColumnName:获取表列的别名
                    String columnName = rsmd.getColumnLabel(i+1);
                    // 通过反射，给student对象指定columnName属性赋值columnValue
                    Field field = Student.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(student,columnValue);
                }
                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closResource(conn,ps,rs);
        }
        return null;
    }

}
