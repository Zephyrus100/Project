package use_case.enter_task;

/**
 * Simple representation of entering a task.
 */
public interface EnterTaskInputBoundary {

    /**
     * Set a task to complete.
     * @param inputData the data of the task.
     */
    void addTask(EnterTaskInputData inputData);
}
