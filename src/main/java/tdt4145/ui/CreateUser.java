package tdt4145.ui;

import tdt4145.core.UserDAO;

import java.sql.SQLException;
import java.util.Scanner;

public class CreateUser {

    public static boolean create() {

        String username;
        String email;
        String password;
        Scanner usernameInput = new Scanner(System.in);
        Scanner emailInput = new Scanner(System.in);
        Scanner passwordInput = new Scanner(System.in);


        /***************************************************/

        System.out.print("Enter username: ");
        username = usernameInput.nextLine();

        System.out.print("Enter email: ");
        email = emailInput.nextLine();

        System.out.print("Enter password: ");
        password = passwordInput.nextLine();
        System.out.println("");

        try {
            UserDAO dao = new UserDAO();
            dao.addUser(email, username, password.toCharArray());
            System.out.println("User " + username + " created!");
            return true;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }

        //TODO: check login
        // input variables:
        // username
        // password
        // if login is correct, return true to continue to mainMenu.
        // return false to retry.

        // System.out.println("Welcome " + username);
        // System.out.println("Username or password is incorrect ");
    }

}
