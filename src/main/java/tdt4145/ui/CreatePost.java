package tdt4145.ui;

import tdt4145.core.FolderDAO;
import tdt4145.core.ThreadDAO;
import tdt4145.core.UserDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * Creates Posts and corresponding Thread
 */

public class CreatePost {

    public static boolean create(int loggedInUserID) {

        String title = "";
        String text = "";
        List<String> textList = new ArrayList<>();
        String folder = "";
        int folderID;
        Boolean allowAnonymous = false;
        Boolean anonymous = false;
        Scanner titleInput = new Scanner(System.in);
        Scanner textInput = new Scanner(System.in);
        Scanner folderInput = new Scanner(System.in);
        Scanner anonymousInput = new Scanner(System.in);


        /***************************************************/

        System.out.println("Please specify the name of folder the post belongs to: ");

        while (true) {

            try {
                FolderDAO folderDao = new FolderDAO();
                folder = folderInput.nextLine();
                folderID = folderDao.getFolderID(folder);
                if (folderID > 0) break;
                System.out.println("Folder not found. Try again.");
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
                System.out.println("Folder not found. Try again.");
            }

        }

        while (true) {
            System.out.print("Enter post title: ");
            title = titleInput.nextLine();
            if (title.length() < 3) {
                System.out.println("Title too short");
            } else {
                break;
            }
        }


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
                System.out.println(String.join("\n", textList));
            } else {
                textList.add(text);
            }
        }
        text = String.join("\n", textList);

        System.out.println(" ");

        try {
            ThreadDAO dao = new ThreadDAO();
            int threadID = dao.CreateThread(text, loggedInUserID, false);
            dao.CreatePost(title, 0, folderID, threadID);
        } catch (SQLException sqlException) {
            System.out.println("Post creation failed..");
            sqlException.printStackTrace();
        }
        return true;
    }


}
