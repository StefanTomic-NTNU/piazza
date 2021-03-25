package tdt4145.ui;

import java.util.Scanner;

/**
 * Provides menu and information about posts and
 * handles inputs and outputs related to specific posts.
 */
public class PostMenu {

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
     * Creates a comment on the selected post.
     *
     * @param selection      the post to comment on
     * @param loggedInUserID is the currently logged in user.
     */
    public void comment(int selection, int loggedInUserID) {
        ThreadHandler.comment(selection, loggedInUserID);
    }

    /**
     * Prints the information about a given post
     *
     * @param selection the post to be selected.
     */
    public void getInfo(int selection) {
        System.out.println("Printing of a Post's info is not yet implemented");
        //TODO
    }

    /**
     * Adds a like to the database from the user to a given post
     */
    public void likePost(int selection) {
        System.out.println("Liking a post is not yet implemented");
        //TODO
    }
}
