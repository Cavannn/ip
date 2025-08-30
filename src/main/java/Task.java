public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type;

    public Task(TaskType type, String description) {
        this.type = type;
        this.description = description;
        this.isDone = false;
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override
    public String toString() {
        return "[" + type.getCode() + "][" + getStatusIcon() + "] " + description;
    }

    public String toFileFormat() {
        return type.getCode() + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    public static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format: " + line);
        }

        TaskType type = TaskType.fromCode(parts[0]);
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = new Task(type, description);
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
