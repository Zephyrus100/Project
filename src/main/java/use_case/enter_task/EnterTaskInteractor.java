package use_case.enter_task;

public class EnterTaskInteractor implements EnterTaskInputBoundary {

    private EnterTaskDataInterface taskData;
    private EnterTaskOutputBoundary presenter;

    public EnterTaskInteractor(EnterTaskDataInterface taskData, EnterTaskOutputBoundary presenter) {
        this.taskData = taskData;
        this.presenter = presenter;
    }

    @Override
    public void addTask(EnterTaskInputData inputTaskData) {
        if (inputTaskData.getTaskTime() != 0 && !inputTaskData.getTaskName().isEmpty()) {
            presenter.prepareTaskAddedView(new EnterTaskOutputData(
                    inputTaskData.getTaskName(),
                    inputTaskData.getDescription(),
                    inputTaskData.getTaskTime(),
                    inputTaskData.getStatus()
            ));
        }
        else {
            presenter.prepareTaskNotAddedView();
        }
    }
}
