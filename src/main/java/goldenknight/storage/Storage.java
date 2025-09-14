package goldenknight.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import goldenknight.task.Deadline;
import goldenknight.task.Event;
import goldenknight.task.Task;
import goldenknight.task.Todo;

/**
 * Handles loading and saving of tasks to a file.
 *
 * <p>This class is responsible for reading tasks from a file into a list
 * and writing tasks from a list back to the file. It ensures that the file
 * exists and creates it if necessary.</p>
 */
public class Storage {

    /** Path to the file used for storing tasks. */
    private final String filePath;

    /**
     * Constructs a Storage object with the specified file path.
     *
     * @param filePath The path to the file where tasks are stored.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file into an ArrayList.
     *
     * <p>If the file does not exist, it will be created. Corrupted lines
     * or unknown task types are skipped with a warning message.</p>
     *
     * @return An ArrayList containing all valid tasks loaded from the file.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(this.filePath);

        // Create file if it doesn't exist
        try {
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                return tasks; // early return avoids deep nesting
            }
        } catch (IOException e) {
            System.err.println("Error creating file: " + e.getMessage());
            return tasks;
        }

        // Read and parse tasks from file
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                try {
                    Task task = parseLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                } catch (Exception e) {
                    System.err.println("⚠ Skipping corrupted line: " + line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

    /**
     * Parses a single line from the task file into a Task object.
     *
     * @param line the line to parse
     * @return a Task object, or null if the type is unknown
     */
    private Task parseLine(String line) {
        String[] parts = line.split(" \\| ");
        switch (parts[0]) {
        case "T":
            Todo t = new Todo(parts[2]);
            if ("1".equals(parts[1])) {
                t.markAsDone();
            }
            return t;
        case "D":
            return Deadline.fromFileFormat(parts);
        case "E":
            return Event.fromFileFormat(parts);
        default:
            System.err.println("⚠ Unknown task type: " + parts[0]);
            return null;
        }
    }


    /**
     * Saves the given list of tasks to the file.
     *
     * <p>If the file or its parent directories do not exist, they are created.
     * Each task is written in its file format on a new line.</p>
     *
     * @param tasks The list of tasks to be saved.
     */
    public void save(ArrayList<Task> tasks) {
        File file = new File(this.filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }

        try (FileWriter fw = new FileWriter(file)) {
            for (Task t : tasks) {
                fw.write(t.toFileFormat() + System.lineSeparator());
            }
        } catch (IOException e) {
            System.err.println("Error saving tasks: " + e.getMessage());
        }
    }
}
