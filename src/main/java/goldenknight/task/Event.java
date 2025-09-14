package goldenknight.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task that spans a specific period of time.
 * An {@code Event} has a description, a start time, and an end time.
 */
public class Event extends Task {
    public static final DateTimeFormatter INPUT_FORMAT = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
    public static final DateTimeFormatter OUTPUT_FORMAT = DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");
    private final LocalDateTime fromDateTime;
    private final LocalDateTime toDateTime;

    /**
     * Creates a new {@code Event} task.
     *
     * @param description Description of the event task.
     * @param from The start date and time of the event, given as a string in {@link #INPUT_FORMAT}.
     * @param to The end date and time of the event, given as a string in {@link #INPUT_FORMAT}.
     */
    public Event(String description, String from, String to) {
        super(TaskType.EVENT, description);
        this.fromDateTime = LocalDateTime.parse(from, INPUT_FORMAT);
        this.toDateTime = LocalDateTime.parse(to, INPUT_FORMAT);
    }

    /**
     * Returns the string representation of this event task,
     * including its status icon, description, and formatted start and end times.
     *
     * @return String representation of the event task.
     */
    @Override
    public String toString() {
        return super.toString() + " (from: " + this.fromDateTime.format(OUTPUT_FORMAT)
                + " to: " + this.toDateTime.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns the string representation of this event task
     * in the format used for saving to a file.
     *
     * @return File format string of the event task.
     */
    @Override
    public String toFileFormat() {
        return "E | " + (this.isDone ? "1" : "0") + " | " + this.description
                + " | " + this.fromDateTime.format(INPUT_FORMAT)
                + " | " + this.toDateTime.format(INPUT_FORMAT);
    }

    /**
     * Creates an {@code Event} object from its file format representation.
     *
     * @param parts An array of strings representing the fields of the event task.
     *              Expected format: {@code ["E", status, description, from, to]}.
     * @return An {@code Event} task created from the given file format parts.
     */
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

    // needed for reminder class
    public LocalDateTime getToDateTime() {
        return this.toDateTime;
    }

}
