package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String URL = "jdbc:mysql://localhost:3306/testingdata";
    private static final String username = "root";
    private static final String password = "KamilxXx1992";


    public Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, username, password);
            System.out.println();


        }catch (SQLException e) {
            e.printStackTrace();

        }
        return connection;
    }

    }

