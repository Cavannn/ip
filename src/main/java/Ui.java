import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println("_______________________________________");
        System.out.println("Hello! I'm the Golden Knight!");
        System.out.println("What can I do for you?");
        System.out.println("_______________________________________");
    }

    public void showGoodbye() {
        System.out.println("_______________________________________");
        System.out.println(" Bye. Hope to see you again soon!");
        System.out.println("_______________________________________");
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println("_______________________________________");
    }

    public void showError(String message) {
        showLine();
        System.out.println(" " + message);
        showLine();
    }

    // --- Handlers ---
    public void showTaskList(TaskList tasks) {
        showLine();
        System.out.println(" Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println(" " + (i + 1) + ". " + tasks.get(i));
        }
        showLine();
    }

    public void handleMarkUnmark(TaskList tasks, String[] parts, String command) throws DukeException {
        if (parts.length < 2) {
            throw new DukeException("OOPS!!! Please specify the task number to " + command + ".");
        }
        int taskNo;
        try {
            taskNo = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("OOPS!!! Task number must be an integer.");
        }
        if (taskNo < 0 || taskNo >= tasks.size()) {
            throw new DukeException("OOPS!!! Task number is out of range.");
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

    public void handleAddTodo(TaskList tasks, String[] parts) throws DukeException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
        }
        Task task = new Todo(parts[1]);
        tasks.add(task);
        printAdded(task, tasks.size());
    }

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

    public void handleAddEvent(TaskList tasks, String[] parts) throws DukeException {
        if (parts.length < 2 || !parts[1].contains("/from") || !parts[1].contains("/to")) {
            throw new DukeException("OOPS!!! The event command must include /from and /to.");
        }
        String[] details = parts[1].split(" /from | /to ", 3);
        Task task = new Event(details[0], details[1], details[2]);
        tasks.add(task);
        printAdded(task, tasks.size());
    }

    public void handleDelete(TaskList tasks, String[] parts) throws DukeException {
        if (parts.length < 2 || parts[1].trim().isEmpty()) {
            throw new DukeException("OOPS!!! Please specify the task number to delete.");
        }
        int taskNo;
        try {
            taskNo = Integer.parseInt(parts[1]) - 1;
        } catch (NumberFormatException e) {
            throw new DukeException("OOPS!!! Task number must be an integer.");
        }
        if (taskNo < 0 || taskNo >= tasks.size()) {
            throw new DukeException("OOPS!!! Task number is out of range.");
        }
        Task removedTask = tasks.delete(taskNo);
        showLine();
        System.out.println(" Noted. I've removed this task:");
        System.out.println("   " + removedTask);
        System.out.println(" Now you have " + tasks.size() + " tasks in the list.");
        showLine();
    }

    private void printAdded(Task task, int total) {
        showLine();
        System.out.println(" Got it. I've added this task:");
        System.out.println("   " + task);
        System.out.println(" Now you have " + total + " tasks in the list.");
        showLine();
    }
}
