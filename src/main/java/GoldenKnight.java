import java.util.ArrayList;
import java.util.Scanner;

public class GoldenKnight {

    private static final String FILE_PATH = "./data/goldenknight.txt";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Storage storage = new Storage(FILE_PATH);

        // Load existing tasks
        ArrayList<Task> items = storage.load();

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
            try {
                if (input.trim().isEmpty()) {
                    throw new DukeException("OOPS!!! You entered an empty command.");
                }

                String[] parts = input.split(" ", 2);
                String command = parts[0];

                if (command.equals("bye")) {
                    System.out.print(goodbye);
                    break;

                } else if (command.equals("list")) {
                    System.out.print(line);
                    System.out.println(" Here are the tasks in your list:");
                    for (int i = 0; i < items.size(); i++) {
                        System.out.println(" " + (i + 1) + ". " + items.get(i));
                    }
                    System.out.print(line);

                } else if (command.equals("mark") || command.equals("unmark")) {
                    if (parts.length < 2) {
                        throw new DukeException("OOPS!!! Please specify the task number to " + command + ".");
                    }
                    int taskNo;
                    try {
                        taskNo = Integer.parseInt(parts[1]) - 1;
                    } catch (NumberFormatException e) {
                        throw new DukeException("OOPS!!! Task number must be an integer.");
                    }
                    if (taskNo < 0 || taskNo >= items.size()) {
                        throw new DukeException("OOPS!!! Task number is out of range.");
                    }
                    Task task = items.get(taskNo);
                    if (command.equals("mark")) {
                        task.markAsDone();
                        System.out.print(line);
                        System.out.println(" Nice! I've marked this task as done:");
                        System.out.println(" " + task);
                        System.out.print(line);
                    } else {
                        task.markAsNotDone();
                        System.out.print(line);
                        System.out.println(" OK, I've marked this task as not done yet:");
                        System.out.println(" " + task);
                        System.out.print(line);
                    }
                    storage.save(items); // save changes

                } else if (command.equals("todo")) {
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
                    }
                    Task task = new Todo(parts[1]);
                    items.add(task);
                    printAdded(task, items.size());
                    storage.save(items);

                } else if (command.equals("deadline")) {
                    if (parts.length < 2 || !parts[1].contains("/by")) {
                        throw new DukeException("OOPS!!! The deadline command must include a description and /by.");
                    }
                    String[] details = parts[1].split(" /by ", 2);
                    if (details.length < 2 || details[0].trim().isEmpty()) {
                        throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
                    }
                    String description = details[0];
                    String by = details[1];
                    Task task = new Deadline(description, by);
                    items.add(task);
                    printAdded(task, items.size());
                    storage.save(items);

                } else if (command.equals("event")) {
                    if (parts.length < 2 || !parts[1].contains("/from") || !parts[1].contains("/to")) {
                        throw new DukeException("OOPS!!! The event command must include /from and /to.");
                    }
                    String[] details = parts[1].split(" /from | /to ", 3);
                    Task task = new Event(details[0], details[1], details[2]);
                    items.add(task);
                    printAdded(task, items.size());
                    storage.save(items);

                } else if (command.equals("delete")) {
                    if (parts.length < 2 || parts[1].trim().isEmpty()) {
                        throw new DukeException("OOPS!!! Please specify the task number to delete.");
                    }
                    int taskNo;
                    try {
                        taskNo = Integer.parseInt(parts[1]) - 1;
                    } catch (NumberFormatException e) {
                        throw new DukeException("OOPS!!! Task number must be an integer.");
                    }
                    if (taskNo < 0 || taskNo >= items.size()) {
                        throw new DukeException("OOPS!!! Task number is out of range.");
                    }

                    Task removedTask = items.remove(taskNo);
                    printLine();
                    System.out.println(" Noted. I've removed this task:");
                    System.out.println("   " + removedTask);
                    System.out.println(" Now you have " + items.size() + " tasks in the list.");
                    printLine();

                    storage.save(items);

                } else {
                    throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }

            } catch (DukeException e) {
                System.out.print(line);
                System.out.println(" " + e.getMessage());
                System.out.print(line);
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

// Custom exception class
class DukeException extends Exception {
    public DukeException(String message) {
        super(message);
    }
}
