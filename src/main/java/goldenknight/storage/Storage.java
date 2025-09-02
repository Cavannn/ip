package goldenknight.storage;

import goldenknight.task.Deadline;
import goldenknight.task.Event;
import goldenknight.task.Task;
import goldenknight.task.Todo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

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

        try {
            if (!file.exists()) {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                return tasks;
            }

            Scanner sc = new Scanner(file);

            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    try {
                        String[] parts = line.split(" \\| ");
                        switch (parts[0]) {
                            case "T":
                                Todo t = new Todo(parts[2]);
                                if ("1".equals(parts[1])) {
                                    t.markAsDone();
                                }
                                tasks.add(t);
                                break;
                            case "D":
                                tasks.add(Deadline.fromFileFormat(parts));
                                break;
                            case "E":
                                tasks.add(Event.fromFileFormat(parts));
                                break;
                            default:
                                System.out.println("⚠ Unknown task type: " + parts[0]);
                        }
                    } catch (Exception e) {
                        System.out.println("⚠ Skipping corrupted line: " + line);
                    }
                }
            }

            sc.close();
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
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
        try {
            File file = new File(this.filePath);
            if (file.getParentFile() != null) {
                file.getParentFile().mkdirs();
            }

            FileWriter fw = new FileWriter(file);
            for (Task t : tasks) {
                fw.write(t.toFileFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
