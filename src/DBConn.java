import java.sql.*;

public class DBConn {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
        //Class.forName("org.mariadb.jdbc.Driver");
        System.out.println("Driver loaded");

        // Try to connect
        Connection connection = DriverManager.getConnection
                ("jdbc:mysql://localhost/databpros", "root", "Lamarchequidurelong41");

        System.out.println("Successfully connected to database");
    }
}


