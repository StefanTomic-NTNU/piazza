package tdt4145.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DBConn {

    protected Connection connection;

    public DBConn() {
    }

    public void connect() throws SQLException {
        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("Driver loaded");

        //Connection connection = DriverManager.getConnection
        //        ("jdbc:mysql://localhost/databpros", "root", "Lamarchequidurelong41");

        connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/piazzaproject", "admin", "mypass");


        System.out.println("Successfully connected to database");
    }
}


