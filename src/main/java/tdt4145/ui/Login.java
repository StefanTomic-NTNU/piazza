package tdt4145.ui;

import tdt4145.core.User;
import tdt4145.core.UserDAO;

import java.sql.SQLException;
import java.util.Scanner;

/**
 * Handles login attempts by taking input and checking them against the database.
 */
public class Login {

    /**
     * Prompts the user to login to an account using the console.
     *
     * @return True if login was successful, otherwise False.
     */
    public static int prompt() {
        String email;
        String password;
        Scanner emailInput = new Scanner(System.in);
        Scanner passwordInput = new Scanner(System.in);

        System.out.print("Enter email: ");
        email = emailInput.nextLine();

        System.out.print("Enter password: ");
        password = passwordInput.nextLine();
        System.out.println();

        try {
            UserDAO dao = new UserDAO();
            int userID = dao.isPasswordCorrect(email, password.toCharArray());
            if (userID >= 0) {
                return userID;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        System.out.println("Email or password is incorrect");
        return -1;
    }

}
