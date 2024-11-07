package use_case.enter_task;

/**
 * Our interactor for the AddTask use case.
 */
public class EnterTaskInteractor implements EnterTaskInputBoundary {

    private EnterTaskDataInterface taskData;
    private EnterTaskOutputBoundary taskPresenter;

    public EnterTaskInteractor(EnterTaskDataInterface taskData, EnterTaskOutputBoundary outputBoundary) {
        this.taskData = taskData;
        this.taskPresenter = outputBoundary;
    }

    @Override
    public void addTask(EnterTaskInputData inputTaskData) {
        if (inputTaskData == null) {
            taskPresenter.prepareTaskNotAddedView("Error! "
                    + "Task could not be added! Check all required fields were filled.");
        }
        else if (inputTaskData.getStatus().equals("Uncompleted") && inputTaskData.getTaskTime() != 0
                && !inputTaskData.getTaskName().isEmpty()) {
            // Task validation passed; add the task
            taskPresenter.prepareTaskAddedView(new EnterTaskOutputData(
                    inputTaskData.getTaskName(),
                    inputTaskData.getDescription(),
                    inputTaskData.getTaskTime()
            ));
        }
        else {
            // Validation failed; provide error feedback
            taskPresenter.prepareTaskNotAddedView("Error! Invalid task data provided.");
        }
    }

}
