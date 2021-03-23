package tdt4145.ui;

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
    public static boolean prompt() {
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
            if (dao.isPasswordCorrect(email, password.toCharArray())) {
                System.out.println("Welcome " + email);
                return true;
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return false;
        }

        System.out.println("Email or password is incorrect");

        return false;
    }

}
