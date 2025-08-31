import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;
    public static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    public static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");

    public Event(String description, String from, String to) {
        super(TaskType.EVENT, description);
        this.fromDateTime = LocalDateTime.parse(from, INPUT_FORMAT);
        this.toDateTime = LocalDateTime.parse(to, INPUT_FORMAT);
    }

    public String toString() {
        String var10000 = super.toString();
        return var10000 + " (from: " + this.fromDateTime.format(OUTPUT_FORMAT) + " to: " + this.toDateTime.format(OUTPUT_FORMAT) + ")";
    }

    public String toFileFormat() {
        String var10000 = this.isDone ? "1" : "0";
        return "E | " + var10000 + " | " + this.description + " | " + this.fromDateTime.format(INPUT_FORMAT) + " | " + this.toDateTime.format(INPUT_FORMAT);
    }

    public static Event fromFileFormat(String[] parts) {
        Event e = new Event(parts[2], parts[3], parts[4]);
        if (parts[1].equals("1")) {
            e.markAsDone();
        }

        return e;
    }
}