package tdt4145.ui;

import tdt4145.core.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handles inputs and outputs messages in regards to the main menu
 */
public class MainMenu implements Menu {

    /**
     * @param posts to be printed to the console.
     */
    public void printPosts(List<Object> posts) {
        System.out.println("Printing posts is not yet implemented");
        int counter = 0;

        for (Object post : posts) {
            System.out.println(counter + " - " + post); //TODO: check format
            counter++;
        }
    }


    public static void initCourse() {
        try {
            CourseDAO dao = new CourseDAO();
            dao.createActiveCourse(4145, "vaar", 2021, true);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public int menu() {
        int selection;
        Scanner input = new Scanner(System.in);
        selection = input.nextInt();
        System.out.println();

        return selection;
    }

    /**
     * @return A list of all the relevant posts in the selected course.
     */
    public List<Object> getPosts() {
        List<Object> posts = new ArrayList<>();

        //TODO: Add posts from db to 'List posts'.
        // Or fetch it from somewhere else.


        return posts;
    }

    /**
     * Creates a new post and adds it to the database.
     */
    public void createPost(int loggedInUserID) {
        CreatePost.create(loggedInUserID);
    }


    /**
     * @param posts to be searched
     * @return a list of the posts that matches the search.
     */
    public void searchPosts(List<Object> posts) {
        System.out.println("Searching for posts is not yet implemented");

        //TODO: implement searching

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
