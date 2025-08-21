import java.util.Scanner;
import java.util.ArrayList;

public class GoldenKnight {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> items = new ArrayList<>();

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
            String[] parts = input.split(" ", 2);
            String command = parts[0];

            if (command.equals("bye")) {
                System.out.println(goodbye);
                break;
            } else if (command.equals("list")) {
                System.out.println(line);
                System.out.println(" Here are the tasks in your list:");
                for (int i = 0; i < items.size(); i++) {
                    System.out.println(" " + (i + 1) + ". " + items.get(i));
                }
                System.out.println(line);
            } else if (command.equals("mark")) {
                int taskNo = Integer.parseInt(parts[1]) - 1;
                Task task = items.get(taskNo);
                task.markAsDone();
                System.out.println(line);
                System.out.println(" Nice! I've marked this task as done:");
                System.out.println(task);
                System.out.println(line);
            } else if (command.equals("unmark")) {
                int taskNo = Integer.parseInt(parts[1]) - 1;
                Task task = items.get(taskNo);
                task.MarkAsNotDone();
                System.out.println(line);
                System.out.println(" OK, I've marked this task as not done yet:");
                System.out.println(task);
                System.out.println(line);
            } else {
                Task newTask = new Task(input);
                items.add(newTask);
                System.out.println(line);
                System.out.println(" added: " + newTask);
                System.out.println(line);
            }
        }

        scanner.close();

    }
}
