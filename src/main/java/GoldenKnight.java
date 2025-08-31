public class GoldenKnight {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public GoldenKnight(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.load());
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

                    default:
                        throw new DukeException("OOPS!!! I'm sorry, but I don't know what that means :-(");
                }

            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args) {
        new GoldenKnight("./data/goldenknight.txt").run();
    }
}
