import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime byDateTime;
    public static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    public static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");

    public Deadline(String description, String by) {
        super(TaskType.DEADLINE, description);
        this.byDateTime = LocalDateTime.parse(by, INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + byDateTime.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toFileFormat() {
        return "D | " + (isDone ? "1" : "0") + " | " + description + " | " + byDateTime.format(INPUT_FORMAT);
    }

    public static Deadline fromFileFormat(String[] parts) {
        Deadline d = new Deadline(parts[2], parts[3]);
        if (parts[1].equals("1")) d.markAsDone();
        return d;
    }
}
