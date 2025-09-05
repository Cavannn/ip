package goldenknight.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import goldenknight.exception.DukeException;
import goldenknight.task.TaskList;
import goldenknight.task.Todo;

class UiTest {

    private Ui ui;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    void setUp() {
        ui = new Ui();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void showWelcome_shouldPrintWelcomeMessage() {
        ui.showWelcome();
        String output = outContent.toString();
        assertTrue(output.contains("Hello! I'm the Golden Knight!"));
    }

    @Test
    void showGoodbye_shouldPrintGoodbyeMessage() {
        ui.showGoodbye();
        String output = outContent.toString();
        assertTrue(output.contains("Hope to see you again soon!"));
    }

    @Test
    void showError_shouldPrintErrorMessage() {
        ui.showError("Test error");
        String output = outContent.toString();
        assertTrue(output.contains("Test error"));
    }

    @Test
    void showTaskList_shouldPrintAllTasks() {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Finish homework"));
        tasks.add(new Todo("Read book"));

        ui.showTaskList(tasks);
        String output = outContent.toString();
        assertTrue(output.contains("Here are the tasks in your list"));
        assertTrue(output.contains("1. [T][ ] Finish homework"));
        assertTrue(output.contains("2. [T][ ] Read book"));
    }

    @Test
    void readCommand_shouldReadInputCorrectly() {
        String simulatedInput = "todo Finish homework\n";
        System.setIn(new ByteArrayInputStream(simulatedInput.getBytes()));

        Ui uiWithInput = new Ui(); // create after setting System.in
        String command = uiWithInput.readCommand();
        assertEquals("todo Finish homework", command);
    }

    @Test
    void handleAddTodo_shouldAddTodoCorrectly() throws DukeException {
        TaskList tasks = new TaskList();
        String[] parts = {"todo", "Finish homework"};

        ui.handleAddTodo(tasks, parts);

        assertEquals(1, tasks.size());
        assertEquals("Finish homework", tasks.get(0).toFileFormat().split(" \\| ")[2]);
        assertTrue(outContent.toString().contains("I've added this task"));
    }

    @Test
    void handleMarkUnmark_shouldMarkAndUnmarkTasks() throws DukeException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Finish homework"));

        // mark task
        ui.handleMarkUnmark(tasks, new String[]{"mark", "1"}, "mark");
        assertEquals("X", tasks.get(0).getStatusIcon());

        // unmark task
        ui.handleMarkUnmark(tasks, new String[]{"unmark", "1"}, "unmark");
        assertEquals(" ", tasks.get(0).getStatusIcon());
    }

    @Test
    void handleDelete_shouldRemoveTask() throws DukeException {
        TaskList tasks = new TaskList();
        tasks.add(new Todo("Finish homework"));

        ui.handleDelete(tasks, new String[]{"delete", "1"});
        assertEquals(0, tasks.size());
        assertTrue(outContent.toString().contains("I've removed this task"));
    }

    @Test
    void handleAddDeadline_invalidInput_shouldThrowException() {
        TaskList tasks = new TaskList();
        String[] parts = {"deadline", "Do homework"};

        Exception e = assertThrows(DukeException.class, () -> ui.handleAddDeadline(tasks, parts));
        assertTrue(e.getMessage().contains("/by"));
    }

    @Test
    void handleAddEvent_invalidInput_shouldThrowException() {
        TaskList tasks = new TaskList();
        String[] parts = {"event", "Project meeting"};

        Exception e = assertThrows(DukeException.class, () -> ui.handleAddEvent(tasks, parts));
        assertTrue(e.getMessage().contains("/from") && e.getMessage().contains("/to"));
    }
}
