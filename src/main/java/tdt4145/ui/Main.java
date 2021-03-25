package tdt4145.ui;

import tdt4145.core.repo.Database;
import tdt4145.core.model.User;

import java.util.Scanner;

/**
 * Main method for running the tDT4145-piazza project
 */
public class Main {

    /**
     * Exits the program
     */
    public static void exit() {
        System.out.println("Goodbye");
        System.exit(0);
    }

    public static void main(String[] args) {

        MainMenu mainMenu = new MainMenu();
        PostMenu postMenu = new PostMenu();

        Database.setNewDatabase("stefandt_gr148", "beste_grp");

        {
            System.out.println("  _____  ____   _____  _  _    _  _  _    ____          ____                       ");
            System.out.println(" |_   _||  _ \\ |_   _|| || |  / || || |  | ___|        |  _ \\(_) __ _ __________ _ ");
            System.out.println("   | |  | | | |  | |  | || |_ | || || |_ |___ \\   ___  | |_) | |/ _` |_  /_  / _` |");
            System.out.println("   | |  | |_| |  | |  |__   _|| ||__   _| ___) | |___| |  __/| | (_| |/ / / / (_| |");
            System.out.println("   |_|  |____/   |_|     |_|  |_|   |_|  |____/        |_|   |_|\\__,_/___/___\\__,_|");
            System.out.println("---------------------------------------------------------------------------------------");
            System.out.println("Disclaimer: this a demo-product showcasing a number of selected database functionalities.");
            System.out.println("Consequently you might find unimplemented functionalities when using this product.");
            System.out.print("Do you accept this? (y/n): ");
            String userDisclaimer = new Scanner(System.in).nextLine();
            if (userDisclaimer.equalsIgnoreCase("y")) {
                System.out.println("Thank you, Enjoy.");
            } else {
                exit();
            }
        }

        // Login and user creation
        User loggedInUser = null;
        while (loggedInUser == null) {
            switch (mainMenu.welcomeMenu()) {
                case 1 -> loggedInUser = UserHandler.login();
                case 2 -> UserHandler.create();
                case 5 -> exit();
            }
        }

        // main program loop
        while (loggedInUser != null) {

            System.out.println();
            System.out.println("Select option");
            System.out.println("-------------------------");
            System.out.println("1 - List all posts");
            System.out.println("2 - Search for posts");
            System.out.println("3 - Open post");
            System.out.println("4 - Create new post");
            System.out.println("5 - Quit");
            if (loggedInUser.hasInstructor_privileges()) {
                System.out.println("6 - View statistics");
            }
            System.out.print("Enter number: ");

            // Main menu selection
            switch (mainMenu.menu()) {
                case 1 -> mainMenu.printPosts();
                case 2 -> mainMenu.searchPosts();
                case 3 -> {

                    System.out.println("Select post to open");
                    System.out.print("Enter number: ");
                    int selection = new Scanner(System.in).nextInt();

                    // Nested switch statement for postMenu.
                    boolean postMenuStatus = true;
                    while (postMenuStatus) {
                        switch (postMenu.menu()) {
                            case 1 -> postMenu.getInfo(selection);
                            case 2 -> postMenu.comment(selection, loggedInUser.getUserID());
                            case 3 -> postMenu.likePost(selection);
                            case 4 -> postMenuStatus = false;
                            case 5 -> exit();
                        }
                    }
                }
                case 4 -> mainMenu.createPost(loggedInUser.getUserID());
                case 5 -> exit();
                case 6 -> {
                    if (loggedInUser.hasInstructor_privileges()) {
                        mainMenu.viewStatistics();
                    }
                }
            }
        }
    }
}
