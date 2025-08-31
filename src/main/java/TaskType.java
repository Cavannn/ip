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
        switch (code) {
            case "T" -> {
                return TODO;
            }
            case "D" -> {
                return DEADLINE;
            }
            case "E" -> {
                return EVENT;
            }
            default -> throw new IllegalArgumentException("Invalid task type code: " + code);
        }
    }
}