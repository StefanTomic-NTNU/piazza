package tdt4145.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainMenu {

    public static int menu() {
        int selection = -1;
        Scanner input = new Scanner(System.in);

        /***************************************************/

        System.out.println("Select option");
        System.out.println("-------------------------");
        System.out.println("1 - List posts");
        System.out.println("2 - Open post");
        System.out.println("3 - Create new post");
        System.out.println("5 - Quit");

        System.out.print("Enter number: ");
        selection = input.nextInt();
        System.out.println("");
        return selection;
    }


    public static List<Object> getPosts() {
        List<Object> posts = new ArrayList<>();

        //TODO: Add posts from db to 'List posts'.
        // Or fetch it from somewhere else.


        return posts;
    }

    public static void printPosts(List<Object> posts){
        int counter = 0;

        for (Object post : posts){
            System.out.println(counter + " - " + post); //TODO: check format
            counter ++;
        }
    }

    public static void createPost() {
        try{
            //TODO: Add functionality for adding new post to db
            System.out.println("Entry successfully added.");
        }catch (Exception e){
            System.out.println("And error occured : " + e.getMessage());
        }
    }
}
