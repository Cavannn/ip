package goldenknight.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private final LocalDateTime fromDateTime;
    private final LocalDateTime toDateTime;
    public static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    public static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");

    public Event(String description, String from, String to) {
        super(TaskType.EVENT, description);
        this.fromDateTime = LocalDateTime.parse(from, INPUT_FORMAT);
        this.toDateTime = LocalDateTime.parse(to, INPUT_FORMAT);
    }

    @Override
    public String toString() {
        return super.toString() + " (from: " + this.fromDateTime.format(OUTPUT_FORMAT)
                + " to: " + this.toDateTime.format(OUTPUT_FORMAT) + ")";
    }

    @Override
    public String toFileFormat() {
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description
                + " | " + this.fromDateTime.format(INPUT_FORMAT)
                + " | " + this.toDateTime.format(INPUT_FORMAT);
    }

    public static Event fromFileFormat(String[] parts) {
        if (parts.length < 5) {
            throw new IllegalArgumentException("Invalid file format for Event task");
        }

        Event e = new Event(parts[2], parts[3], parts[4]);
        if ("1".equals(parts[1])) {
            e.markAsDone();
        }

        return e;
    }
}
