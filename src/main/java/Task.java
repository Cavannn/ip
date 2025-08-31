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

    public String toString() {
        String var10000 = this.type.getCode();
        return "[" + var10000 + "][" + this.getStatusIcon() + "] " + this.description;
    }

    public String toFileFormat() {
        String var10000 = this.type.getCode();
        return var10000 + " | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

    public static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format: " + line);
        } else {
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
}