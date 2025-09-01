package goldenknight.task;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    private TaskList taskList;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        taskList = new TaskList();
        task1 = new Task(TaskType.TODO, "Read book");
        task2 = new Task(TaskType.TODO, "Write report");
    }

    @Test
    void add_shouldIncreaseSize() {
        assertEquals(0, taskList.size());
        taskList.add(task1);
        assertEquals(1, taskList.size());
        taskList.add(task2);
        assertEquals(2, taskList.size());
    }

    @Test
    void get_shouldReturnCorrectTask() {
        taskList.add(task1);
        taskList.add(task2);
        assertEquals(task1, taskList.get(0));
        assertEquals(task2, taskList.get(1));
    }

    @Test
    void delete_shouldRemoveAndReturnTask() {
        taskList.add(task1);
        taskList.add(task2);
        Task removed = taskList.delete(0);
        assertEquals(task1, removed);
        assertEquals(1, taskList.size());
        assertEquals(task2, taskList.get(0));
    }

    @Test
    void getAll_shouldReturnAllTasks() {
        taskList.add(task1);
        taskList.add(task2);
        ArrayList<Task> allTasks = taskList.getAll();
        assertEquals(2, allTasks.size());
        assertTrue(allTasks.contains(task1));
        assertTrue(allTasks.contains(task2));
    }

    @Test
    void size_shouldReflectNumberOfTasks() {
        assertEquals(0, taskList.size());
        taskList.add(task1);
        assertEquals(1, taskList.size());
        taskList.add(task2);
        assertEquals(2, taskList.size());
        taskList.delete(1);
        assertEquals(1, taskList.size());
    }
}
