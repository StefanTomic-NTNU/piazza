package tdt4145.ui;

import tdt4145.core.FolderDAO;
import tdt4145.core.Tag;
import tdt4145.core.ThreadDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Creates Posts and Comments and a corresponding Thread.
 */
public class ThreadHandler {

    /**
     * Creates a post
     *
     * @param loggedInUserID current logged in user
     * @return true if the operation is successful
     */
    public static boolean post(int loggedInUserID) {
        String title = "";
        String text = "";
        List<String> textList = new ArrayList<>();
        String folder = "";
        int folderID;
        String tag = "";
        Integer tagID;
        List<Integer> tagList = new ArrayList<>();
        Boolean allowAnonymous = false;
        Boolean anonymous = false;
        Scanner titleInput = new Scanner(System.in);
        Scanner textInput = new Scanner(System.in);
        Scanner folderInput = new Scanner(System.in);
        Scanner anonymousInput = new Scanner(System.in);
        Scanner tagInput = new Scanner(System.in);

        System.out.println("Please specify the name of folder the post belongs to: ");

        // Write foldername
        while (true) {
            try {
                FolderDAO folderDao = new FolderDAO();
                folder = folderInput.nextLine();
                folderID = folderDao.getFolderID(folder);
                if (folderID > 0) break;
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
            System.out.println("Folder not found. Try again.");
        }

        // Write post title
        while (true) {
            System.out.print("Enter post title: ");
            title = titleInput.nextLine();
            if (title.length() < 3) {
                System.out.println("Title too short");
            } else {
                break;
            }
        }

        // Write post content
        System.out.println(" -- POST CREATION --");
        System.out.println("Lines will keep being added until you write only ':wq'");
        System.out.println("You can undo previously added line by writing only 'undo_line'");
        System.out.println("To view post content write only 'print'");

        while (true) {
            text = textInput.nextLine();
            if (text.equals(":wq")) {
                break;
            } else if (text.equals("undo_line") && !textList.isEmpty()) {
                textList.remove(textList.size() - 1);
            } else if (text.equals("print")) {
                System.out.println(title);
                System.out.println();
                System.out.println(String.join("\n", textList));
            } else {
                textList.add(text);
            }
        }

        text = String.join("\n", textList);
        System.out.println(" ");

        // List available tags
        try {
            ThreadDAO threadDAO1 = new ThreadDAO();
            ArrayList<Tag> tags = threadDAO1.getTags();
            System.out.println("Available tags: ");
            System.out.println();
            for (Tag retrievedTag : tags) {
                System.out.println(retrievedTag.getLabel());
            }
        } catch (SQLException sq) {
            sq.printStackTrace();
        } // Add tags from list

        System.out.println();
        System.out.println("Please enter the names of the tags you wish to add");
        System.out.println("When you are happy with the tags, write ':wq' to finish post creation");

        while (true) {
            try {
                ThreadDAO threadDAO2 = new ThreadDAO();
                tag = tagInput.nextLine();
                if (tag.equals(":wq")) break;
                tagID = threadDAO2.getTagID(tag);
                if (tagID > 0) {
                    tagList.add(tagID);
                    System.out.println("Tag " + tag + " added.");
                } else {
                    System.out.println("Tag not found. Try again.");
                }
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }

        // Adds post to db
        try {
            ThreadDAO dao = new ThreadDAO();
            int threadID = dao.CreateThread(text, loggedInUserID, false);
            dao.CreatePost(title, 0, folderID, threadID);
            for (Integer chosenTag : tagList) {
                dao.linkPostTags(threadID, (chosenTag));
            }
            System.out.println("Post " + title + " successfully created!");
            return true;

        } catch (SQLException sqlException) {
            System.out.println("Post creation failed..");
            sqlException.printStackTrace();
            return false;
        }
    }

    /**
     * Creates a comment on a post
     *
     * @param parentID       id of the post
     * @param loggedInUserID current logged in user
     * @return true if the operation is successful
     */
    public static boolean comment(int parentID, int loggedInUserID) {
        String text;
        List<String> textList = new ArrayList<>();
        Scanner textInput = new Scanner(System.in);

        System.out.println(" -- COMMENT CREATION --");
        System.out.println();
        System.out.println("Lines will keep being added until you enter ':wq' by itself");
        System.out.println("You can undo previously added line by writing only 'undo_line'");

        while (true) {
            text = textInput.nextLine();
            if (text.equals(":wq")) {
                break;
            } else if (text.equals("undo_line") && !textList.isEmpty()) {
                textList.remove(textList.size() - 1);
            } else {
                textList.add(text);
            }
        }

        text = String.join("\n", textList);
        System.out.println(" ");

        try {
            ThreadDAO dao = new ThreadDAO();
            int threadID = dao.CreateThread(text, loggedInUserID, false);
            dao.CreateComment(threadID, parentID);
            return true;
        } catch (SQLException sqlException) {
            System.out.println("Comment creation failed..");
            sqlException.printStackTrace();
            return false;
        }
    }
}
