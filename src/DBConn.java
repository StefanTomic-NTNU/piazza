import java.sql.*;

public abstract class DBConn {

    protected Connection connection;

    public DBConn(){
    }

    public void connect() throws SQLException, ClassNotFoundException {
        // Load the JDBC driver
        Class.forName("org.mariadb.jdbc.Driver");
        System.out.println("Driver loaded");

        // Try to connect
        connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/piazzaProject", "admin", "mypass");

        System.out.println("Successfully connected to database");
    }
}


