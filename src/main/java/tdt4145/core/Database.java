package tdt4145.core;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Class creates database connection.
 */
public class Database {

    private static Database database;
    private static ComboPooledDataSource comboPooledDataSource;
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String DATABASE_USERNAME = null;
    private static String DATABASE_URL = "jdbc:mysql://mysql.stud.ntnu.no/stefandt_gr148db";
    private static String DATABASE_PASSWORD = null;

    /**
     * Sets up ComboPooledDataSource to handle multiple simultaneous queries to Database
     */
    private Database() {
        // if not given pw & username, read from .properties file
        if (DATABASE_USERNAME == null || DATABASE_PASSWORD == null) {
            try {
                File file = new File("src/main/resources/tdt4145/core/setup.txt");
                System.out.println(file.getAbsolutePath());
                FileReader filer = new FileReader(file);
                BufferedReader br = new BufferedReader(filer);
                String[] sb = new String[3];
                String line;
                int i = 0;
                while ((line = br.readLine()) != null) {
                    sb[i] = line;
                    i = i + 1;
                }
                filer.close();
                DATABASE_USERNAME = sb[0];
                DATABASE_PASSWORD = sb[1];

            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("FAILED TO READ DATABASE PROPERTIES, EXITING...");
                System.exit(-1);
            }
        }

        try {
            comboPooledDataSource = new ComboPooledDataSource();
            comboPooledDataSource.setDriverClass(DATABASE_DRIVER); //loads the jdbc driver
            comboPooledDataSource.setJdbcUrl(DATABASE_URL);
            comboPooledDataSource.setUser(DATABASE_USERNAME);
            comboPooledDataSource.setPassword(DATABASE_PASSWORD);

            comboPooledDataSource.setMaxPoolSize(10);
            comboPooledDataSource.setMaxStatements(100);

            // fixing the timeout
            //comboPooledDataSource.setTestConnectionOnCheckout(true); // if this slows down too much, go for the two below *instead* --> it did.
            // alternative 4 better performance
            comboPooledDataSource.setTestConnectionOnCheckout(false);
            comboPooledDataSource.setTestConnectionOnCheckin(true);
            comboPooledDataSource.setIdleConnectionTestPeriod(30);

            // DO NOT do some aggressive connection age limit or max idle time, rely on the testing to work
        } catch (Exception e) {
            System.out.println("Database connection failed");
        }
    }

    /**
     * Checks if there is an existing instance of Database. If not, it will create one.
     *
     * @return an instance of Database, either the existing one, or a new one.
     */
    public static Database getInstance() {
        if (database == null) database = new Database();
        return database;
    }

    public static void setNewDatabase(String url, String username, String password) { // fixed now
        DATABASE_URL = url;
        setNewDatabase(username, password);
    }

    public static void setNewDatabase(String username, String password) { // fixed now
        DATABASE_USERNAME = username;
        DATABASE_PASSWORD = password;
    }

    /**
     * Returns the connection from Database object.
     *
     * @return a connection to the Database object
     * @throws SQLException TODO: write here
     */
    public Connection getConnection() throws SQLException { // why was this static, if you don't mind me asking?
        return comboPooledDataSource.getConnection();
    }
}
