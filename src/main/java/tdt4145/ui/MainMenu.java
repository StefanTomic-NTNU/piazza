package tdt4145.ui;

import tdt4145.core.ThreadDAO;
import tdt4145.core.UserDAO;
import tdt4145.core.UserOverview;

import java.sql.SQLException;
import java.util.ArrayList;
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

        /*System.out.println();
        System.out.println("Select option");
        System.out.println("-------------------------");
        System.out.println("1 - List all posts");
        System.out.println("2 - Search for posts");
        System.out.println("3 - Open post");
        System.out.println("4 - Create new thread");
        System.out.println("5 - Quit");
        System.out.print("Enter number: ");*/

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
     * Prints a list of the posts that matches the search.
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

    /**
     * Prints per user post statistics to console
     * the columns consist of:
     * USERNAME, NR POSTS VIEWED, NR POSTS WRITTEN
     */
    public void viewStatistics() {
        try {
            UserDAO userDAO = new UserDAO();
            ArrayList<UserOverview> statistics = userDAO.overviewStatistics();
            int length = statistics.size();
            Object[][] table = new String[length+1][];
            table[0] = new String[] {"USERNAME", "NR. VIEWED", "NR. WRITTEN"};
            for (int i=0; i < length; i++) {
                UserOverview ow = statistics.get(i);
                table[i+1] = new String[] {ow.getName(),
                        Integer.valueOf(ow.getNbreadposts()).toString(),
                        Integer.valueOf(ow.getNbpost()).toString()};
            }
            System.out.println("-------- PER USER POST STATISTICS --------");
            for (Object[] row : table) {
                System.out.format("%-15s%-15s%-15s%n", row);
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        }
    }
}
