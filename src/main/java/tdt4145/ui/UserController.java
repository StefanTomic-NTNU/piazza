package tdt4145.ui;

import tdt4145.core.UserDAO;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Creates an account based on the users input in the console.
 */
public class UserController {

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
                System.out.println("Username too short. Please try again.");
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
                System.out.println("Password too short. Please try again.");
            } else {
                break;
            }
        }

        System.out.println();

        // Adds data to database
        try {
            UserDAO dao = new UserDAO();
            int returnvalue = dao.addUser(email, username, password.toCharArray());
            if (returnvalue == -1) {
                System.out.println("User creation failed..");
                System.out.println("please try again");
            } else if (returnvalue == -2) {
                System.out.println("email already registered please login or use another email");
            } else {
                System.out.println("User '" + username + "' successfully created!");
                return true;
            }
            return false;
        } catch (SQLException sqlException) {
            System.out.println("User creation failed..");
            sqlException.printStackTrace();
        }
        return false;
    }

    /**
     * Prompts the user to login to an account using the console.
     *
     * @return True if login was successful, otherwise False.
     */
    public static int login() {
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
