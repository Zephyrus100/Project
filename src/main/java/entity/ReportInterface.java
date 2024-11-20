package entity;

import java.util.List;

/**
 * Representation of our Report in our application.
 */
public interface ReportInterface {

    /**
     * Returns a list of tasks.
     * @return List of tasks.
     */
    List<Task> getTasks();

    /**
     * Add a task to the list of Tasks.
     * @param task a task.
     */
    void addTask(Task task);
}
