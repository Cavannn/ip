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

    @Override
    public String toString() {
        return "[" + this.type.getCode() + "][" + this.getStatusIcon() + "] " + this.description;
    }

    public String toFileFormat() {
        return this.type.getCode() + " | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

    /**
     * Factory method to create the correct Task subclass from a file format line.
     *
     * @param line The line from file in the format TYPE | isDone | description | [date info]
     * @return A Task, Todo, Deadline, or Event object.
     * @throws IllegalArgumentException if the line is invalid or the task type code is unrecognized
     */
    public static Task fromFileFormat(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid task format: " + line);
        }

        TaskType type = TaskType.fromCode(parts[0]);

        return switch (type) {
            case TODO -> {
                Todo todo = new Todo(parts[2]);
                if ("1".equals(parts[1])) todo.markAsDone();
                yield todo;
            }
            case DEADLINE -> Deadline.fromFileFormat(parts);
            case EVENT -> Event.fromFileFormat(parts);
        };
    }

    public boolean isDone() {
        return this.isDone;
    }
}
