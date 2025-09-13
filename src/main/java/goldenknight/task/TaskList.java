package goldenknight.task;

import java.util.ArrayList;

/**
 * Represents a list of {@link Task} objects in the GoldenKnight application.
 * Provides methods to add, delete, access, and retrieve tasks.
 */
public class TaskList {
    /** The internal list storing the tasks. */
    private ArrayList<Task> tasks;

    /**
     * Constructs a {@code TaskList} with an existing list of tasks.
     *
     * @param tasks the list of tasks to initialize with
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Task list provided to constructor should not be null";
        this.tasks = tasks;
    }

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
        assert this.tasks != null : "Tasks list should be initialized";
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        assert task != null : "Task to add should not be null";
        tasks.add(task);
        assert tasks.contains(task) : "Task should be added to the list";
    }

    /**
     * Deletes the task at the specified index from the list.
     *
     * @param index the index of the task to delete (0-based)
     * @return the deleted task
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task delete(int index) {
        assert index >= 0 && index < tasks.size() : "Index for delete should be within bounds";
        Task removed = tasks.remove(index);
        assert removed != null : "Deleted task should not be null";
        return removed;
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index the index of the task to retrieve (0-based)
     * @return the task at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "Index for get should be within bounds";
        Task retrieved = tasks.get(index);
        assert retrieved != null : "Retrieved task should not be null";
        return retrieved;
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        assert tasks != null : "Tasks list should not be null when checking size";
        return tasks.size();
    }

    /**
     * Returns the entire list of tasks.
     *
     * @return an {@link ArrayList} containing all tasks
     */
    public ArrayList<Task> getAll() {
        assert tasks != null : "Tasks list should not be null when getting all tasks";
        return new ArrayList<>(tasks);
    }

    /**
     * Searches for tasks that contain the specified keyword in their description.
     *
     * @param keyword the keyword to search for in the task descriptions
     * @return an {@code ArrayList<Task>} containing all tasks whose descriptions
     *         include the specified keyword
     */
    public ArrayList<Task> find(String keyword) {
        assert keyword != null && !keyword.isBlank() : "Keyword for find should not be null or blank";
        ArrayList<Task> results = new ArrayList<>();
        for (Task task : tasks) {
            assert task != null : "Task in list should not be null";
            if (task.getDescription().contains(keyword)) {
                results.add(task);
            }
        }
        return results;
    }
}
