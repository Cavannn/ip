package goldenknight.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void markAsDone_shouldSetIsDoneToTrue() {
        Task task = new Task(TaskType.TODO, "Read book");
        assertFalse(task.isDone, "Task should initially be not done");
        task.markAsDone();
        assertTrue(task.isDone, "Task should be marked as done");
    }

    @Test
    void markAsNotDone_shouldSetIsDoneToFalse() {
        Task task = new Task(TaskType.TODO, "Read book");
        task.markAsDone();
        assertTrue(task.isDone);
        task.markAsNotDone();
        assertFalse(task.isDone, "Task should be marked as not done");
    }

    @Test
    void getStatusIcon_returnsCorrectSymbol() {
        Task task = new Task(TaskType.TODO, "Read book");
        assertEquals(" ", task.getStatusIcon(), "Default task should not be done");
        task.markAsDone();
        assertEquals("X", task.getStatusIcon(), "Task should return 'X' when done");
    }

    @Test
    void toString_returnsCorrectFormat() {
        Task task = new Task(TaskType.TODO, "Read book");
        String expected = "[T][ ] Read book";
        assertEquals(expected, task.toString(), "toString should match expected format");

        task.markAsDone();
        String expectedDone = "[T][X] Read book";
        assertEquals(expectedDone, task.toString(), "toString should reflect done status");
    }

    @Test
    void toFileFormat_returnsCorrectFormat() {
        Task task = new Task(TaskType.TODO, "Read book");
        String expected = "T | 0 | Read book";
        assertEquals(expected, task.toFileFormat(), "File format should match expected when not done");

        task.markAsDone();
        String expectedDone = "T | 1 | Read book";
        assertEquals(expectedDone, task.toFileFormat(), "File format should reflect done status");
    }

    @Test
    void fromFileFormat_validInput_returnsCorrectTask() {
        String line = "T | 1 | Read book";
        Task task = Task.fromFileFormat(line);

        assertEquals(TaskType.TODO, task.type);
        assertEquals("Read book", task.description);
        assertTrue(task.isDone, "Task should be marked as done from file format");
    }

    @Test
    void fromFileFormat_invalidInput_throwsException() {
        String invalidLine = "invalid format line";
        assertThrows(IllegalArgumentException.class,
                () -> Task.fromFileFormat(invalidLine),
                "Invalid input should throw IllegalArgumentException");
    }
}
