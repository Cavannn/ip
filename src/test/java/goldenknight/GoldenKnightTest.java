package goldenknight;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GoldenKnightTest {

    private GoldenKnight goldenKnight;

    @BeforeEach
    void setUp() {
        // Use a test file path; storage will save/load tasks here
        goldenKnight = new GoldenKnight("test_tasks.txt");
    }

    @Test
    void getWelcomeMessage_shouldContainGreeting() {
        String welcome = goldenKnight.getWelcomeMessage();
        assertTrue(welcome.contains("Hello! I'm the Golden Knight!"));
    }

    @Test
    void addTodo_and_listTasks_shouldWork() {
        String addResponse = goldenKnight.addTodo("Finish homework");
        assertTrue(addResponse.contains("I've added this task"));
        assertTrue(addResponse.contains("Finish homework"));

        String listResponse = goldenKnight.listTasks();
        assertTrue(listResponse.contains("Here are the tasks in your list"));
        assertTrue(listResponse.contains("1. [T][ ] Finish homework"));
    }

    @Test
    void mark_and_unmarkTask_shouldWork() {
        goldenKnight.addTodo("Finish homework");

        String markResponse = goldenKnight.markTask(0);
        assertTrue(markResponse.contains("marked this task as done"));
        assertTrue(markResponse.contains("[T][X] Finish homework"));

        String unmarkResponse = goldenKnight.unmarkTask(0);
        assertTrue(unmarkResponse.contains("marked this task as not done"));
        assertTrue(unmarkResponse.contains("[T][ ] Finish homework"));
    }

    @Test
    void deleteTask_shouldWork() {
        goldenKnight.addTodo("Finish homework");

        String deleteResponse = goldenKnight.deleteTask(0);
        assertTrue(deleteResponse.contains("I've removed this task"));
        assertTrue(deleteResponse.contains("Finish homework"));
    }

    @Test
    void addDeadline_shouldReturnCorrectMessage() {
        String response = goldenKnight.addDeadline("Submit report /by 10/9/2025 2359");
        assertTrue(response.contains("I've added this task"));
        assertTrue(response.contains("Submit report"));
    }

    @Test
    void addEvent_shouldReturnCorrectMessage() {
        String response = goldenKnight.addEvent("Project meeting /from 6/9/2025 1000 /to 6/9/2025 1100");
        assertTrue(response.contains("I've added this task"));
        assertTrue(response.contains("Project meeting"));
    }

    @Test
    void findTasks_shouldReturnMatchingTasks() {
        goldenKnight.addTodo("Finish homework");
        goldenKnight.addTodo("Read book");

        String response = goldenKnight.findTasks("homework");
        assertTrue(response.contains("matching tasks"));
        assertTrue(response.contains("Finish homework"));
        assertFalse(response.contains("Read book"));
    }

    @Test
    void invalidMarkIndex_shouldReturnError() {
        goldenKnight.addTodo("Finish homework");
        String response = goldenKnight.markTask(5); // invalid index
        assertTrue(response.startsWith("Error:"));
    }

    @Test
    void invalidUnmarkIndex_shouldReturnError() {
        goldenKnight.addTodo("Finish homework");
        String response = goldenKnight.unmarkTask(5); // invalid index
        assertTrue(response.startsWith("Error:"));
    }

    @Test
    void invalidDeleteIndex_shouldReturnError() {
        goldenKnight.addTodo("Finish homework");
        String response = goldenKnight.deleteTask(5); // invalid index
        assertTrue(response.startsWith("Error:"));
    }
}
