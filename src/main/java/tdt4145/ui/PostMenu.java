package tdt4145.ui;

import tdt4145.core.ThreadDAO;

import java.sql.SQLException;
import java.util.Scanner;


/**
 * Provides menu and information about posts and
 * handles inputs and outputs related to specific posts.
 */
public class PostMenu implements Menu {

    /**
     * Provides a menu selection for the post selected in {openPostPrompt}.
     */
    public int menu() {

        int selection;
        Scanner input = new Scanner(System.in);

        System.out.println();
        System.out.println("Select option");
        System.out.println("-------------------------");
        System.out.println("1 - View info");
        System.out.println("2 - Comment on post");
        System.out.println("3 - Like post");
        System.out.println("4 - Go back");
        System.out.println("5 - Quit");
        System.out.print("Enter number: ");

        selection = input.nextInt();
        System.out.println();

        return selection;
    }

    /**
     * Prints the information about a given post
     *
     * @param selection the post to be selected.
     */
    public void getInfo(int selection) {
        System.out.println("Printing of a Post's info is not yet implemented");
        //TODO: get post title, comments, folder, and nr. of likes
    }


    public void comment(int selection) {
        System.out.println("Commenting on a Post is not yet implemented");
        //TODO: implement commenting on posts


        String text;
        //TODO: get info from current Post and User
        String coursename;
        int userID;
        boolean anonymous;
        Scanner textInput = new Scanner(System.in);

        int threadID;
        int postID = selection;

        // Input Text
        System.out.print("Enter comment: ");
        text = textInput.nextLine();


        try {
            ThreadDAO dao = new ThreadDAO();
            //dao.CreateThread(text, coursename, userID, anonymous);
            //dao.CreateComment(threadID, postID);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }


    /**
     * Adds a like to the database from the user to a given post
     */
    public void likePost(int selection) {
        System.out.println("Liking a post is not yet implemented");
        //TODO: add functionality for liking posts.
    }
}
