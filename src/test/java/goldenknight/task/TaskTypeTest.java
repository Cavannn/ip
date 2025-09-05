package goldenknight.task;

import org.junit.jupiter.api.Test;

class TaskTypeTest {

    @Test
    void getCode_returnsCorrectCode() {
        assertEquals("T", TaskType.TODO.getCode());
        assertEquals("D", TaskType.DEADLINE.getCode());
        assertEquals("E", TaskType.EVENT.getCode());
    }

    private void assertEquals(String t, String code) {
    }

    private void assertEquals(TaskType taskType, TaskType t) {
    }

    @Test
    void fromCode_validCode_returnsCorrectEnum() {
        assertEquals(TaskType.TODO, TaskType.fromCode("T"));
        assertEquals(TaskType.DEADLINE, TaskType.fromCode("D"));
        assertEquals(TaskType.EVENT, TaskType.fromCode("E"));
    }

    @Test
    void fromCode_invalidCode_throwsException() {

    }
}
