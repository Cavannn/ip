package goldenknight.ui;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import goldenknight.exception.DukeException;
import goldenknight.task.TaskList;

class UiTest {

    private Ui ui;
    private TaskList tasks;

    @BeforeEach
    void setUp() {
        ui = new Ui();
        tasks = new TaskList();
    }

    @Test
    void getWelcomeMessage_shouldContainGreeting() {
        String msg = ui.getWelcomeMessage();
        assertTrue(msg.contains("Hello! I'm the Golden Knight!"));
    }

    @Test
    void getGoodbyeMessage_shouldContainFarewell() {
        String msg = ui.getGoodbyeMessage();
        assertTrue(msg.contains("Bye. Hope to see you again soon!"));
    }

    @Test
    void addTodoString_shouldAddTodo() throws DukeException {
        String result = ui.addTodoString(tasks, "Finish homework");
        assertTrue(result.contains("I've added this task"));
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0).getDescription().equals("Finish homework"));
    }

    @Test
    void addTodoString_invalidInput_shouldThrow() {
        DukeException e = assertThrows(DukeException.class, () -> ui.addTodoString(tasks, ""));
        assertTrue(e.getMessage().contains("cannot be empty"));
    }

    @Test
    void addDeadlineString_shouldAddDeadline() throws DukeException {
        String input = "Submit report /by 6/9/2025 2359";
        String result = ui.addDeadlineString(tasks, input);
        assertTrue(result.contains("I've added this task"));
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0).getDescription().equals("Submit report"));
    }

    @Test
    void addDeadlineString_invalidInput_shouldThrow() {
        String input = "Submit report";
        DukeException e = assertThrows(DukeException.class, () -> ui.addDeadlineString(tasks, input));
        assertTrue(e.getMessage().contains("/by"));
    }

    @Test
    void addEventString_shouldAddEvent() throws DukeException {
        String input = "Team meeting /from 6/9/2025 1000 /to 6/9/2025 1100";
        String result = ui.addEventString(tasks, input);
        assertTrue(result.contains("I've added this task"));
        assertEquals(1, tasks.size());
        assertTrue(tasks.get(0).getDescription().equals("Team meeting"));
    }

    @Test
    void addEventString_invalidInput_shouldThrow() {
        String input = "Team meeting";
        DukeException e = assertThrows(DukeException.class, () -> ui.addEventString(tasks, input));
        assertTrue(e.getMessage().contains("/from") && e.getMessage().contains("/to"));
    }

    @Test
    void markTaskString_shouldMarkTask() throws DukeException {
        ui.addTodoString(tasks, "Finish homework");
        String result = ui.markTaskString(tasks, 0);
        assertTrue(result.contains("marked this task as done"));
        assertEquals("X", tasks.get(0).getStatusIcon());
    }

    @Test
    void unmarkTaskString_shouldUnmarkTask() throws DukeException {
        ui.addTodoString(tasks, "Finish homework");
        ui.markTaskString(tasks, 0);
        String result = ui.unmarkTaskString(tasks, 0);
        assertTrue(result.contains("marked this task as not done yet"));
        assertEquals(" ", tasks.get(0).getStatusIcon());
    }

    @Test
    void markTaskString_invalidIndex_shouldThrow() {
        DukeException e = assertThrows(DukeException.class, () -> ui.markTaskString(tasks, 0));
        assertTrue(e.getMessage().contains("out of range"));
    }

    @Test
    void deleteTaskString_shouldDeleteTask() throws DukeException {
        ui.addTodoString(tasks, "Finish homework");
        String result = ui.deleteTaskString(tasks, 0);
        assertTrue(result.contains("I've removed this task"));
        assertEquals(0, tasks.size());
    }

    @Test
    void findTasksString_shouldFindMatchingTasks() throws DukeException {
        ui.addTodoString(tasks, "Finish homework");
        ui.addTodoString(tasks, "Read book");
        String result = ui.findTasksString(tasks, "Read");
        assertTrue(result.contains("Read book"));
        assertTrue(!result.contains("Finish homework"));
    }

    @Test
    void findTasksString_noMatch_shouldReturnMessage() throws DukeException {
        ui.addTodoString(tasks, "Finish homework");
        String result = ui.findTasksString(tasks, "Play");
        assertTrue(result.contains("No matching tasks found"));
    }
}
