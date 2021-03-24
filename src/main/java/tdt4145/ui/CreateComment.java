package tdt4145.ui;


import tdt4145.core.ThreadDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Creates Comment and corresponding Thread.
 * Content of Comment is input by user.
 */
public class CreateComment {

    /**
     *
     * @param parentID ID of the thread the comment is replying to
     * @param loggedInUserID The ID of the user submitting the comment
     * @return true if Comment was successfully submitted. Otherwise false
     */
    public static boolean create(int parentID, int loggedInUserID) {
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
        } catch (SQLException sqlException) {
            System.out.println("Comment creation failed..");
            sqlException.printStackTrace();
            return false;
        }
        return true;
    }
}
