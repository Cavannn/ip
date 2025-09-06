package goldenknight.ui;

import goldenknight.exception.DukeException;
import goldenknight.task.Deadline;
import goldenknight.task.Event;
import goldenknight.task.Task;
import goldenknight.task.TaskList;
import goldenknight.task.Todo;

import java.util.stream.Collectors;

/**
 * GUI-ready version of Ui.
 * Methods now return Strings instead of printing to console.
 */
public class Ui {
    private static final String LINE = "_______________________________________";

    // -------------------- Welcome / Goodbye --------------------

    public String getWelcomeMessage() {
        return LINE + "\nHello! I'm the Golden Knight!\nWhat can I do for you?\n" + LINE;
    }

    public String getGoodbyeMessage() {
        return LINE + "\nBye. Hope to see you again soon!\n" + LINE;
    }

    // -------------------- Task list --------------------

    public String getTaskListString(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE).append("\nHere are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        sb.append(LINE);
        return sb.toString();
    }

    // -------------------- Add tasks --------------------

    public String addTodoString(TaskList tasks, String description) throws DukeException {
        if (description == null || description.isBlank()) {
            throw new DukeException("The description of a todo cannot be empty.");
        }
        Task task = new Todo(description);
        tasks.add(task);
        return formatAddedTaskMessage(task, tasks.size());
    }

    public String addDeadlineString(TaskList tasks, String input) throws DukeException {
        if (input == null || input.isBlank() || !input.contains("/by")) {
            throw new DukeException("The deadline command must include a description and /by.");
        }
        String[] parts = input.split(" /by ", 2);
        if (parts.length < 2 || parts[0].isBlank() || parts[1].isBlank()) {
            throw new DukeException("The description and date of a deadline cannot be empty.");
        }
        Task task = new Deadline(parts[0], parts[1]);
        tasks.add(task);
        return formatAddedTaskMessage(task, tasks.size());
    }

    public String addEventString(TaskList tasks, String input) throws DukeException {
        if (input == null || input.isBlank() || !input.contains("/from") || !input.contains("/to")) {
            throw new DukeException("The event command must include /from and /to.");
        }
        String[] parts = input.split(" /from | /to ", 3);
        if (parts.length < 3 || parts[0].isBlank() || parts[1].isBlank() || parts[2].isBlank()) {
            throw new DukeException("The description, start time, and end time of an event cannot be empty.");
        }
        Task task = new Event(parts[0], parts[1], parts[2]);
        tasks.add(task);
        return formatAddedTaskMessage(task, tasks.size());
    }

    private String formatAddedTaskMessage(Task task, int totalTasks) {
        return LINE + "\nGot it. I've added this task:\n  " + task + "\nNow you have " + totalTasks + " tasks in the list.\n" + LINE;
    }

    // -------------------- Mark / Unmark --------------------

    public String markTaskString(TaskList tasks, int index) throws DukeException {
        checkIndex(tasks, index);
        Task task = tasks.get(index);
        task.markAsDone();
        return LINE + "\nNice! I've marked this task as done:\n  " + task + "\n" + LINE;
    }

    public String unmarkTaskString(TaskList tasks, int index) throws DukeException {
        checkIndex(tasks, index);
        Task task = tasks.get(index);
        task.markAsNotDone();
        return LINE + "\nOK! I've marked this task as not done yet:\n  " + task + "\n" + LINE;
    }

    private void checkIndex(TaskList tasks, int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("Task number is out of range.");
        }
    }

    // -------------------- Delete --------------------

    public String deleteTaskString(TaskList tasks, int index) throws DukeException {
        checkIndex(tasks, index);
        Task removed = tasks.delete(index);
        return LINE + "\nNoted. I've removed this task:\n  " + removed
                + "\nNow you have " + tasks.size() + " tasks in the list.\n" + LINE;
    }

    // -------------------- Find --------------------

    public String findTasksString(TaskList tasks, String keyword) throws DukeException {
        if (keyword == null || keyword.isBlank()) {
            throw new DukeException("The find command requires a keyword.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append(LINE).append("\nHere are the matching tasks in your list:\n");
        int count = 0;
        for (Task t : tasks.getAll()) {
            if (t.getDescription().contains(keyword)) {
                count++;
                sb.append(count).append(". ").append(t).append("\n");
            }
        }
        if (count == 0) sb.append("No matching tasks found.\n");
        sb.append(LINE);
        return sb.toString();
    }
}
