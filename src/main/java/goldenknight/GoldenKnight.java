package goldenknight;

import goldenknight.exception.DukeException;
import goldenknight.storage.Storage;
import goldenknight.task.TaskList;
import goldenknight.ui.Ui;

/**
 * The {@code GoldenKnight} class represents the main backend of the Golden Knight application.
 * It manages tasks, storage, and generates responses for GUI input.
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
    }

    // -------------------- Public Methods for GUI --------------------

    public String getWelcomeMessage() {
        return ui.getWelcomeMessage();
    }

    public String getGoodbyeMessage() {
        return ui.getGoodbyeMessage();
    }

    public String listTasks() {
        return ui.getTaskListString(tasks);
    }

    public String addTodo(String description) {
        try {
            String result = ui.addTodoString(tasks, description);
            storage.save(tasks.getAll());
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String addDeadline(String input) {
        try {
            String result = ui.addDeadlineString(tasks, input);
            storage.save(tasks.getAll());
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String addEvent(String input) {
        try {
            String result = ui.addEventString(tasks, input);
            storage.save(tasks.getAll());
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String markTask(int index) {
        try {
            String result = ui.markTaskString(tasks, index);
            storage.save(tasks.getAll());
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String unmarkTask(int index) {
        try {
            String result = ui.unmarkTaskString(tasks, index);
            storage.save(tasks.getAll());
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String deleteTask(int index) {
        try {
            String result = ui.deleteTaskString(tasks, index);
            storage.save(tasks.getAll());
            return result;
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }

    public String findTasks(String keyword) {
        try {
            return ui.findTasksString(tasks, keyword);
        } catch (DukeException e) {
            return "Error: " + e.getMessage();
        }
    }
}
