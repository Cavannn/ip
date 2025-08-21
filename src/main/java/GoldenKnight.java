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
                + "Hello! I'm the Golden Knight!\n"
                + "What can I do for you?\n"
                + "_______________________________________\n"
                + " Bye. Hope to see you again soon! \n"
                + "_______________________________________\n";

        String greeting = "_______________________________________\n"
                + "Hello! I'm the Golden Knight!\n"
                + "What can I do for you?\n"
                + "_______________________________________\n";

        String goodbye = "_______________________________________\n"
                + " Bye. Hope to see you again soon!\n"
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
            } else if (command.equals("todo")) {
                String description = parts[1];
                Task task = new Todo(description);
                items.add(task);
                printAdded(task, items.size());
            } else if (command.equals("deadline")) {
                String[] details = parts[1].split(" /by ", 2);
                String description = details[0];
                String by = details[1];
                Task task = new Deadline(description, by);
                items.add(task);
                printAdded(task, items.size());
            } else if (command.equals("event")) {
                String[] details = parts[1].split(" /from | /to ", 3);
                String description = details[0];
                String from = details[1];
                String to = details[2];
                Task task = new Event(description, from, to);
                items.add(task);
                printAdded(task, items.size());
            } else {
                System.out.println(line);
                System.out.println(" OOPS! I don't know what that means.");
                System.out.println(line);
            }
        }

        scanner.close();

    }

    private static void printAdded(Task task, int total) {
        printLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + total + " tasks in the list.");
        printLine();
    }

    private static void printLine() {
        System.out.println("_______________________________________");
    }
}
