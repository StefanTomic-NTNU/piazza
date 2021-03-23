package tdt4145.ui;

import tdt4145.core.Database;

import java.util.List;

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

        Database.setNewDatabase(null, null);
        // MainMenu.initCourse();

        System.out.println("  _____  ____   _____  _  _    _  _  _    ____          ____                       ");
        System.out.println(" |_   _||  _ \\ |_   _|| || |  / || || |  | ___|        |  _ \\(_) __ _ __________ _ ");
        System.out.println("   | |  | | | |  | |  | || |_ | || || |_ |___ \\   ___  | |_) | |/ _` |_  /_  / _` |");
        System.out.println("   | |  | |_| |  | |  |__   _|| ||__   _| ___) | |___| |  __/| | (_| |/ / / / (_| |");
        System.out.println("   |_|  |____/   |_|     |_|  |_|   |_|  |____/        |_|   |_|\\__,_/___/___\\__,_|");


        // Login and user creation
        boolean loginStatus = false;
        while (!loginStatus) {
            switch (Welcome.menu()) {
                case 1 -> loginStatus = Login.prompt();
                case 2 -> CreateUser.create();
                case 5 -> exit();
            }

        }

        // Main menu selection
        while (true) {
            List<Object> posts = MainMenu.getPosts();
            switch (MainMenu.menu()) {
                case 1 -> MainMenu.printPosts(posts);
                case 2 -> PostMenu.openPostPrompt(posts); //opens a new menu
                case 3 -> MainMenu.createPost();
                case 5 -> exit();
            }
            int userChoice = MainMenu.menu();
            System.out.println(userChoice);
        }
    }
}
