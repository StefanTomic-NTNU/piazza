package tdt4145.core.DAOs;

import tdt4145.core.Database;

import java.sql.Connection;
import java.sql.SQLException;

class TemplateDAO implements AutoCloseable {

    private final Connection connection;

    public TemplateDAO() throws SQLException {
        connection = Database.getInstance().getConnection();
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