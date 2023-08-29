package org.possystem.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class BaseDAO {
    static final String DB_URL = "jdbc:mysql://localhost:3306/pos_demo";
    final String USER = "root";
    final String PASS = "root";
    Connection conn;

    BaseDAO(){
        try {
            conn = DriverManager.getConnection(DB_URL,USER,PASS);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

