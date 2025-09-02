package goldenknight.ui;

import goldenknight.exception.DukeException;
import goldenknight.task.*;

import java.util.Scanner;

/**
 * The {@code Ui} class handles all interactions with the user.
 * It is responsible for displaying messages, reading user input,
 * and providing feedback on commands executed in the Golden Knight application.
 */
public class Ui {
    private Scanner scanner;

    /**
     * Constructs a new {@code Ui} instance with a {@link Scanner}
     * to read user input from the console.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Displays the welcome message when the application starts.
     */
    public void showWelcome() {
        System.out.println("_______________________________________");
        System.out.println("Hello! I'm the Golden Knight!");
        System.out.println("What can I do for you?");
        System.out.println("_______________________________________");
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbye() {
        System.out.println("_______________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("_______________________________________");
    }

    /**
     * Reads a full command input from the user.
     *
     * @return the input command entered by the user
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Displays a separator line to format UI output.
     */
    public void showLine() {
        System.out.println("_______________________________________");
    }

    /**
     * Displays an error message to the user.
     *
     * @param message the error message to display
     */
    public void showError(String message) {
        showLine();
        System.out.println(" " + message);
        showLine();
    }

    // --- Handlers ---

    /**
     * Displays all tasks currently in the task list.
     *
     * @param tasks the {@link TaskList} containing tasks to display
     */
    public void showTaskList(TaskList tasks) {
        showLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
        showLine();
    }

    /**
     * Handles marking or unmarking a task as done or not done.
     *
     * @param tasks   the {@link TaskList} containing the tasks
     * @param parts   the split user input command
     * @param command the command type ("mark" or "unmark")
     * @throws DukeException if the task number is missing, invalid, or out of range
     */
    public void handleMarkUnmark(TaskList tasks, String[] parts, String command) throws DukeException {
        if (parts.length < 2) {
            throw new DukeException("OOPS!!! Please specify the task number to " + command + ".");
        }
        int taskNo;
        try {
            taskNo = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("OOPS!!! goldenknight.task.Task number must be an integer.");
        }
        if (taskNo < 0 || taskNo >= tasks.size()) {
            throw new DukeException("OOPS!!! goldenknight.task.Task number is out of range.");
        }
        Task task = tasks.get(taskNo);
        if (command.equals("mark")) {
            task.markAsDone();
            showLine();
            System.out.println(" Nice! I've marked this task as done:");
            System.out.println(" " + task);
            showLine();
        } else {
            task.markAsNotDone();
            showLine();
            System.out.println(" OK, I've marked this task as not done yet:");
            System.out.println(" " + task);
            showLine();
        }
    }

    /**
     * Handles adding a new {@link Todo} task.
     *
     * @param tasks the {@link TaskList} to add the new task to
     * @param parts the split user input command
     * @throws DukeException if the description of the todo is missing or empty
     */
    public void handleAddTodo(TaskList tasks, String[] parts) throws DukeException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
        }
        Task task = new Todo(parts[1]);
        tasks.add(task);
        printAdded(task, tasks.size());
    }

    /**
     * Handles adding a new {@link Deadline} task.
     *
     * @param tasks the {@link TaskList} to add the new task to
     * @param parts the split user input command
     * @throws DukeException if the description or deadline is missing or invalid
     */
    public void handleAddDeadline(TaskList tasks, String[] parts) throws DukeException {
        if (parts.length < 2 || !parts[1].contains("/by")) {
            throw new DukeException("OOPS!!! The deadline command must include a description and /by.");
        }
        String[] details = parts[1].split(" /by ", 2);
        if (details.length < 2 || details[0].trim().isEmpty()) {
            throw new DukeException("OOPS!!! The description of a deadline cannot be empty.");
        }
        Task task = new Deadline(details[0], details[1]);
        tasks.add(task);
        printAdded(task, tasks.size());
    }

    /**
     * Handles adding a new {@link Event} task.
     *
     * @param tasks the {@link TaskList} to add the new task to
     * @param parts the split user input command
     * @throws DukeException if the event description, start, or end time is missing or invalid
     */
    public void handleAddEvent(TaskList tasks, String[] parts) throws DukeException {
        if (parts.length < 2 || !parts[1].contains("/from") || !parts[1].contains("/to")) {
            throw new DukeException("OOPS!!! The event command must include /from and /to.");
        }
        String[] details = parts[1].split(" /from | /to ", 3);
        Task task = new Event(details[0], details[1], details[2]);
        tasks.add(task);
        printAdded(task, tasks.size());
    }

    /**
     * Handles deleting a task from the task list.
     *
     * @param tasks the {@link TaskList} containing the tasks
     * @param parts the split user input command
     * @throws DukeException if the task number is missing, invalid, or out of range
     */
    public void handleDelete(TaskList tasks, String[] parts) throws DukeException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new DukeException("OOPS!!! Please specify the task number to delete.");
        }
        int taskNo;
        try {
            taskNo = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("OOPS!!! goldenknight.task.Task number must be an integer.");
        }
        if (taskNo < 0 || taskNo >= tasks.size()) {
            throw new DukeException("OOPS!!! goldenknight.task.Task number is out of range.");
        }
        Task removedTask = tasks.delete(taskNo);
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + removedTask);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        showLine();
    }

    /**
     * Prints a confirmation message when a new task is added.
     *
     * @param task  the task that was added
     * @param total the total number of tasks in the list after addition
     */
    private void printAdded(Task task, int total) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + total + " tasks in the list.");
        showLine();
    }
}
