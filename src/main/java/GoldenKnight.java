import java.util.Scanner;
import java.util.ArrayList;

public class GoldenKnight {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> items = new ArrayList<>();

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

        String line = "_______________________________________\n";

        System.out.print(greeting);

        while (true) {
            String input = scanner.nextLine();

            // Exit
            if (input.equals("bye")) {
                System.out.println(goodbye);
                break;
            } else if (input.equals("list")) {
                System.out.println(line);
                for (int i = 0; i < items.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + items.get(i));
                }
                System.out.println(line);
            } else {
                items.add(input);
                System.out.println(line);
                System.out.println(" added: " + input);
                System.out.println(line);
            }
        }

        scanner.close();

    }
}
