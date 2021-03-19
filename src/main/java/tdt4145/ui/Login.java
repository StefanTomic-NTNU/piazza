package tdt4145.ui;

import java.util.Scanner;

public class Login {

    public static boolean prompt() {
        String username;
        String password;
        Scanner usernameInput = new Scanner(System.in);
        Scanner passwordInput = new Scanner(System.in);


        /***************************************************/

        System.out.print("Enter username: ");
        username = usernameInput.nextLine();

        System.out.print("Enter password: ");
        password = passwordInput.nextLine();
        System.out.println("");

        //TODO: check login
        System.out.println("Welcome " + username);

        return true;
    }

}
