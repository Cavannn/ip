package goldenknight.task;

/**
 * Represents a {@link Task} of type {@link TaskType#TODO}.
 * A to-do task is a simple task without any associated deadline or event time.
 */
public class Todo extends Task {

    /**
     * Constructs a new {@code Todo} task with the given description.
     *
     * @param description the details of the to-do task
     */
    public Todo(String description) {
        super(TaskType.TODO, description);
    }
}
