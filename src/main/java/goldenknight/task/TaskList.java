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
        this.tasks = tasks;
    }

    /**
     * Constructs an empty {@code TaskList}.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Adds a task to the list.
     *
     * @param task the task to add
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes the task at the specified index from the list.
     *
     * @param index the index of the task to delete (0-based)
     * @return the deleted task
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task delete(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task index out of range");
        }
        return tasks.remove(index);
    }

    /**
     * Retrieves the task at the specified index.
     *
     * @param index the index of the task to retrieve (0-based)
     * @return the task at the given index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public Task get(int index) {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task index out of range");
        }
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     *
     * @return the size of the task list
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the entire list of tasks.
     *
     * @return an {@link ArrayList} containing all tasks
     */
    public ArrayList<Task> getAll() {
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
        ArrayList<Task> results = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                results.add(task);
            }
        }
        return results;
    }
}
