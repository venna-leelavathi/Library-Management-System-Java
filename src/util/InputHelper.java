package util;

import java.util.Scanner;

// Small helper so we don't repeat Scanner code everywhere
public class InputHelper {
    private static Scanner sc = new Scanner(System.in);

    public static String readLine(String message) {
        System.out.print(message);
        return sc.nextLine().trim();
    }

    public static int readInt(String message) {
        while (true) {
            System.out.print(message);
            String text = sc.nextLine().trim();
            try {
                return Integer.parseInt(text);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
}
