package use_case.enter_task;

/**
 * Representation of the output from a task.
 */
public interface EnterTaskOutputBoundary {

    /**
     * Prepare successful view of the task, correctly added.
     * @param addTaskData the data that will be passed onto the presenter.
     * @return True if the task was successfully added.
     */
    boolean prepareTaskAddedView(EnterTaskOutputData addTaskData);

    /**
     * Prepare unsuccessful view of the task, not being added.
     */
    void prepareTaskNotAddedView(String error);

}
