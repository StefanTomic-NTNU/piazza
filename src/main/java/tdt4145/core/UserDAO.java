package tdt4145.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Data Access Object for User
 *
 * @author Patrick Helvik Legendre
 */

public class UserDAO extends TemplateDAO{
    private Connection connection;

    public UserDAO() throws SQLException {
        this.connection = super.getConnection();
    }


    /**
     * Set user as logged in database
     *
     * @param email users email
     * @return true if log in is successful, false if not
     */

    public boolean logIn(String email) throws SQLException {
        //add in check to see if user is already logged in
        String sqlSentence1 = "SELECT logged_in FROM User WHERE email = ?";
        String sqlSentence2 = "UPDATE User SET logged_in = '1' WHERE email = ?";
        boolean resultBoolean = false;
        ResultSet resultSet;
        int result = 0;

        try (PreparedStatement preparedStatement1 = connection.prepareStatement(sqlSentence1);
             PreparedStatement preparedStatement2 = connection.prepareStatement(sqlSentence2)) {
            connection.setAutoCommit(false);
            preparedStatement1.setString(1, email);
            resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                resultBoolean = resultSet.getBoolean("logged_in");
            }
            if (!resultBoolean) {
                preparedStatement2.setString(1, email);
                result = preparedStatement2.executeUpdate();
                connection.commit();
            }
        } catch (SQLException e) {
            Cleanup.performRollback(connection);
        } finally {
            Cleanup.enableAutoCommit(connection);
        }
        return result == 1;
    }

    /**
     * Set user a logged out in database
     *
     * @param email users email
     * @return true if log out is successful, false if not
     */
    public boolean logOut(String email) throws SQLException {
        String sqlSentence = "UPDATE User SET logged_in = '0' WHERE email = ?"; //change

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSentence)) {
            preparedStatement.setString(1, email);
            int result = preparedStatement.executeUpdate();
            return result == 1;
        }
    }

    public int addUser(String email, String name, char[] password) throws SQLException { //be carefull password has to be char[]
        String sqlSentence = "INSERT INTO User (name, email, password, salt) VALUES(?,?,?,?);";
        String sqlstatement2 = "SELECT LAST_INSERT_ID();";
        ResultSet resultSet;
        byte[] salt = Password.getSalt();
        byte[] hashedPassword = Password.hash(password, salt);
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSentence);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, email);
            preparedStatement.setBytes(3, hashedPassword);
            preparedStatement.setBytes(4, salt);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlstatement2);
            resultSet = preparedStatement.executeQuery();
            return resultSet.getInt("userID"); //returns int no longer boolean
        } catch (SQLException sq) {
            sq.printStackTrace();
            return -1;
        } finally {
            Cleanup.enableAutoCommit((connection));
        }
    }

    /**
     * Sets a password for a user given user id
     *
     * @param user_id  of the user who's password to be changed
     * @param password should be the new password for the user
     * @return true if the new password is successfully set
     * @throws SQLException if setting new password fails
     */
    public boolean setPassword(int user_id, char[] password) throws SQLException {
        byte[] salt = Password.getSalt();
        String sqlSentence2 = "UPDATE User SET password = ? , salt = ? WHERE userID = ?"; //change
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSentence2)) {
            preparedStatement.setBytes(1, Password.hash(password, salt));
            preparedStatement.setBytes(2, salt);
            preparedStatement.setInt(3, user_id);
            int result = preparedStatement.executeUpdate();
            Arrays.fill(password, 'a');
            return result == 1;
        }
    }

    /**
     * Checks if given password is correct for a user, given email
     *
     * @param email    of the user for the password to be checked
     * @param password to be checked if it is correct
     * @return true if password is correct, false if not
     * @throws SQLException if query fails
     */

    public int isPasswordCorrect(String email, char[] password) throws SQLException{
        byte[] salt;
        byte[] hashed;
        ResultSet resultSet = null;
        int result;
        String sqlSentence = "SELECT salt, password, userID FROM User WHERE email = ?";
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlSentence)){
            connection.setAutoCommit(false);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                salt = resultSet.getBytes("salt");
                hashed = resultSet.getBytes("password");
                result = resultSet.getInt("userID");
                if(Password.verify(password, hashed, salt)){
                    return result;
                }
                return -1;
            }
        }finally {
            if (resultSet != null){
                resultSet.close();
            }
        }
        Arrays.fill(password, 'a');
        return  -1;
    }

    public boolean viewedPost(int userID, int threadID) {
        String sqlstatement = "SELECT times_viewed From ViewedPosts WHERE userID = ? and threadID = ?";
        String sqlstatement2 = "UPDATE ViewedPosts SET times_viewed = ? WHERE userID = ? and threadID = ?";
        String sqlstatement3 = "INSERT INTO ViewedPosts(userID, threadID) VALUES(?,?)";
        ResultSet resultSet;
        int times_viewed = -1;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, threadID);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                times_viewed = resultSet.getInt("times_viewed");
            }

            if (times_viewed > -1) {
                preparedStatement = connection.prepareStatement(sqlstatement2);
                preparedStatement.setInt(1, times_viewed);
                preparedStatement.setInt(2, userID);
                preparedStatement.setInt(3, threadID);
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = connection.prepareStatement(sqlstatement3);
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, threadID);
                preparedStatement.executeUpdate();
            }
            return true;

        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } finally {
            Cleanup.enableAutoCommit(connection);
        }
    }

    public boolean likethread(int userID, int threadID) {
        String sqlstatement = "INSERT INTO likes(userID, threadID) VALUES(?,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, threadID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } finally {
            Cleanup.enableAutoCommit(connection);
        }
    }

    public boolean getInstructor(int userID){
        String sqlstatement = "SELECT instructor_privileges FROM User WHERE userID = ?";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            return resultSet.getBoolean("instructor_privileges");
        }catch (SQLException sq){
            sq.printStackTrace();
            return false;
        }
    }

    public int nbcreatedPosts(){
        String sqlstatement = "SELECT User.name, COUNT(ViewedPosts.userID), COUNT(Post.threadID) FROM User, Post, ViewedPost, Thread WHERE " +
                "User.userID = ViewedPosts.userID AND User.userID = Thread.userID AND Thread.threadID = Post.threadID AND" +
                "ViewedPosts.threadID = Thread.threadID";
        ResultSet resultSet = null;
        try {

        }
    }



/* Non-working code. May be useful at some point.
    public User getUser(String email) throws SQLException {
        String sqlSentence = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSentence)) {
            preparedStatement.setString(1, email);
            System.out.println(preparedStatement);
            ResultSet result = preparedStatement.executeQuery();
            System.out.println(result.getInt(1));
            System.out.println(result.getString(2));
            System.out.println(result.getString(3));
            System.out.println(result.getBoolean(7));
            User user = new User(
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getBoolean(7));
            return user;
        } catch (SQLException sqlException) {
            return null;
        }
    }
*/


}
