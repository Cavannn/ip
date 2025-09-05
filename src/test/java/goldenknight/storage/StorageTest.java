package goldenknight.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import goldenknight.task.Deadline;
import goldenknight.task.Event;
import goldenknight.task.Task;
import goldenknight.task.Todo;

class StorageTest {

    private static final String TEST_FILE = "test_tasks.txt";
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage(TEST_FILE);
    }

    @AfterEach
    void tearDown() {
        // Delete the test file after each test
        File file = new File(TEST_FILE);
        if (file.exists()) {
            file.delete();
        }
    }

    @Test
    void saveAndLoad_multipleTasks_shouldReturnSameTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Todo 1"));
        tasks.add(new Deadline("Deadline 1", "2/9/2025 1800"));
        tasks.add(new Event("Event 1", "2/9/2025 1000", "2/9/2025 1200"));

        // mark one as done
        tasks.get(0).markAsDone();

        storage.save(tasks);

        ArrayList<Task> loadedTasks = storage.load();

        assertEquals(tasks.size(), loadedTasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i).toFileFormat(), loadedTasks.get(i).toFileFormat());
        }
    }

    @Test
    void load_emptyFile_shouldReturnEmptyList() throws Exception {
        // Create an empty file
        new File(TEST_FILE).createNewFile();

        ArrayList<Task> loadedTasks = storage.load();
        assertTrue(loadedTasks.isEmpty());
    }

    @Test
    void load_fileWithCorruptedLines_shouldSkipCorruptedLines() throws Exception {
        FileWriter fw = new FileWriter(TEST_FILE);
        fw.write("T | 0 | Todo 1\n");
        fw.write("INVALID LINE\n");
        fw.write("D | 1 | Deadline 1 | 2/9/2025 1800\n");
        fw.close();

        ArrayList<Task> loadedTasks = storage.load();
        assertEquals(2, loadedTasks.size());
        assertEquals("T | 0 | Todo 1", loadedTasks.get(0).toFileFormat());
        assertEquals("D | 1 | Deadline 1 | 2/9/2025 1800", loadedTasks.get(1).toFileFormat());
    }
}
