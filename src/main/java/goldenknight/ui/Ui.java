package goldenknight.ui;

import goldenknight.exception.DukeException;
import goldenknight.task.*;

import java.util.Scanner;

public class Ui {
    private static final String LINE = "_______________________________________";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";

    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        showLine();
        System.out.println("Hello! I'm the Golden Knight!");
        System.out.println("What can I do for you?");
        showLine();
    }

    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println(LINE);
    }

    public void showError(String message) {
        showLine();
        System.out.println(" " + message);
        showLine();
    }

    public void showTaskList(TaskList tasks) {
        if (tasks == null) {
            throw new IllegalArgumentException("Task list cannot be null");
        }
        showLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
        showLine();
    }

    public void handleMarkUnmark(TaskList tasks, String[] parts, String command) throws DukeException {
        int taskNo = parseTaskNumber(parts);
        Task task = tasks.get(taskNo);

        if (CMD_MARK.equals(command)) {
            task.markAsDone();
            printMessage("Nice! I've marked this task as done:", task);
        } else if (CMD_UNMARK.equals(command)) {
            task.markAsNotDone();
            printMessage("OK, I've marked this task as not done yet:", task);
        } else {
            throw new DukeException("Invalid mark/unmark command");
        }
    }

    public void handleAddTodo(TaskList tasks, String[] parts) throws DukeException {
        validateParts(parts, "The description of a todo cannot be empty.");
        Task task = new Todo(parts[1]);
        tasks.add(task);
        printAdded(task, tasks.size());
    }

    public void handleAddDeadline(TaskList tasks, String[] parts) throws DukeException {
        validateParts(parts, "The deadline command must include a description and /by.");

        String input = parts[1].trim();
        if (!input.contains("/by")) {
            throw new DukeException("The deadline command must include /by.");
        }

        String[] details = input.split(" /by ", 2);
        if (details.length < 2 || details[0].trim().isEmpty() || details[1].trim().isEmpty()) {
            throw new DukeException("The description and date of a deadline cannot be empty.");
        }

        Task task = new Deadline(details[0], details[1]);
        tasks.add(task);
        printAdded(task, tasks.size());
    }

    public void handleAddEvent(TaskList tasks, String[] parts) throws DukeException {
        validateParts(parts, "The event command must include /from and /to.");

        String input = parts[1].trim();
        if (!input.contains("/from") || !input.contains("/to")) {
            throw new DukeException("The event command must include /from and /to.");
        }

        String[] details = input.split(" /from | /to ", 3);
        if (details.length < 3
                || details[0].trim().isEmpty()
                || details[1].trim().isEmpty()
                || details[2].trim().isEmpty()) {
            throw new DukeException("The description, start time, and end time of an event cannot be empty.");
        }

        Task task = new Event(details[0], details[1], details[2]);
        tasks.add(task);
        printAdded(task, tasks.size());
    }

    public void handleDelete(TaskList tasks, String[] parts) throws DukeException {
        int taskNo = parseTaskNumber(parts);
        Task removedTask = tasks.delete(taskNo);

        showLine();
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removedTask);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        showLine();
    }

    // ------------------ Helper Methods ------------------

    private void validateParts(String[] parts, String errorMessage) throws DukeException {
        if (parts == null || parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new DukeException("OOPS!!! " + errorMessage);
        }
    }

    private int parseTaskNumber(String[] parts) throws DukeException {
        validateParts(parts, "Please specify the task number.");
        int taskNo;
        try {
            taskNo = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("Task number must be an integer.");
        }
        if (taskNo < 0) {
            throw new DukeException("Task number must be positive.");
        }
        return taskNo;
    }

    public void handleFind(TaskList tasks, String[] parts) throws DukeException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new DukeException("OOPS!!! The find command requires a keyword.");
        }
        String keyword = parts[1].trim();
        showLine();
        System.out.println(" Here are the matching tasks in your list:");
        int count = 1;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task.getDescription().contains(keyword)) {
                System.out.println(" " + count + ". " + task);
                count++;
            }
        }
        if (count == 1) {
            System.out.println(" No matching tasks found.");
        }
        showLine();
    }

    private void printAdded(Task task, int total) {
        printMessage("Got it. I've added this task:", task);
        System.out.println("Now you have " + total + " tasks in the list.");
        showLine();
    }

    private void printMessage(String message, Task task) {
        showLine();
        System.out.println(message);
        System.out.println("  " + task);
        showLine();
    }
}
