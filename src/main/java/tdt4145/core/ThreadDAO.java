package tdt4145.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.ArrayList;

public class ThreadDAO extends TemplateDAO {
    private final Connection connection;
    //TODO create get all Courses int table Course
    //TODO maybe create a method for instructor priviligies
    //TODO create search function
    //TODO get statistics on user if user has priviligies, created post, watched posts
    //The output is “user name, number of posts read, number of posts created
    // The result should also include users which have not read or created posts.

    public ThreadDAO() throws SQLException {
        this.connection = super.getConnection();
    }
    //check if allow anonymous in CourseDAO
    public int CreateThread(String text, int userID, boolean anonymous) {
        String sqlstatement = "INSERT into Thread(text, anonymous, userID) VALUES(?,?,?)";
        String sqlstatement2 = "SELECT LAST_INSERT_ID()";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setString(1, text);
            preparedStatement.setBoolean(2, anonymous);
            preparedStatement.setInt(3, userID);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlstatement2);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("last_insert_id()");
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return -1;
    }

    public boolean check_anonymous(int threadID) {
        String sqlstatement = "SELECT anonymous FROM Thread WHERE threadID = ?";
        ResultSet resultSet;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, threadID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getBoolean("anonymous");
            }
            return false;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } finally {
            Cleanup.enableAutoCommit(connection);
        }
    }

    public boolean CreatePost(String title, int colour, int folderID, int threadID) {
        String sqlstatement = "INSERT INTO Post(threadID, title, colour, folderID) VALUES(?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, threadID);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, colour);
            preparedStatement.setInt(4, folderID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } finally {
            Cleanup.enableAutoCommit(connection);
        }
    }

    public boolean CreateComment(int threadID, int parentID) {
        String sqlstatement = "INSERT INTO Comment VALUES(?, ?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, threadID);
            preparedStatement.setInt(2, parentID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } finally {
            Cleanup.enableAutoCommit(connection);
        }
    }

    public int getTopThread(int threadID) {
        String sqlstatement = "SELECT parentID FROM Comment where threadID = ?";
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, threadID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("parentID");
            }
            return -2;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return -1;
        }
    }

    public boolean linkPostTags(int threadID, int tagID) {
        String sqlstatement = "INSERT INTO PostTags(threadID, tagID) VALUES(?, ?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, threadID);
            preparedStatement.setInt(2, tagID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } finally {
            Cleanup.enableAutoCommit(connection);
        }

    }

    public ArrayList<Tag> getTags(){
        ArrayList<Tag> tags = new ArrayList<Tag>();
        String sqlstatement = "SELECT tagID, label FROM Tags";
        ResultSet resultSet = null;
        int tagid = 0;
        String label = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                tagid = resultSet.getInt("tagID");
                label = resultSet.getString("label");
                Tag tag = new Tag(tagid, label);
                tags.add(tag);
            }
            return tags;
        }catch (SQLException sq){
            sq.printStackTrace();
            return tags;
        }

    }

    public int getTagID(String label) {
        String sqlstatement = "SELECT tagID FROM tags WHERE label = ?";
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setString(1, label);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getInt("tagID");
            }
            return -2;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return -1;
        }
    }


}
