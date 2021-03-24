package tdt4145.ui;


import tdt4145.core.ThreadDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Creates Comment and corresponding Thread
 */
public class CreateComment {

    public static boolean create(int loggedInUserID) {
        String text = "";
        List<String> textList = new ArrayList<>();
        int parentID = -1;
        Boolean allowAnonymous = false;
        Boolean anonymous = false;
        Scanner textInput = new Scanner(System.in);
        Scanner parentIDInput = new Scanner(System.in);
        Scanner anonymousInput = new Scanner(System.in);

        while (parentID < 0) {
            System.out.print("Enter parent thread id: ");
            try {
                parentID = Integer.parseInt(parentIDInput.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Input must be an int");
                break;
            }
        }

        System.out.print(" -- COMMENT CREATION --");
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
            System.out.println(threadID);
            System.out.println(loggedInUserID);
            dao.CreateComment(threadID, parentID);
        } catch (SQLException sqlException) {
            System.out.println("Comment creation failed..");
            sqlException.printStackTrace();
        }

        return true;
    }
}
