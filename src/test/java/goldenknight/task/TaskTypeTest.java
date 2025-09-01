package goldenknight.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskTypeTest {

    @Test
    void getCode_returnsCorrectCode() {
        assertEquals("T", TaskType.TODO.getCode());
        assertEquals("D", TaskType.DEADLINE.getCode());
        assertEquals("E", TaskType.EVENT.getCode());
    }

    @Test
    void fromCode_validCode_returnsCorrectEnum() {
        assertEquals(TaskType.TODO, TaskType.fromCode("T"));
        assertEquals(TaskType.DEADLINE, TaskType.fromCode("D"));
        assertEquals(TaskType.EVENT, TaskType.fromCode("E"));
    }

    @Test
    void fromCode_invalidCode_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> TaskType.fromCode("X"));
        assertThrows(IllegalArgumentException.class, () -> TaskType.fromCode(""));
        assertThrows(IllegalArgumentException.class, () -> TaskType.fromCode(null));
    }
}
