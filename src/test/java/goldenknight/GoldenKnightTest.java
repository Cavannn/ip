package goldenknight;

import goldenknight.exception.DukeException;
import goldenknight.storage.Storage;
import goldenknight.task.TaskList;
import goldenknight.ui.Ui;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class GoldenKnightTest {

    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;
    private ByteArrayOutputStream outContent;
    private ByteArrayInputStream inContent;

    @BeforeEach
    void setUpStreams() {
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);
    }

    @Test
    void run_shouldProcessTodoAndListCommands() {
        String simulatedInput = "todo Finish homework\nlist\nbye\n";
        inContent = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inContent);

        GoldenKnight app = new GoldenKnight("test_tasks.txt");
        app.run();

        String output = outContent.toString();

        // Check for welcome message
        assertTrue(output.contains("Hello! I'm the Golden Knight!"));

        // Check that the todo task is added
        assertTrue(output.contains("Finish homework"));

        // Check that list command shows the task
        assertTrue(output.contains("1. [T][ ] Finish homework"));

        // Check goodbye message
        assertTrue(output.contains("Bye. Hope to see you again soon!"));
    }

    @Test
    void run_shouldMarkAndUnmarkTasks() throws IOException {
        // Prepare a task file
        Storage storage = new Storage("test_tasks.txt");
        TaskList tasks = new TaskList();
        tasks.add(new goldenknight.task.Todo("Finish homework"));
        storage.save(tasks.getAll());

        String simulatedInput = "mark 1\nunmark 1\nbye\n";
        inContent = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inContent);

        GoldenKnight app = new GoldenKnight("test_tasks.txt");
        app.run();

        String output = outContent.toString();

        // Check that the task is marked
        assertTrue(output.contains("Nice! I've marked this task as done"));
        assertTrue(output.contains("[T][X] Finish homework"));

        // Check that the task is unmarked
        assertTrue(output.contains("OK, I've marked this task as not done yet"));
        assertTrue(output.contains("[T][ ] Finish homework"));
    }

    @Test
    void run_invalidCommand_showsError() {
        String simulatedInput = "foobar\nbye\n";
        inContent = new ByteArrayInputStream(simulatedInput.getBytes());
        System.setIn(inContent);

        GoldenKnight app = new GoldenKnight("test_tasks.txt");
        app.run();

        String output = outContent.toString();

        assertTrue(output.contains("OOPS!!! I'm sorry, but I don't know what that means"));
    }
}
