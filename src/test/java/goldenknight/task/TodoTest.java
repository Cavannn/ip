package goldenknight.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    @Test
    void constructor_shouldCreateTodoWithCorrectDescription() {
        Todo todo = new Todo("Finish homework");
        assertEquals("[T][ ] Finish homework", todo.toString());
        assertFalse(todo.isDone(), "New Todo should not be marked as done");
    }

    @Test
    void toString_notDone_returnsCorrectFormat() {
        Todo todo = new Todo("Finish homework");
        assertEquals("[T][ ] Finish homework", todo.toString());
    }

    @Test
    void toString_done_returnsCorrectFormat() {
        Todo todo = new Todo("Finish homework");
        todo.markAsDone();
        assertEquals("[T][X] Finish homework", todo.toString());
    }

    @Test
    void toFileFormat_notDone_returnsCorrectFormat() {
        Todo todo = new Todo("Finish homework");
        assertEquals("T | 0 | Finish homework", todo.toFileFormat());
    }

    @Test
    void toFileFormat_done_returnsCorrectFormat() {
        Todo todo = new Todo("Finish homework");
        todo.markAsDone();
        assertEquals("T | 1 | Finish homework", todo.toFileFormat());
    }
}
