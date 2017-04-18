package de.oster.hibernate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HibTest {
    public static void main(String[] args) {
        String jdbcUrl = "jdbc:mysql://127.0.0.1:3307/hb_student_tracker?useSSL=false";
        String user = "root";
        String passwd = "777matriza";

        try {
            System.out.println("Connection to database: " + jdbcUrl);

            Connection myConn = DriverManager.getConnection(jdbcUrl, user, passwd);

            System.out.println("Connection is success.");
            System.out.println(myConn.getSchema());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
