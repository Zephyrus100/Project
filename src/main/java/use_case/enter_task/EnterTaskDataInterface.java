package use_case.enter_task;

public interface EnterTaskDataInterface {

    /**
     * Adds a task with the given parameters.
     *
     * @param taskName        the name of the task
     * @param taskDescription the description of the task
     * @param taskTime        the time required for the task
     * @param taskStatus      the status of the task (e.g., completed or not)
     */
    void addTask(String taskName, String taskDescription, double taskTime, String taskStatus);

    /**
     * Retrieves the task name based on an index or identifier.
     *
     * @param taskName the name of the task
     * @return the task name if it exists, null otherwise
     */
    String getTaskName(String taskName);

    /**
     * Retrieves the task time for the specified task name.
     *
     * @param taskName the name of the task
     * @return the task time, or -1 if the task doesn't exist
     */
    double getTaskTime(String taskName);

    /**
     * Retrieves the task status for the specified task name.
     *
     * @param taskName the name of the task
     * @return the task status, or null if the task doesn't exist
     */
    String getTaskStatus(String taskName);

    /**
     * Retrieves the task description for the specified task name.
     *
     * @param taskName the name of the task
     * @return the task description, or null if the task doesn't exist
     */
    String getTaskDescription(String taskName);
}
