package tdt4145.core.DAOs;

import tdt4145.core.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CourseDAO extends TemplateDAO {

    private final Connection connection;

    public CourseDAO() throws SQLException {
        this.connection = super.getConnection();
    }

    /**
     * Insert in the db a new row in ActiveCourse
     *
     * @param courseID that takes in the id for the course
     * @param term takes in the term for the course
     * @param year takes in the actual year
     * @param allow_anonymous if the course allows anonymous post or comments
     * @return a status for insert as a boolean
     */
    public boolean createActiveCourse(int courseID, String term, int year, boolean allow_anonymous) {
        String sqlstatement = "INSERT into ActiveCourse(term, year, allow_anonymous, courseID) VALUES(?,?,?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)){
            preparedStatement.setString(1, term);
            preparedStatement.setInt(2, year);
            preparedStatement.setBoolean(3, allow_anonymous);
            preparedStatement.setInt(4, courseID);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param term as string for the actual term for this course
     * @param year as an int for the actual year for this course
     * @param courseID the id of the course
     * @return a boolean, returns true
     * if the course allows users to create anonymous posts or comments in the course and false if the user can not
     * create anonymous posts.
     */

    public boolean getAnonymousCourse(String term, int year, int courseID) {
        String sqlstatement = "SELECT allow_anonymous FROM ActiveCourse WHERE term = ? and year = ? and courseID = ?";
        ResultSet resultSet;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)){
            preparedStatement.setString(1, term);
            preparedStatement.setInt(2, year);
            preparedStatement.setInt(3, courseID);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                return resultSet.getBoolean("allow_anonymous");
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
        return false;
    }

    /**
     * Gets all the courses that are registered in the db
     * @return an arraylist of all the object course that stores courseID and name
     */

    public ArrayList<Course> getCourses(){
        ArrayList<Course>  courses = new ArrayList<>();
        ResultSet resultSet = null;
        String sqlstatement = "SELECT courseID, name FROM Course";
        int courseID = 0;
        String name = "";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)){
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
        }finally {
            Cleanup.closeResultSet(resultSet);
        }
        return courses;
    }
}
