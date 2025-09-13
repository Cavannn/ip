package goldenknight;

import goldenknight.exception.DukeException;
import goldenknight.storage.Storage;
import goldenknight.task.TaskList;
import goldenknight.ui.Ui;

/**
 * The {@code GoldenKnight} class represents the main backend of the Golden Knight application.
 * <p>
 * It manages the task list, handles storage, and generates responses for the GUI
 * based on user input commands. Methods in this class return strings suitable
 * for display in the GUI.
 */
public class GoldenKnight {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new {@code GoldenKnight} instance.
     * Initializes the UI, storage, and task list.
     *
     * @param filePath the file path where tasks are stored
     */
    public GoldenKnight(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());

        // Assertions: invariants after construction
        assert ui != null : "UI should be initialized";
        assert storage != null : "Storage should be initialized";
        assert tasks != null : "TaskList should be initialized";
    }

    // -------------------- Public Methods for GUI --------------------

    /**
     * Returns the welcome message displayed when the application starts.
     *
     * @return the welcome message string
     */
    public String getWelcomeMessage() {
        assert ui != null : "UI must not be null when getting welcome message";
        return ui.getWelcomeMessage();
    }

    /**
     * Returns the goodbye message displayed when the application exits.
     *
     * @return the goodbye message string
     */
    public String getGoodbyeMessage() {
        assert ui != null : "UI must not be null when getting goodbye message";
        return ui.getGoodbyeMessage();
    }

    /**
     * Returns a formatted string containing the list of tasks.
     *
     * @return the string representation of the current task list
     */
    public String listTasks() {
        assert tasks != null : "TaskList must not be null when listing tasks";
        return ui.getTaskListString(tasks);
    }

    /**
     * Adds a todo task to the task list and saves it to storage.
     *
     * @param description the description of the todo task
     * @return a confirmation message, or an error message if the task cannot be added
     */
    public String addTodo(String description) {
        try {
            String result = ui.addTodoString(tasks, description);
            storage.save(tasks.getAll());
            assert result != null && !result.isBlank() : "Result message should not be null/blank";
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Adds a deadline task to the task list and saves it to storage.
     *
     * @param input the user input string containing the deadline details
     * @return a confirmation message, or an error message if the task cannot be added
     */
    public String addDeadline(String input) {
        try {
            String result = ui.addDeadlineString(tasks, input);
            storage.save(tasks.getAll());
            assert result != null && !result.isBlank() : "Result message should not be null/blank";
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Adds an event task to the task list and saves it to storage.
     *
     * @param input the user input string containing the event details
     * @return a confirmation message, or an error message if the task cannot be added
     */
    public String addEvent(String input) {
        try {
            String result = ui.addEventString(tasks, input);
            storage.save(tasks.getAll());
            assert result != null && !result.isBlank() : "Result message should not be null/blank";
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Marks the task at the specified index as completed and saves the updated list.
     *
     * @param index the index of the task to mark
     * @return a confirmation message, or an error message if the index is invalid
     */
    public String markTask(int index) {
        try {
            String result = ui.markTaskString(tasks, index);
            storage.save(tasks.getAll());
            assert result.contains("marked") : "Result should confirm marking";
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Unmarks the task at the specified index (sets it as not completed)
     * and saves the updated list.
     *
     * @param index the index of the task to unmark
     * @return a confirmation message, or an error message if the index is invalid
     */
    public String unmarkTask(int index) {
        try {
            String result = ui.unmarkTaskString(tasks, index);
            storage.save(tasks.getAll());
            assert result.contains("not done") : "Result should confirm unmarking";
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Deletes the task at the specified index from the task list and saves the update.
     *
     * @param index the index of the task to delete
     * @return a confirmation message, or an error message if the index is invalid
     */
    public String deleteTask(int index) {
        try {
            String result = ui.deleteTaskString(tasks, index);
            storage.save(tasks.getAll());
            assert result.contains("removed") : "Result should confirm deletion";
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    /**
     * Finds tasks that contain the given keyword in their description.
     *
     * @param keyword the keyword to search for
     * @return a string listing the matching tasks, or an error message if none are found
     */
    public String findTasks(String keyword) {
        try {
            return ui.findTasksString(tasks, keyword);
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }
}
