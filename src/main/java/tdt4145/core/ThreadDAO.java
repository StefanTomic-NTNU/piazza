package tdt4145.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class ThreadDAO extends TemplateDAO {
    private Connection connection;

    public ThreadDAO() throws SQLException {
        this.connection = super.getConnection();
    }

    public boolean CreateThread(String text, String Coursename, int userID) {
        //check with anonymous verify with Coursename
        String sqlstatement = "INSERT into Thread(text, anonymous, userID) VALUES(?,?,?)";
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setString(1, text);
            preparedStatement.setBoolean(2, false);
            preparedStatement.setInt(3, userID);
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException sq){
            sq.printStackTrace();
            return false;
        }finally {
            Cleanup.enableAutoCommit(connection);
        }
    }


}
