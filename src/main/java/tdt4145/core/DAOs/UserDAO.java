package tdt4145.core.DAOs;

import tdt4145.core.UserOverview;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class UserDAO extends TemplateDAO {

    private final Connection connection;

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

    /**
     * Checks if user has the email in the database before
     * Add user to the database and retrieves the id for the user that has been created.
     * @param email users email
     * @param name users name
     * @param password users password with char[]
     * @return the id of the user that has been created
     *
     */

    public int addUser(String email, String name, char[] password){ //be carefull password has to be char[]
        String sqlstatement = "SELECT name FROM User WHERE email = ?";
        String sqlSentence = "INSERT INTO User (name, email, password, salt) VALUES(?,?,?,?);";
        String sqlstatement2 = "SELECT LAST_INSERT_ID();";
        ResultSet resultSet = null;
        byte[] salt = Password.getSalt();
        byte[] hashedPassword = Password.hash(password, salt);
        try (PreparedStatement preparedStatement1 = connection.prepareStatement(sqlstatement);
        PreparedStatement preparedStatement2 = connection.prepareStatement(sqlSentence);
        PreparedStatement preparedStatement3 = connection.prepareStatement(sqlstatement2)){

            connection.setAutoCommit(false);
            preparedStatement1.setString(1, email);
            resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                Cleanup.enableAutoCommit(connection);
                Cleanup.closeResultSet(resultSet);
                return -2;
            }
            preparedStatement2.setString(1, name);
            preparedStatement2.setString(2, email);
            preparedStatement2.setBytes(3, hashedPassword);
            preparedStatement2.setBytes(4, salt);
            preparedStatement2.executeUpdate();
            connection.commit();
            resultSet = preparedStatement3.executeQuery();
            if(resultSet.next()){
                Cleanup.enableAutoCommit((connection));
                return resultSet.getInt("last_insert_id()"); //returns int no longer boolean
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
            Cleanup.performRollback(connection);
        } finally {
            Cleanup.enableAutoCommit((connection));
            Cleanup.closeResultSet(resultSet);
        }
        return -1;
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
    public int isPasswordCorrect(String email, char[] password) throws SQLException {
        byte[] salt;
        byte[] hashed;
        ResultSet resultSet = null;
        int result;
        String sqlSentence = "SELECT salt, password, userID FROM User WHERE email = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSentence)) {
            connection.setAutoCommit(false);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                salt = resultSet.getBytes("salt");
                hashed = resultSet.getBytes("password");
                result = resultSet.getInt("userID");
                if (Password.verify(password, hashed, salt)) {
                    return result;
                }
                return -1;
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        Arrays.fill(password, 'a');
        return -1;
    }

    /**
     *
     * If viewedpost allready exist update the times_viewed variable by adding 1. If viewedpost does not exist
     * append a new row to the database
     * @param userID id of the user
     * @param threadID id of the thread
     * @return a status for the insert
     */

    public boolean viewedPost(int userID, int threadID) {
        String sqlstatement = "SELECT times_viewed From ViewedPost WHERE userID = ? and threadID = ?";
        String sqlstatement2 = "UPDATE ViewedPost SET times_viewed = ? WHERE userID = ? and threadID = ?";
        String sqlstatement3 = "INSERT INTO ViewedPost(userID, threadID) VALUES(?,?)";
        ResultSet resultSet = null;
        int times_viewed = -1;

        try (PreparedStatement preparedStatement1 = connection.prepareStatement(sqlstatement);
             PreparedStatement preparedStatement2 = connection.prepareStatement(sqlstatement2);
             PreparedStatement preparedStatement3 = connection.prepareStatement(sqlstatement3)) {

            connection.setAutoCommit(false);

            preparedStatement1.setInt(1, userID);
            preparedStatement1.setInt(2, threadID);
            resultSet = preparedStatement1.executeQuery();
            if (resultSet.next()) {
                times_viewed = resultSet.getInt("times_viewed");
            }

            if (times_viewed > -1) {
                preparedStatement2.setInt(1, times_viewed);
                preparedStatement2.setInt(2, userID);
                preparedStatement2.setInt(3, threadID);
                preparedStatement2.executeUpdate();
                connection.commit();
            } else {
                preparedStatement3.setInt(1, userID);
                preparedStatement3.setInt(2, threadID);
                preparedStatement3.executeUpdate();
                connection.commit();
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
            Cleanup.performRollback(connection);
        } finally {
            Cleanup.enableAutoCommit(connection);
            Cleanup.closeResultSet(resultSet);
        }
        return true;
    }

    /**
     *
     * inserts in the database a like for a thread.
     * @param userID id of the user
     * @param threadID id of the thread
     * @return a status for the insert
     */

    public boolean likeThread(int userID, int threadID) {
        String sqlstatement = "INSERT INTO LikesThread(userID, threadID) VALUES(?,?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)) {
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, threadID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return false;
    }

    /**
     * checks the database if the user has instructor privileges.
     * @param userID id of the user
     * @return a boolean with true if a user has instructor privileges and false if it does not
     */

    public boolean getInstructor(int userID) {
        String sqlstatement = "SELECT instructor_privileges FROM User WHERE userID = ?";
        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)){
            preparedStatement.setInt(1, userID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean("instructor_privileges");
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }finally {
            Cleanup.closeResultSet(resultSet);
        }
        return false;
    }

    /**
     * The method ask the db for the name, the number of read posts and the number of post each user has created and
     * sorts it by the number of post read.
     * @return an arrayList of userOverview objects that give statistics about the users on piazza
     */

    public ArrayList<UserOverview> overviewStatistics() {
        String sqlstatement = "SELECT DISTINCT User.name, COUNT(ViewedPosts.userID) AS nbreadpost, COUNT(p.threadID) AS nbpost" +
                " FROM " +
                "User LEFT OUTER JOIN Thread ON User.userID = Thread.userID " +
                "LEFT OUTER JOIN Post as p ON p.threadID = Thread.threadID " +
                "LEFT OUTER JOIN ViewedPosts ON User.userID = ViewedPosts.userID " +
                "GROUP BY User.name ORDER BY nbreadpost DESC";
        ResultSet resultSet = null;
        ArrayList<UserOverview> userOverviews = new ArrayList<>();
        String name = "";
        int nbposts = 0;
        int nbreadpost = 0;

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)){
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                name = resultSet.getString("name");
                nbreadpost = resultSet.getInt("nbreadpost");
                nbposts = resultSet.getInt("nbpost");
                UserOverview userOverview = new UserOverview(name, nbposts, nbreadpost);
                userOverviews.add(userOverview);
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }finally {
            Cleanup.closeResultSet(resultSet);
        }
        return userOverviews;
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
