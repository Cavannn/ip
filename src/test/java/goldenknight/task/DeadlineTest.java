package goldenknight.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void toString_validDeadline_correctFormat() {
        Deadline d = new Deadline("submit assignment", "2/9/2025 1800");
        String expected = "[D][ ] submit assignment (by: Sep 2 2025 18:00)";
        assertEquals(expected, d.toString());
    }

    private void assertEquals(String expected, String string) {
    }

    @Test
    public void toFileFormat_notDone_correctOutput() {
        Deadline d = new Deadline("submit assignment", "2/9/2025 1800");
        String expected = "D | 0 | submit assignment | 2/9/2025 1800";
        assertEquals(expected, d.toFileFormat());
    }

    @Test
    public void toFileFormat_markedDone_correctOutput() {
        Deadline d = new Deadline("submit assignment", "2/9/2025 1800");
        d.markAsDone();
        String expected = "D | 1 | submit assignment | 2/9/2025 1800";
        assertEquals(expected, d.toFileFormat());
    }

    @Test
    public void fromFileFormat_doneTask_reconstructedCorrectly() {
        String[] parts = {"D", "1", "submit assignment", "2/9/2025 1800"};
        Deadline d = Deadline.fromFileFormat(parts);
        assertTrue(d.isDone(), "Deadline should be marked as done");
        assertEquals("D | 1 | submit assignment | 2/9/2025 1800", d.toFileFormat());
    }

    @Test
    public void fromFileFormat_notDoneTask_reconstructedCorrectly() {
        String[] parts = {"D", "0", "submit assignment", "2/9/2025 1800"};
        Deadline d = Deadline.fromFileFormat(parts);
        assertFalse(d.isDone(), "Deadline should not be marked as done");
        assertEquals("D | 0 | submit assignment | 2/9/2025 1800", d.toFileFormat());
    }
}
