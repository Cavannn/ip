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

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

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

            try (Scanner sc = new Scanner(file)) {
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
                                System.err.println("⚠ Unknown task type: " + parts[0]);
                            }
                        } catch (Exception e) {
                            System.err.println("⚠ Skipping corrupted line: " + line);
                        }
                    }
                }
            }

        } catch (IOException e) {
            System.err.println("Error loading tasks: " + e.getMessage());
        }

        return tasks;
    }

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
