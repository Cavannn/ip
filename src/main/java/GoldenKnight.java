import java.util.Scanner;

public class GoldenKnight {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";

        String Lvl0 = "_______________________________________\n"
                + "Hello! I'm the Golden Knight! \n"
                + "What can I do for you? \n"
                + "_______________________________________\n"
                + " Bye. Hope to see you again soon! \n"
                + "_______________________________________\n";

        String greeting = "_______________________________________\n"
                + "Hello! I'm the Golden Knight! \n"
                + "What can I do for you? \n"
                + "_______________________________________\n";

        String goodbye = "_______________________________________\n"
                + " Bye. Hope to see you again soon! \n"
                + "_______________________________________\n";

        System.out.print(greeting);

        while (true) {

            String input = scanner.nextLine();

            // Exit
            if (input.equals("bye")) {
                System.out.println(goodbye);
                break;
            }

            // Echo
            System.out.println("_______________________________________");
            System.out.println(" " + input);
            System.out.println("_______________________________________");
        }

        scanner.close();

    }

}
