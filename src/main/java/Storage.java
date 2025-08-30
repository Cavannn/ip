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
        File file = new File(filePath);

        try {
            if (!file.exists()) {
                // Create folder if missing
                file.getParentFile().mkdirs();
                file.createNewFile();
                return tasks;
            }

            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                String line = sc.nextLine().trim();
                if (!line.isEmpty()) {
                    try {
                        tasks.add(Task.fromFileFormat(line));
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

    public void save(ArrayList<Task> tasks) {
        try {
            FileWriter fw = new FileWriter(filePath);
            for (Task t : tasks) {
                fw.write(t.toFileFormat() + System.lineSeparator());
            }
            fw.close();
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
