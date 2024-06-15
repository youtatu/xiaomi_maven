package com.qf.util;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @author wgy
 * @version V1.0
 * @project java2402_xiaomi
 * @package com.qf.util
 * @company 千锋教育
 * @date 2024/6/11 10:05
 */
public class DataSourceUtils {
    //创建数据源(连接池)
    private static DruidDataSource dataSource;
    // ThreadLocal
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();

    static {
        try {
            Properties properties = new Properties();
            ClassLoader classLoader = DataSourceUtils.class.getClassLoader();
            InputStream is = classLoader.getResourceAsStream("druid.properties");
            properties.load(is);
            is.close();
            dataSource = new DruidDataSource();
            dataSource.configFromPropety(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static DataSource getDataSource() {
        return dataSource;
    }

    // 返回链接
    public static Connection getConnection() {
        Connection connection = threadLocal.get();
        try {
            if (connection == null) {
                connection = dataSource.getConnection();
                threadLocal.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 关闭资源
    public static void closeAll(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if(resultSet != null) {
                resultSet.close();
            }
            if(statement != null) {
                statement.close();
            }
            if(connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 事务有关的方法
    public static void begin() throws SQLException {
        Connection connection = getConnection();
        if(connection != null) {
            connection.setAutoCommit(false);
        }
    }
    public static void commit() {
        try {
            Connection connection = getConnection();
            if(connection != null) {
                connection.commit();
                close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void rollback() {
        try {
            Connection connection = getConnection();
            if(connection != null) {
                connection.rollback();
                close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void close() {
        try {
            Connection connection = getConnection();
            if(connection != null) {
                connection.close();
                threadLocal.remove();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
