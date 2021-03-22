package tdt4145.ui;

import tdt4145.core.UserDAO;

import java.sql.SQLException;
import java.util.Scanner;

public class CreateUser {

    public static boolean create() {
        try {
            UserDAO dao = new UserDAO();
            //dao.addUser();
        } catch (SQLException sqlException) {

            sqlException.printStackTrace();
        }
        // Return true if successful
        return true;
    }

}
