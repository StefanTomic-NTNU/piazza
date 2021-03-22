package tdt4145.ui;

import java.util.Scanner;

public class MainMenu {

    public static int menu() {
        int selection = -1;
        Scanner input = new Scanner(System.in);

        /***************************************************/

        System.out.println("Select option");
        System.out.println("-------------------------");
        System.out.println("1 - List posts");
        System.out.println("2 - Create new post");
        System.out.println("5 - Quit");

        System.out.print("Enter number: ");
        selection = input.nextInt();
        System.out.println("");
        return selection;
    }

}
