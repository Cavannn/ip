package goldenknight.task;

public enum TaskType {
    TODO("T"),
    DEADLINE("D"),
    EVENT("E");

    private final String code;

    private TaskType(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

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
