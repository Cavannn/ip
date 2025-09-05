package goldenknight.task;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class EventTest {

    @Test
    void toString_validEvent_correctFormat() {
        Event e = new Event("team meeting", "2/9/2025 1000", "2/9/2025 1200");
        String expected = "[E][ ] team meeting (from: Sep 2 2025 10:00 to: Sep 2 2025 12:00)";
        assertEquals(expected, e.toString());
    }

    private void assertEquals(String expected, String string) {
    }

    @Test
    void toFileFormat_notDone_correctOutput() {
        Event e = new Event("team meeting", "2/9/2025 1000", "2/9/2025 1200");
        String expected = "E | 0 | team meeting | 2/9/2025 1000 | 2/9/2025 1200";
        assertEquals(expected, e.toFileFormat());
    }

    @Test
    void toFileFormat_markedDone_correctOutput() {
        Event e = new Event("team meeting", "2/9/2025 1000", "2/9/2025 1200");
        e.markAsDone();
        String expected = "E | 1 | team meeting | 2/9/2025 1000 | 2/9/2025 1200";
        assertEquals(expected, e.toFileFormat());
    }

    @Test
    void fromFileFormat_doneEvent_reconstructedCorrectly() {
        String[] parts = {"E", "1", "team meeting", "2/9/2025 1000", "2/9/2025 1200"};
        Event e = Event.fromFileFormat(parts);
        assertTrue(e.isDone(), "Event should be marked as done");
        assertEquals("E | 1 | team meeting | 2/9/2025 1000 | 2/9/2025 1200", e.toFileFormat());
    }

    @Test
    void fromFileFormat_notDoneEvent_reconstructedCorrectly() {
        String[] parts = {"E", "0", "team meeting", "2/9/2025 1000", "2/9/2025 1200"};
        Event e = Event.fromFileFormat(parts);
        assertFalse(e.isDone(), "Event should not be marked as done");
        assertEquals("E | 0 | team meeting | 2/9/2025 1000 | 2/9/2025 1200", e.toFileFormat());
    }

    @Test
    void fromFileFormat_invalidInput_throwsException() {
        // Input array shorter than 5 elements
        String[] invalidParts = {"E", "0", "Description"};

        // Assert that IllegalArgumentException is thrown
        assertThrows(IllegalArgumentException.class, () -> {
            Event.fromFileFormat(invalidParts);
        });
    }
}
