package tdt4145.ui;

import tdt4145.core.UserDAO;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Creates an account based on the users input in the console.
 */
public class CreateUser {

    private final static String emailRegex =
            "^[a-zA-Z0-9_+&*-]+(?:\\." +
                    "[a-zA-Z0-9_+&*-]+)*@" +
                    "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                    "A-Z]{2,7}$";

    /**
     * Prompts the user to create an account using the console.
     *
     * @return True if creation was successful, otherwise False.
     */
    public static boolean create() {

        String username;
        String email;
        String password;
        Scanner usernameInput = new Scanner(System.in);
        Scanner emailInput = new Scanner(System.in);
        Scanner passwordInput = new Scanner(System.in);

        // Input username
        while (true) {
            System.out.print("Enter username: ");
            username = usernameInput.nextLine();
            if (username.length() < 5) {
                System.out.println("Username too short");
            } else {
                break;
            }
        }

        // Input email
        Pattern emailPat = Pattern.compile(emailRegex);
        while (true) {
            System.out.print("Enter email: ");
            email = emailInput.nextLine();
            if (!emailPat.matcher(email).matches()) {
                System.out.println("Email invalid");
            } else {
                break;
            }
        }

        // Input password
        while (true) {
            System.out.print("Enter password: ");
            password = passwordInput.nextLine();
            if (password.length() < 5) {
                System.out.println("Username too short");
            } else {
                break;
            }
        }

        System.out.println();

        // Adds data to database
        try {
            UserDAO dao = new UserDAO();
            dao.addUser(email, username, password.toCharArray());
            System.out.println("User " + username + " successfully created!");
            return true;
        } catch (SQLException sqlException) {
            System.out.println("User creation failed..");
            sqlException.printStackTrace();
        }
        return false;
    }

}
