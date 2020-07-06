package graphgame.utilities;

import java.util.Scanner;

public class MyInput {

    private static Scanner scanner = new Scanner(System.in);

    public static String takeInput() {
        return scanner.nextLine();
    }
}
