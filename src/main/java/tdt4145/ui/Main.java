package tdt4145.ui;

import tdt4145.core.Database;

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

        Database.setNewDatabase("admin", "mypass");
        // MainMenu.initCourse();

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
            if (userDisclaimer.toLowerCase().equals("y")) {
                System.out.println("Thank you, Enjoy.");
            } else {
                exit();
            }
        }

        // Login and user creation
        int loggedInUserID = -1;
        while (loggedInUserID < 0) {
            switch (mainMenu.welcomeMenu()) {
                case 1 -> loggedInUserID = UserController.login();
                case 2 -> UserController.create();
                case 5 -> exit();
            }
        }

        // int loggedInUserID = 1;
        // main program loop
        while (loggedInUserID >= 0) {

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
                            case 2 -> postMenu.comment(selection, loggedInUserID);
                            case 3 -> postMenu.likePost(selection);
                            case 4 -> postMenuStatus = false;
                            case 5 -> exit();
                        }
                    }
                }
                case 4 -> mainMenu.createPost(loggedInUserID);
                case 5 -> exit();
            }
        }
    }
}
