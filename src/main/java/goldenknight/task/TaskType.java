package goldenknight.task;

/**
 * Represents the type of a task in the GoldenKnight application.
 * Each task type is associated with a short string code used for
 * file storage and display.
 */
public enum TaskType {
    /** A task without a specific deadline or event (to-do). */
    TODO("T"),

    /** A task with a deadline. */
    DEADLINE("D"),

    /** A task that occurs at a specific time (event). */
    EVENT("E");

    /** The short string code representing the task type. */
    private final String code;

    /**
     * Constructs a {@code TaskType} with the specified string code.
     *
     * @param code the code representing the task type
     */
    private TaskType(String code) {
        this.code = code;
    }

    /**
     * Returns the string code associated with this task type.
     *
     * @return the task type code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Returns the {@code TaskType} corresponding to the given code.
     *
     * @param code the string code of the task type (e.g., "T", "D", "E")
     * @return the matching {@code TaskType}
     * @throws IllegalArgumentException if the code is {@code null} or invalid
     */
    public static TaskType fromCode(String code) {
        if (code == null) {
            throw new IllegalArgumentException("Task type code cannot be null");
        }

        return switch (code) {
            case "T" -> TODO;
            case "D" -> DEADLINE;
            case "E" -> EVENT;
            default -> throw new IllegalArgumentException("Invalid task type code: " + code);
        };
    }
}
