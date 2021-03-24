package tdt4145.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FolderDAO extends TemplateDAO {

    private final Connection connection;

    public FolderDAO() throws SQLException {
        this.connection = super.getConnection();
    }

    public int createFolder(String name) {
        String sqlstatement = "INSERT into Folder(name) VALUES(?)";
        String sqlstatement2 = "SELECT LAST_INSERT_ID();";
        ResultSet resultSet;
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
            preparedStatement = connection.prepareStatement(sqlstatement2);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("folderID");
            }
            return -2;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return -1;
        } finally {
            Cleanup.enableAutoCommit(connection);
        }
    }

    public boolean createRootFolder(int folderID, int courseID, String term, int year) {
        String sqlstatement = "INSERT into RootFolder(folderID, courseID, term, year) VALUES(?, ?, ?, ?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, folderID);
            preparedStatement.setInt(2, courseID);
            preparedStatement.setString(3, term);
            preparedStatement.setInt(4, year);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } finally {
            Cleanup.enableAutoCommit(connection);
        }
    }

    public boolean createSubFolder(int folderID, int parentfolder) {
        String sqlstatement = "INSERT into SubFolder(folderID, parent_folder) VALUES(?,?)";
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, folderID);
            preparedStatement.setInt(2, parentfolder);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return false;
        } finally {
            Cleanup.enableAutoCommit(connection);
        }
    }

    public int getTopFolder(int folderID) {
        String sqlstatement = "SELECT parent_folder FROM SubFolder where folderID = ?";
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setInt(1, folderID);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("parent_folder");
            }
            return -2;
        } catch (SQLException sq) {
            sq.printStackTrace();
            return -1;
        }
    }

    public int getFolderID(String name) {
        String sqlstatement = "SELECT folderID FROM folder WHERE name = ?";
        ResultSet resultSet;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sqlstatement);
            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt("folderID");
            }
            return -2;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return -1;
        }
    }
}
