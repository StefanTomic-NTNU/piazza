package tdt4145.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class ThreadDAO extends TemplateDAO {
    private Connection connection;

    public ThreadDAO() throws SQLException {
        this.connection = super.getConnection();
    }
    //check if allow anonymous in CourseDAO
    public boolean CreateThread(String text, String Coursename, int userID, boolean anonymous) {
        String sqlstatement = "INSERT into Thread(text, anonymous, userID) VALUES(?,?,?)";
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setString(1, text);
            preparedStatement.setBoolean(2, anonymous);
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

    public boolean check_anonymous(int threadID){
        String sqlstatement = "SELECT anonymous FROM Thread WHERE threadID = ?";
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, threadID);
            resultSet = preparedStatement.executeQuery();
            boolean result = resultSet.getBoolean("anonymous");
            return result;
        }catch (SQLException sq){
            sq.printStackTrace();
            return false;
        }finally {
            Cleanup.enableAutoCommit(connection);
        }
    }

    public boolean CreatePost(String title, int colour, int folderID, String Coursename, int userID, int threadID){
        String sqlstatement = "INSERT into Post(threadID, title, colour, folderID) VALUES(?,?,?,?)";
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, threadID);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, colour);
            preparedStatement.setInt(4, folderID);
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
