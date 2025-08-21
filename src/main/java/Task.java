public class Task {
    protected String description;
    protected boolean isDone;
    protected TaskType type; // <-- enum added

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
}