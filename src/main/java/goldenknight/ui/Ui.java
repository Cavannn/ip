package goldenknight.ui;

import java.util.Scanner;

import goldenknight.exception.DukeException;
import goldenknight.task.Deadline;
import goldenknight.task.Event;
import goldenknight.task.Task;
import goldenknight.task.TaskList;
import goldenknight.task.Todo;

/**
 * The {@code Ui} class handles all interactions with the user.
 * It is responsible for displaying messages, reading user input,
 * and providing feedback on commands executed in the Golden Knight application.
 */
public class Ui {
    private static final String LINE = "_______________________________________";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";

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
        showLine();
        System.out.println("Hello! I'm the Golden Knight!");
        System.out.println("What can I do for you?");
        showLine();
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void showGoodbye() {
        showLine();
        System.out.println("Bye. Hope to see you again soon!");
        showLine();
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
        System.out.println(LINE);
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

    /**
     * Handles marking or unmarking a task as done or not done.
     *
     * @param tasks   the {@link TaskList} containing the tasks
     * @param parts   the split user input command
     * @param command the command type ("mark" or "unmark")
     * @throws DukeException if the task number is missing, invalid, or out of range
     */
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

    /**
     * Handles adding a new {@link Todo} task.
     *
     * @param tasks the {@link TaskList} to add the new task to
     * @param parts the split user input command
     * @throws DukeException if the description of the todo is missing or empty
     */
    public void handleAddTodo(TaskList tasks, String[] parts) throws DukeException {
        validateParts(parts, "The description of a todo cannot be empty.");
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

    /**
     * Handles adding a new {@link Event} task.
     *
     * @param tasks the {@link TaskList} to add the new task to
     * @param parts the split user input command
     * @throws DukeException if the event description, start, or end time is missing or invalid
     */
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

    /**
     * Handles deleting a task from the task list.
     *
     * @param tasks the {@link TaskList} containing the tasks
     * @param parts the split user input command
     * @throws DukeException if the task number is missing, invalid, or out of range
     */
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

    /**
     * Handles the "find" command by searching for tasks that contain the specified keyword
     * in their descriptions, and prints the matching tasks to the console.
     *
     * @param tasks the {@code TaskList} containing all tasks to search through
     * @param parts the array of strings representing the user's input command,
     *              where the keyword should be at index 1
     * @throws DukeException if the keyword is missing or empty
     */
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

    /**
     * Prints a confirmation message when a new task is added.
     *
     * @param task  the task that was added
     * @param total the total number of tasks in the list after addition
     */
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
