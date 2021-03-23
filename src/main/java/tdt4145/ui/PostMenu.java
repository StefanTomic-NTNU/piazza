package tdt4145.ui;

import java.util.List;


/**
 * Provides menu and information about posts and
 * handles inputs and outputs related to specific posts.
 */
public class PostMenu {

    /**
     * Prompts the user to select a specific post to view.
     *
     * @param posts to be selected from.
     */
    public static void openPostPrompt(List<Object> posts) {
        int selection;
        //TODO: Integer input to select specific post
    }

    /**
     * Provides a menu selection for the post selected in {openPostPrompt}.
     */
    public static void menu() {
        //TODO: create menu in while loop
        // 1. view info, comments, replies and likes
        // 2. like post
        // 3. go back
    }

    /**
     * Prints the information about a given post
     *
     * @param selection the post to be selected.
     * @param posts     to be selected from.
     */
    public static void getPostInfo(List<Object> posts, int selection) {
        //TODO: get post title, comments, and nr. of likes
    }


    /**
     * Adds a like to the database from the user to a given post
     */
    public static void likePost(List<Object> posts, int selection) {
        //TODO: add functionality for liking posts.
    }
}
