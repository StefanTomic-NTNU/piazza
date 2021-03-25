package tdt4145.ui;

import tdt4145.core.ThreadDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Handles inputs and outputs messages in regards to the main menu
 */
public class MainMenu {

    public int welcomeMenu() {
        int selection;
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("Select option");
        System.out.println("-------------------------");
        System.out.println("1 - Login existing user");
        System.out.println("2 - Create new user");
        System.out.println("5 - Quit");
        System.out.print("Enter option: ");

        selection = input.nextInt();
        System.out.println();

        return selection;
    }

    /**
     * Prints info about a post
     */
    public void printPosts() {
        System.out.println("Printing posts is not yet implemented");
    }

    /**
     * Provides a menu selection the main menu.
     */
    public int menu() {
        int selection;
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("Select option");
        System.out.println("-------------------------");
        System.out.println("1 - List all posts");
        System.out.println("2 - Search for posts");
        System.out.println("3 - Open post");
        System.out.println("4 - Create new thread");
        System.out.println("5 - Quit");
        System.out.print("Enter number: ");

        selection = input.nextInt();
        System.out.println();

        return selection;
    }

    /**
     * Creates a new post and adds it to the database.
     */
    public void createPost(int loggedInUserID) {
        ThreadController.post(loggedInUserID);
    }

    /**
     * Provides searching for a post by keyword.
     *
     * @return a list of the posts that matches the search.
     */
    public void searchPosts() {
        System.out.print("Search: ");
        String search = new Scanner(System.in).nextLine();

        try {
            ThreadDAO dao = new ThreadDAO();
            List<Integer> result = dao.searchByKeyword(search);
            for (Integer integer : result) {
                System.out.println(integer);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
