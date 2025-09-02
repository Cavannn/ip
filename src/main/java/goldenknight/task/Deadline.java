package goldenknight.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline. A {@code Deadline} has a description
 * and must be completed by a specific {@link LocalDateTime}.
 */
public class Deadline extends Task {
    /** The date and time by which the task must be completed. */
    private LocalDateTime byDateTime;

    /** Input format used when parsing user-provided date and time strings. */
    public static final DateTimeFormatter INPUT_FORMAT =
            DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

    /** Output format used when displaying the deadline in a user-friendly format. */
    public static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM d yyyy HH:mm");

    /**
     * Creates a new {@code Deadline} task.
     *
     * @param description Description of the deadline task.
     * @param by The deadline date and time, given as a string in {@link #INPUT_FORMAT}.
     */
    public Deadline(String description, String by) {
        super(TaskType.DEADLINE, description);
        this.byDateTime = LocalDateTime.parse(by, INPUT_FORMAT);
    }

    /**
     * Returns the string representation of this deadline task,
     * including its status icon, description, and formatted deadline.
     *
     * @return String representation of the deadline task.
     */
    @Override
    public String toString() {
        return super.toString() + " (by: " + this.byDateTime.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Returns the string representation of this deadline task
     * in the format used for saving to a file.
     *
     * @return File format string of the deadline task.
     */
    @Override
    public String toFileFormat() {
        return "D | " + (this.isDone ? "1" : "0") + " | " + this.description + " | " + this.byDateTime.format(INPUT_FORMAT);
    }

    /**
     * Creates a {@code Deadline} object from its file format representation.
     *
     * @param parts An array of strings representing the fields of the deadline task.
     *              Expected format: {@code ["D", status, description, by]}.
     * @return A {@code Deadline} task created from the given file format parts.
     */
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
