package goldenknight;

import goldenknight.exception.DukeException;
import goldenknight.parser.Parser;
import goldenknight.storage.Storage;
import goldenknight.task.Task;
import goldenknight.task.TaskList;
import goldenknight.ui.Ui;

import java.util.ArrayList;

public class GoldenKnight {

    private static final String CMD_BYE = "bye";
    private static final String CMD_LIST = "list";
    private static final String CMD_MARK = "mark";
    private static final String CMD_UNMARK = "unmark";
    private static final String CMD_TODO = "todo";
    private static final String CMD_DEADLINE = "deadline";
    private static final String CMD_EVENT = "event";
    private static final String CMD_DELETE = "delete";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public GoldenKnight(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        ArrayList<Task> loadedTasks = storage.load();
        tasks = new TaskList(loadedTasks != null ? loadedTasks : new ArrayList<>());
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                String[] parts = Parser.parse(fullCommand);
                String command = parts[0];

                switch (command) {
                case CMD_BYE -> {
                    ui.showGoodbye();
                    isExit = true;
                }
                case CMD_LIST -> ui.showTaskList(tasks);
                case CMD_MARK, CMD_UNMARK -> {
                    ui.handleMarkUnmark(tasks, parts, command);
                    saveTasks();
                }
                case CMD_TODO -> {
                    ui.handleAddTodo(tasks, parts);
                    saveTasks();
                }
                case CMD_DEADLINE -> {
                    ui.handleAddDeadline(tasks, parts);
                    saveTasks();
                }
                case CMD_EVENT -> {
                    ui.handleAddEvent(tasks, parts);
                    saveTasks();
                }
                case CMD_DELETE -> {
                    ui.handleDelete(tasks, parts);
                    saveTasks();
                }
                default -> throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }

            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    private void saveTasks() throws DukeException {
        storage.save(tasks.getAll());
    }

    public static void main(String[] args) {
        new GoldenKnight("./data/goldenknight.txt").run();
    }
}
