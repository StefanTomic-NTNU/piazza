package tdt4145.core.repo;

import tdt4145.core.model.Tag;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Dao class that addresses the thread, post and comment table
 */
public class ThreadDAO extends TemplateDAO {

    private final Connection connection;

    public ThreadDAO() throws SQLException {
        this.connection = super.getConnection();
    }


    /**
     * This method creates a new row in the db to create a new thread.
     * @param text is a text of strings for a thread
     * @param userID is the userid
     * @param anonymous is a boolean that permits the user to create an anonymous post or not
     * @return the id of the thread if not returns -1
     */
    public int CreateThread(String text, int userID, boolean anonymous) {
        String sqlstatement = "INSERT into Thread(text, anonymous, userID) VALUES(?,?,?)";
        String sqlstatement2 = "SELECT LAST_INSERT_ID()";
        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
             PreparedStatement preparedStatement2 = connection.prepareStatement(sqlstatement2)){
            preparedStatement.setString(1, text);
            preparedStatement.setBoolean(2, anonymous);
            preparedStatement.setInt(3, userID);
            preparedStatement.executeUpdate();
            resultSet = preparedStatement2.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("last_insert_id()");
            }
            return -1;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return -1;
        }finally {
            Cleanup.closeResultSet(resultSet);
        }
    }

    public boolean checkAnonymous(int threadID) {
        String sqlstatement = "SELECT anonymous FROM Thread WHERE threadID = ?";
        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)){
            preparedStatement.setInt(1, threadID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("anonymous");
            }
            return false;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } finally {
            Cleanup.closeResultSet(resultSet);
        }
    }

    /**
     * Method to create a new post in the db. By inserting the values into the post table.
     * @param title as the title of the post
     * @param colour used to give a color for the post
     * @param folderID the id of the folder, the post belongs to
     * @param threadID the id of the thread for the post
     * @return a boolean as status for the insert
     */
    public boolean CreatePost(String title, int colour, int folderID, int threadID) {
        String sqlstatement = "INSERT INTO Post(threadID, title, colour, folderID) VALUES(?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)){
            preparedStatement.setInt(1, threadID);
            preparedStatement.setString(2, title);
            preparedStatement.setInt(3, colour);
            preparedStatement.setInt(4, folderID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        }
    }

    /**
     * Method to create a comment by adding the values into the database.
     * @param threadID the id of the thread, the actual id for the comment
     * @param parentID the id of the thread, the comment is attached to.
     * @return a status for the insert with a boolean.
     */
    public boolean CreateComment(int threadID, int parentID) {
        String sqlstatement = "INSERT INTO Comment VALUES(?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)){
            preparedStatement.setInt(1, threadID);
            preparedStatement.setInt(2, parentID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        }
    }

    /**
     * method to get the top thread id for a comment.
     * @param threadID for the actual comment
     * @return the id of the parent thread.
     */
    public int getTopThread(int threadID) {
        String sqlstatement = "SELECT parentID FROM Comment where threadID = ?";
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)) {
            preparedStatement.setInt(1, threadID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("parentID");
            }
            return -1;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return -1;
        }finally {
            Cleanup.closeResultSet(resultSet);
        }
    }

    /**
     * Method to insert tags for a post.
     * @param threadID the id of the post
     * @param tagID the id of tags attached
     * @return a status for the insert with a boolean.
     */

    public boolean linkPostTag(int threadID, int tagID) {
        String sqlstatement2 = "INSERT INTO PostTag(threadID, tagID) VALUES(?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement2)) {
            preparedStatement.setInt(1, threadID);
            preparedStatement.setInt(2, tagID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return false;
    }

    /**
     * Method to get all tags registered in the database
     * @return an array list of tags (object) that stores the id and label
     */

    public ArrayList<Tag> getTags() {
        ArrayList<Tag> tags = new ArrayList<Tag>();
        String sqlstatement = "SELECT tagID, label FROM Tag";
        ResultSet resultSet = null;
        int tagid = 0;
        String label = "";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)) {
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                tagid = resultSet.getInt("tagID");
                label = resultSet.getString("label");
                Tag tag = new Tag(tagid, label);
                tags.add(tag);
            }
            return tags;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return tags;
        }finally {
            Cleanup.closeResultSet(resultSet);
        }

    }

    /**
     *
     * @param label as a String
     * @return the id for the label in the tags table
     */

    public int getTagID(String label) {
        String sqlstatement = "SELECT tagID FROM Tag WHERE label = ?";
        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)){
            preparedStatement.setString(1, label);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("tagID");
            }
            return -1;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return -1;
        }finally {
            Cleanup.closeResultSet(resultSet);
        }
    }

    /**
     * Method to search all the post in both the title and the text for a specific keyword that is inputed by the user.
     * @param keyword as a string input by the user
     * @return an arraylist of integers that stores all the post ids from the db
     */

    public ArrayList<Integer> searchByKeyword(String keyword){
        String sqlstatement = "SELECT DISTINCT Thread.threadID FROM Post, Thread WHERE " +
                "((Post.threadID = Thread.threadID AND Post.title LIKE ?) OR (Thread.text LIKE ?))";

        ResultSet resultSet = null;
        int id = 0;
        ArrayList<Integer> ids = new ArrayList<>();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setString(1, "%" + keyword + "%");
            preparedStatement.setString(2, "%" + keyword + "%");

            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                id = resultSet.getInt("threadID");
                ids.add(id);
            }
            return ids;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return ids;
        }finally {
            Cleanup.closeResultSet(resultSet);
        }
    }
}
