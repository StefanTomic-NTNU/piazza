package tdt4145.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseDAO extends TemplateDAO {
    private final Connection connection;

    public CourseDAO() throws SQLException {
        this.connection = super.getConnection();
    }

    public boolean createActiveCourse(int courseID, String term, int year, boolean allow_anonymous) {
        String sqlstatement = "INSERT into ActiveCourse(term, year, allow_anonymous, courseID) VALUES(?,?,?,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setString(1, term);
            preparedStatement.setInt(2, year);
            preparedStatement.setBoolean(3, allow_anonymous);
            preparedStatement.setInt(4, courseID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } finally {
            Cleanup.enableAutoCommit(connection);
        }
    }

    public boolean getAnonymousCourse(String term, int year, int courseID) {
        String sqlstatement = "SELECT allow_anonymous FROM ActiveCourse WHERE term = ? and year = ? and courseID = ?";
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setString(1, term);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, courseID);
            resultSet = preparedStatement.executeQuery();
            return resultSet.getBoolean("allow_anonymous");
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        }
    }


}