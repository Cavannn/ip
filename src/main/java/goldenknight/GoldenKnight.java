package goldenknight;

import goldenknight.exception.DukeException;
import goldenknight.parser.Parser;
import goldenknight.storage.Storage;
import goldenknight.task.TaskList;
import goldenknight.ui.Ui;

/**
 * The {@code GoldenKnight} class represents the main entry point of the Golden Knight application.
 * It manages the core logic of the task management system, including loading tasks,
 * processing user commands, and saving changes to storage.
 */
public class GoldenKnight {

    private static final String CMD_FIND = "find";

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

    /**
     * Runs the main program loop of the Golden Knight application.
     * <p>
     * The loop continues until the user issues the "bye" command.
     * Each user command is parsed, executed, and saved to storage.
     * Errors are caught and displayed to the user without terminating the program.
     * </p>
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                String[] parts = Parser.parse(fullCommand);
                String command = parts[0];

                switch (command) {
                case "bye":
                    ui.showGoodbye();
                    isExit = true;
                    break;

                case "list":
                    ui.showTaskList(tasks);
                    break;

                case "mark":
                case "unmark":
                    ui.handleMarkUnmark(tasks, parts, command);
                    storage.save(tasks.getAll());
                    break;

                case "todo":
                    ui.handleAddTodo(tasks, parts);
                    storage.save(tasks.getAll());
                    break;

                case "deadline":
                    ui.handleAddDeadline(tasks, parts);
                    storage.save(tasks.getAll());
                    break;

                case "event":
                    ui.handleAddEvent(tasks, parts);
                    storage.save(tasks.getAll());
                    break;

                case "delete":
                    ui.handleDelete(tasks, parts);
                    storage.save(tasks.getAll());
                    break;

                case CMD_FIND:
                    ui.handleFind(tasks, parts);
                    break;

                default:
                    throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }

            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    /**
     * The entry point of the application.
     * Creates a new {@code GoldenKnight} instance with the default storage file
     * and starts the program.
     *
     * @param args command-line arguments (not used in this program)
     */
    public static void main(String[] args) {
        new GoldenKnight("./data/goldenknight.txt").run();
    }
}
