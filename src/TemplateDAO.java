import java.sql.Connection;
import java.sql.SQLException;


class TemplateDAO implements AutoCloseable {

    private Connection connection;

    TemplateDAO() throws SQLException, ClassNotFoundException {
        DBConn
        DBConn.connect();
    }

    Connection getConnection() {
        return connection;
    }


    @Override
    public void close() throws SQLException {
        if (connection != null) {

            connection.close();
        }
    }


}
