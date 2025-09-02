package goldenknight.task;

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
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return this.isDone ? "X" : " ";
    }

    public String getDescription() {
        return this.description;
    }

    public boolean isDone() {
        return this.isDone;
    }

    @Override
    public String toString() {
        return "[" + this.type.getCode() + "][" + this.getStatusIcon() + "] " + this.description;
    }

    public String toFileFormat() {
        return this.type.getCode() + " | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

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
