package com.student.util;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

import static com.mysql.cj.conf.PropertyKey.PASSWORD;

public class DBUtil {
    private static String URL = "jdbc:mysql://localhost:3306/student_db";
    private static final String USER = "root";
    private static final String PASSWORD = "@Rahul2963";

    public static Connection getConnection() throws SQLException{
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL,USER,PASSWORD);
        }catch (ClassNotFoundException e){
            throw new SQLException("MySQL Driver Not Found");
        }
    }
}
