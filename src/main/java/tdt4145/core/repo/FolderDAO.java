package tdt4145.core.repo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FolderDAO extends TemplateDAO {

    private final Connection connection;

    public FolderDAO() throws SQLException {
        this.connection = super.getConnection();
    }

    /**
     * Creates a new row in the folder database
     * @param name as a String, the name of the folder
     * @return the id of the folder
     */

    public int createFolder(String name) {
        String sqlstatement = "INSERT into Folder(name) VALUES(?)";
        String sqlstatement2 = "SELECT LAST_INSERT_ID();";
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement1 = connection.prepareStatement(sqlstatement);
            PreparedStatement preparedStatement2 = connection.prepareStatement(sqlstatement2)) {
            preparedStatement1.setString(1, name);
            preparedStatement1.executeUpdate();
            resultSet = preparedStatement2.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("last_insert_id()");
            }
            return -2;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return -1;
        }finally {
            Cleanup.closeResultSet(resultSet);
        }
    }

    /**
     * Method to insert a root folder in the database
     * @param folderID takes in the folderid for the folder
     * @param courseID takes in the courseID for the course
     * @param term takes in the term for the course
     * @param year takes in the year for the course
     * @return a boolean as a status for the insert
     */

    public boolean createRootFolder(int folderID, int courseID, String term, int year) {
        String sqlstatement = "INSERT into RootFolder(folderID, courseID, term, year) VALUES(?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)){
            preparedStatement.setInt(1, folderID);
            preparedStatement.setInt(2, courseID);
            preparedStatement.setString(3, term);
            preparedStatement.setInt(4, year);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        }
    }

    public boolean createSubFolder(int folderID, int parentfolder) {
        String sqlstatement = "INSERT into SubFolder(folderID, parent_folder) VALUES(?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)) {
            preparedStatement.setInt(1, folderID);
            preparedStatement.setInt(2, parentfolder);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        }
    }

    public int getTopFolder(int folderID) {
        String sqlstatement = "SELECT parent_folder FROM SubFolder where folderID = ?";
        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)){
            preparedStatement.setInt(1, folderID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("parent_folder");
            }
            return -2;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return -1;
        }finally {
            Cleanup.closeResultSet(resultSet);
        }
    }

    /**
     * Method to get the folder id from the name
     * @param name as a String for the folder
     * @return an int for the folderID, -1 if the query fails
     */

    public int getFolderID(String name) {
        String sqlstatement = "SELECT folderID FROM Folder WHERE name = ?";
        ResultSet resultSet = null;
        try(PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement)) {
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("folderID");
            }
            return -1;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return -1;
        }finally {
            Cleanup.closeResultSet(resultSet);
        }
    }
}
