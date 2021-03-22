package tdt4145.ui;

import tdt4145.core.UserDAO;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateUser {

    private final static String emailRegex =
            "^[a-zA-Z0-9_+&*-]+(?:\\."+
            "[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
            "A-Z]{2,7}$";

    public static boolean create() {

        String username = "";
        String email = "";
        String password = "";
        Scanner usernameInput = new Scanner(System.in);
        Scanner emailInput = new Scanner(System.in);
        Scanner passwordInput = new Scanner(System.in);


        /***************************************************/

        while (true) {
            System.out.print("Enter username: ");
            username = usernameInput.nextLine();
            if (username.length() < 5) {
                System.out.println("Username too short");
            } else {
                break;
            }
        }

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

        while (true) {
            System.out.print("Enter password: ");
            password = passwordInput.nextLine();
            if (password.length() < 5) {
                System.out.println("Username too short");
            } else {
                break;
            }
        }

        System.out.println("");

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
