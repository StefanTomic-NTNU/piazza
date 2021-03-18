package tdt4145;

import java.util.Scanner;

public class Main {

    public static int welcome() {
        int selection = -1;
        Scanner input = new Scanner(System.in);

        /***************************************************/
        System.out.println("Welcome to Piazza cli");
        System.out.println("");


        System.out.println("Select option");
        System.out.println("-------------------------");
        System.out.println("1 - Login existing user");
        System.out.println("2 - Create new user");
        System.out.println("3 - ");
        System.out.println("5 - Quit");

        System.out.print("Enter option: ");
        selection = input.nextInt();
        System.out.println("");
        return selection;
    }

    public static int mainMenu() {
        int selection = -1;
        Scanner input = new Scanner(System.in);

        /***************************************************/

        System.out.println("Select option");
        System.out.println("-------------------------");
        System.out.println("1 - List posts");
        System.out.println("2 - Create new post");
        System.out.println("5 - Quit");

        System.out.print("Enter number: ");
        selection = input.nextInt();
        System.out.println("");
        return selection;
    }

    public static boolean login() {
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



    public static void main(String[] args) {

        /** Login and user creation */
        Boolean loginStatus = false;
        while (!loginStatus){
            switch (welcome()) {
                case 1 -> loginStatus = login();
                //case 2 -> createUser();
                case 5 -> System.exit(0);
            }
        }

        /** Main menu selection*/
        while (true){
            switch (mainMenu()){
                //case 1 -> listPosts();
                //case 2 -> createNewPost();
                case 5 -> System.exit(0);
            }
            int userChoice = mainMenu();
            System.out.println(userChoice);
        }


        /*********************************************************/



    }

}
