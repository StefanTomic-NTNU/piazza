package tdt4145.ui;

import tdt4145.core.Database;
import tdt4145.core.UserDAO;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void exit() {

        System.out.println("Goodbye");
        System.exit(0);

    }

    public static void main(String[] args){
        System.out.println("Welcome to Piazza cli");
        Database.setNewDatabase(null, null);


        /** Login and user creation */
        Boolean loginStatus = false;

        while (!loginStatus){
            switch (Welcome.menu()) {
                case 1 -> loginStatus = Login.prompt();
                case 2 -> CreateUser.create();
                case 5 -> exit();
            }


        /** Main menu selection*/

        while (true){
            switch (MainMenu.menu()){
                //case 1 -> listPosts();
                //case 2 -> createNewPost();
                case 5 -> exit();
            }
            int userChoice = MainMenu.menu();
            System.out.println(userChoice);
        }


        /*********************************************************/



    }

}
