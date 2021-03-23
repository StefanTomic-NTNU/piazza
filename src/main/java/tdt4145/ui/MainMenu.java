package tdt4145.ui;

import tdt4145.core.CourseDAO;
import tdt4145.core.ThreadDAO;

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

        System.out.println();
        System.out.println("Select option");
        System.out.println("-------------------------");
        System.out.println("1 - List all posts");
        System.out.println("2 - Search for posts");
        System.out.println("3 - Open post");
        System.out.println("4 - Create new post");
        System.out.println("5 - Quit");
        System.out.print("Enter number: ");

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
        System.out.println("Functionality for creating posts not yet implemented");
        CreatePost.create(loggedInUserID);
        /*try {
            //TODO: Add functionality for adding new post to db
            System.out.println("Entry successfully added.");
        } catch (Exception e) {
            System.out.println("And error occured : " + e.getMessage());
        }*/
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
            //dao.search(search);
            //print result
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
