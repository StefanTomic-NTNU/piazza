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

    public static boolean create() {

        String title = "";
        String text = "";
        List<String> textList = new ArrayList<>();
        String folder = "";
        Boolean anonymous = false;
        Scanner titleInput = new Scanner(System.in);
        Scanner textInput = new Scanner(System.in);
        Scanner folderInput = new Scanner(System.in);
        Scanner anonymousInput = new Scanner(System.in);


        /***************************************************/

        while (true) {
            System.out.print("Enter folder name: ");
            folder = anonymousInput.nextLine();
            /**
            try {
                FolderDAO folderDao = new FolderDAO();
                // if (folderDao.) break;
            } catch (SQLException sqlException) {
                System.out.println("Folder not found..");
                sqlException.printStackTrace();
            } */
            break;
        }


        while (true) {
            System.out.print("Enter title: ");
            title = titleInput.nextLine();
            if (title.length() < 3) {
                System.out.println("Title too short");
            } else {
                break;
            }
        }


        System.out.print(" -- POST CREATION --");
        System.out.println("Lines will keep being added until you write only 'post_finished'");
        System.out.println("You can undo previously added line by writing only 'undo_line'");
        while (true) {
            text = textInput.nextLine();
            if (text.equals("post_finished")) {
                break;
            } else if (text.equals("undo_line") && !textList.isEmpty()) {
                textList.remove(textList.size() - 1);
            } else {
                textList.add(text);
            }
        }
        text = String.join(", ", textList);

        System.out.println(" ");

        try {
            ThreadDAO dao = new ThreadDAO();
            // dao.CreateThread()
        } catch (SQLException sqlException) {
            System.out.println("User creation failed..");
            sqlException.printStackTrace();
        }
        return true;
    }


}
