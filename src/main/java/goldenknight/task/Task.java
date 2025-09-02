package goldenknight.task;

/**
 * Represents a generic task in the GoldenKnight application.
 * A task has a description, a completion status, and a task type.
 *
 * Subclasses such as {@link Deadline} and {@link Event} extend this class
 * to represent more specific types of tasks.
 */
public class Task {
    /** Description of the task. */
    protected String description;

    /** Whether the task is completed. */
    protected boolean isDone;

    /** The type of task (e.g., ToDo, Deadline, Event). */
    protected TaskType type;

    /**
     * Constructs a new {@code Task} with the given type and description.
     * By default, the task is not done.
     *
     * @param type the type of the task
     * @param description the task description
     */
    public Task(TaskType type, String description) {
        this.type = type;
        this.description = description;
        this.isDone = false;
    }

    /**
     * Marks the task as completed.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not completed.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns the status icon representing completion.
     *
     * @return "X" if the task is done, otherwise " "
     */
    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDone() {
        return this.isDone;
    }

    /**
     * Returns a string representation of the task
     * including its type, status, and description.
     *
     * @return a formatted string of the task
     */
    @Override
    public String toString() {
        return "[" + this.type.getCode() + "][" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Returns the task in file storage format.
     *
     * @return a string formatted for saving the task to a file
     */
    public String toFileFormat() {
        return this.type.getCode() + " | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

    /**
     * Reconstructs a {@code Task} object from its file storage format.
     *
     * @param line the string containing task data in file format
     * @return the reconstructed {@code Task}
     * @throws IllegalArgumentException if the format is invalid
     */
    public static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format: " + line);
        }

        TaskType type = TaskType.fromCode(parts[0]);
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task;
        switch (type) {
        case TODO:
            task = new Todo(description);
            break;
        case DEADLINE:
            // deadline info is in parts[3] if available
            task = new Deadline(description, parts.length > 3 ? parts[3] : "");
            break;
        case EVENT:
            task = new Event(
                    description,
                    parts.length > 3 ? parts[3] : "",
                    parts.length > 4 ? parts[4] : ""
            );
            break;

        default:
            throw new IllegalArgumentException("Unknown task type: " + type);
        }

        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
