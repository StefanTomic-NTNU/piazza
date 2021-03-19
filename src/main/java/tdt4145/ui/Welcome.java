package tdt4145.ui;

import java.util.Scanner;

public class Welcome {

    public static int menu() {
        int selection = -1;
        Scanner input = new Scanner(System.in);

        /***************************************************/
        System.out.println("");


        System.out.println("Select option");
        System.out.println("-------------------------");
        System.out.println("1 - Login existing user");
        System.out.println("2 - Create new user");
        //System.out.println("3 - ");
        System.out.println("5 - Quit");

        System.out.print("Enter option: ");
        selection = input.nextInt();
        System.out.println("");
        return selection;
    }

}
