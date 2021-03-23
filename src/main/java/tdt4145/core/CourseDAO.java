package tdt4145.core;

import java.lang.invoke.SwitchPoint;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDAO extends TemplateDAO{
    private Connection connection;

    public CourseDAO() throws SQLException {
        this.connection = super.getConnection();
    }

    public boolean createActiveCourse(int courseID, String term, int year , boolean allow_anonymous){
        String sqlstatement = "INSERT into ActiveCourse(term, year, allow_anonymous, courseID) VALUES(?,?,?,?)";
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setString(1, term);
            preparedStatement.setInt(2, year);
            preparedStatement.setBoolean(3, allow_anonymous);
            preparedStatement.setInt(4, courseID);
            preparedStatement.executeUpdate();
            return true;
        }catch (SQLException sq){
            sq.printStackTrace();
            return false;
        }finally {
            Cleanup.enableAutoCommit(connection);
        }
    }

    public boolean getAnonymousCourse(String term, int year, int courseID){
        String sqlstatement = "SELECT allow_anonymous FROM ActiveCourse WHERE term = ? and year = ? and courseID = ?";
        ResultSet resultSet = null;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setString(1, term);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, courseID);
            resultSet = preparedStatement.executeQuery();
            boolean result = resultSet.getBoolean("allow_anonymous");
            return result;
        }catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        }
    }

    public ArrayList<Course> getCourses(){
        ArrayList<Course>  courses = new ArrayList<>();
        ResultSet resultSet = null;
        String sqlstatement = "SELECT courseID, name FROM Course";
        int courseID = 0;
        String name = "";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                courseID = resultSet.getInt("courseID");
                name = resultSet.getString("name");
                Course course = new Course(courseID, name);
                courses.add(course);
            }
            return courses;
        }catch (SQLException sq){
            sq.printStackTrace();
            return courses;
        }
    }




}
