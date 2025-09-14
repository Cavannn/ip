package goldenknight.ui;

import goldenknight.exception.DukeException;
import goldenknight.task.Deadline;
import goldenknight.task.Event;
import goldenknight.task.Reminder;
import goldenknight.task.Task;
import goldenknight.task.TaskList;
import goldenknight.task.Todo;


/**
 * GUI-ready version of Ui.
 * Methods now return Strings instead of printing to console.
 */
public class Ui {
    private static final String LINE = "_______________________________________";

    public String getWelcomeMessage() {
        return LINE + "\nHello! I'm the Golden Knight HEEHEEHEEHAA!\n"
                + "What can I do for you?\n" + LINE;
    }

    public String getGoodbyeMessage() {
        return LINE + "\nBye. Hope to see you again soon!\n" + LINE;
    }

    public String getTaskListString(TaskList tasks) {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE).append("\nHere are the tasks in your list:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append(i + 1).append(". ").append(tasks.get(i)).append("\n");
        }
        sb.append(LINE);
        return sb.toString();
    }

    /**
     * Adds a new {@link Todo} task to the given task list.
     *
     * <p>The description must not be {@code null} or blank, otherwise
     * a {@link DukeException} is thrown. Once added, a confirmation
     * message containing the task details and the updated task count
     * is returned.</p>
     *
     * @param tasks       the list of tasks to which the new todo will be added
     * @param description the description of the todo task
     * @return a formatted confirmation message with the added task and total task count
     * @throws DukeException if the description is {@code null} or blank
     */
    public String addTodoString(TaskList tasks, String description) throws DukeException {
        if (description == null || description.isBlank()) {
            throw new DukeException("The description of a todo cannot be empty.");
        }
        Task task = new Todo(description);
        tasks.add(task);
        return formatAddedTaskMessage(task, tasks.size());
    }

    /**
     * Adds a new {@link Deadline} task to the given task list.
     *
     * <p>The input must contain a description and a due date separated by " /by ".
     * If the input is {@code null}, blank, or missing "/by", a {@link DukeException} is thrown.
     * Once added, a confirmation message containing the task details and the updated
     * task count is returned.</p>
     *
     * @param tasks the list of tasks to which the new deadline will be added
     * @param input the user input containing the description and due date, separated by " /by "
     * @return a formatted confirmation message with the added task and total task count
     * @throws DukeException if the input is {@code null}, blank, or incorrectly formatted
     */
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

    /**
     * Adds a new {@link Event} task to the given task list.
     *
     * <p>The input must contain a description, a start time, and an end time,
     * separated by " /from " and " /to ". If the input is {@code null}, blank,
     * or missing the required delimiters, a {@link DukeException} is thrown.
     * Once added, a confirmation message containing the task details and the updated
     * task count is returned.</p>
     *
     * @param tasks the list of tasks to which the new event will be added
     * @param input the user input containing the description, start time, and end time
     *              in the format "description /from start /to end"
     * @return a formatted confirmation message with the added task and total task count
     * @throws DukeException if the input is {@code null}, blank, or incorrectly formatted
     */
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

    /**
     * Formats a confirmation message for a newly added task.
     *
     * <p>This method returns a string containing the task that was added
     * and the updated total number of tasks in the list. The message
     * is wrapped with separator lines for readability in the GUI.</p>
     *
     * @param task       the task that was just added
     * @param totalTasks the updated total number of tasks in the list
     * @return a formatted confirmation message for the added task
     */
    private String formatAddedTaskMessage(Task task, int totalTasks) {
        return LINE + "\nGot it. I've added this task:\n  "
                + task + "\nNow you have " + totalTasks + " tasks in the list.\n" + LINE;
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param tasks the task list
     * @param index the index of the task to mark
     * @return a confirmation message
     * @throws DukeException if the index is invalid
     */
    public String markTaskString(TaskList tasks, int index) throws DukeException {
        checkIndex(tasks, index);
        Task task = tasks.get(index);
        task.markAsDone();
        return LINE + "\nNice! I've marked this task as done:\n  " + task + "\n" + LINE;
    }

    /**
     * Unmarks the task at the given index (sets it as not done).
     *
     * @param tasks the task list
     * @param index the index of the task to unmark
     * @return a confirmation message
     * @throws DukeException if the index is invalid
     */
    public String unmarkTaskString(TaskList tasks, int index) throws DukeException {
        checkIndex(tasks, index);
        Task task = tasks.get(index);
        task.markAsNotDone();
        return LINE + "\nOK! I've marked this task as not done yet:\n  " + task + "\n" + LINE;
    }

    /**
     * Checks if the given index is valid for the task list.
     *
     * @param tasks the task list
     * @param index the index to check
     * @throws DukeException if the index is out of range
     */
    private void checkIndex(TaskList tasks, int index) throws DukeException {
        if (index < 0 || index >= tasks.size()) {
            throw new DukeException("Task number is out of range.");
        }
    }

    /**
     * Deletes the task at the given index from the task list.
     *
     * @param tasks the task list
     * @param index the index of the task to delete
     * @return a confirmation message
     * @throws DukeException if the index is invalid
     */
    public String deleteTaskString(TaskList tasks, int index) throws DukeException {
        checkIndex(tasks, index);
        Task removed = tasks.delete(index);
        return LINE + "\nNoted. I've removed this task:\n  "
                + removed + "\nNow you have " + tasks.size() + " tasks in the list.\n" + LINE;
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param tasks   the task list
     * @param keyword the search keyword
     * @return a formatted string of matching tasks
     * @throws DukeException if the keyword is null or blank
     */
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
        if (count == 0) {
            sb.append("No matching tasks found.\n");
        }
        sb.append(LINE);
        return sb.toString();
    }

    /**
     * Returns a message showing the next upcoming task (deadline or event).
     *
     * @param tasks the TaskList containing all tasks
     * @return a reminder string
     */
    public String getNextTaskReminder(TaskList tasks) {
        // Assuming TaskList has a method getAll() that returns ArrayList<Task>
        return LINE + "\n" + Reminder.reminder(tasks.getAll()) + "\n" + LINE;
    }

}
