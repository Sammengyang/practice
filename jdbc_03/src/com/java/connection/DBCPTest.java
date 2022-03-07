package com.java.connection;

import jdk.nashorn.api.scripting.ScriptUtils;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Sam  Email:superdouble@yeah.net
 * @Description
 * @create 2021-11-22 22:03
 */

public class DBCPTest {
    //方式一:
    @Test
    public void testGetConneection1() throws SQLException {
        // 创建dbcp的数据库连接池
        BasicDataSource source =new BasicDataSource();
        // 设置基本信息
        source.setDriverClassName("com.mysql.jdbc.Driver");
        source.setUrl("jdbc:mysql://localhost:3306/javatest");
        source.setUsername("root");
        source.setPassword("123456");
        // 设置数据库管理的相关属性
        source.setInitialSize(10);
        source.setMaxActive(100);

        Connection conn = source.getConnection();
        System.out.println(conn);

    }
    // 方式二:使用配置文件

    /**
     *
     * @throws Exception
     */
    @Test
    public void testGetConneection2() throws Exception {
        Properties pros= new Properties();
        //加载配置文件
        // 方式一:
//        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("DBCP.properties");
        //方式二：
        FileInputStream is = new FileInputStream(new File("src/DBCP.properties"));
        pros.load(is);
        DataSource source = BasicDataSourceFactory.createDataSource(pros);

        Connection conn = source.getConnection();
        System.out.println(conn);
    }
}
