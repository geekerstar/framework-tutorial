package com.geekerstar.flink.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author geekerstar
 * @date 2021/9/4 15:12
 * @description
 */
public class MySQLUtils {

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/pk_flink_imooc", "root", "root");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void close(Connection connection, PreparedStatement pstmt) {
        if(null != pstmt) {
            try {
                pstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(null != connection) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(getConnection());
    }
}

