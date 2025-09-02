package goldenknight.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private final LocalDateTime byDateTime;
    public static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    public static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");

    public Deadline(String description, String by) {
        super(TaskType.DEADLINE, description);
        this.byDateTime = LocalDateTime.parse(by, INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + this.byDateTime.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.byDateTime.format(INPUT_FORMAT);
    }

    public static Deadline fromFileFormat(String[] parts) {
        if (parts.length < 4) {
            throw new IllegalArgumentException("Invalid file format for Deadline task");
        }

        Deadline d = new Deadline(parts[2], parts[3]);
        if ("1".equals(parts[1])) {
            d.markAsDone();
        }

        return d;
    }
}
