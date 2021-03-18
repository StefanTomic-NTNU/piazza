package tdt4145;

import java.sql.*;

public abstract class DBConn {

    protected Connection connection;

    public DBConn(){
    }

    public void connect() throws SQLException {
        // Load the JDBC driver
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("org.mariadb.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }


        System.out.println("Driver loaded");


        //Connection connection = DriverManager.getConnection
        //        ("jdbc:mysql://localhost/databpros", "root", "Lamarchequidurelong41");

        connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/piazzaProject", "admin", "mypass");


        System.out.println("Successfully connected to database");
    }
}


