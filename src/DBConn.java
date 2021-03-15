import java.sql.*;

public class DBConn {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Load the JDBC driver
        Class.forName("org.mariadb.jdbc.Driver");
        System.out.println("Driver loaded");

        // Try to connect
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/piazzaProject", "", "");

        System.out.println("Successfully connected to database");
    }
}


