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

    public UserDAO() throws SQLException{
        this.connection = super.getConnection();
    }


    /**
     * Set user as logged in database
     *
     * @param email users email
     * @return true if log in is successful, false if not
     */

    public boolean logIn(String email) throws SQLException{
        //add in check to see if user is already logged in
        String sqlSentence1 = "SELECT logged_in FROM users WHERE email = ?"; //change pls
        String sqlSentence2 = "UPDATE users SET logged_in = '1' WHERE email = ?";
        boolean resultBoolean = false;
        ResultSet resultSet;
        int result = 0;

        try(PreparedStatement preparedStatement1 = connection.prepareStatement(sqlSentence1);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlSentence2)){
            connection.setAutoCommit(false);
            preparedStatement1.setString(1, email);
            resultSet = preparedStatement1.executeQuery();
            if(resultSet.next()){
                resultBoolean = resultSet.getBoolean("logged_in");
            }
            if(!resultBoolean) {
                preparedStatement2.setString(1, email);
                result = preparedStatement2.executeUpdate();
                connection.commit();
            }
        }catch (SQLException e){
            Cleanup.performRollback(connection);
        }finally {
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
        String sqlSentence = "UPDATE users SET logged_in = '0' WHERE email = ?"; //change

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlSentence)){
            preparedStatement.setString(1, email);
            int result = preparedStatement.executeUpdate();
            return result == 1;
        }
    }

    public boolean addUser(String email, String nickname, char[] password) throws  SQLException{
        String sqlSentence = "INSERT INTO users (email, password, salt, nickname) VALUES(?,?,?,?);"; //change

        byte[] salt = Password.getSalt();
        byte[] hashedPassword = Password.hash(password, salt);

        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlSentence)){
            preparedStatement.setString(1, email);
            preparedStatement.setBytes(2, hashedPassword);
            preparedStatement.setBytes(3, salt);
            preparedStatement.setString(4, nickname);
            int result = preparedStatement.executeUpdate();
            Arrays.fill(password, 'a');
            return result == 1;
        }
    }

    /**
     * Sets a password for a user given user id
     *
     * @param user_id of the user who's password to be changed
     * @param password should be the new password for the user
     * @return true if the new password is successfully set
     * @throws SQLException if setting new password fails
     */
    public boolean setPassword(int user_id, char[] password) throws SQLException{
        byte[] salt = Password.getSalt();
        String sqlSentence2 = "UPDATE users SET password = ? , salt = ? WHERE user_id = ?"; //change
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlSentence2)){
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
     * @param email of the user for the password to be checked
     * @param password to be checked if it is correct
     * @return true if password is correct, false if not
     * @throws SQLException if query fails
     */
    public boolean isPasswordCorrect(String email, char[] password) throws SQLException{
        byte[] salt;
        byte[] hashed;
        ResultSet resultSet = null;
        String sqlSentence = "SELECT salt, password FROM users WHERE email = ?"; //change
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlSentence)){
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                salt = resultSet.getBytes("salt");
                hashed = resultSet.getBytes("password");
                return Password.verify(password, hashed, salt);
            }

        }finally {
            if (resultSet != null){
                resultSet.close();
            }
        }
        Arrays.fill(password, 'a');
        return false;
    }







}
