package use_case.enter_task;

import entity.LocalTimer;
import entity.TimerSession;
import entity.Task;

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
            Task task = new Task(inputTaskData.getTaskTime(),
                    inputTaskData.getTaskName(),
                    inputTaskData.getDescription()
            );
            taskData.addTask(task);

            final EnterTaskOutputData enterTaskOutputData = new EnterTaskOutputData(
                inputTaskData.getTaskName(),
                inputTaskData.getDescription(),
                inputTaskData.getTaskTime(),
                inputTaskData.getStatus()
            );
            
            System.out.println("Task saved to database: " + inputTaskData.getTaskName());
            presenter.prepareTaskAddedView(enterTaskOutputData);
        } else {
            presenter.prepareTaskNotAddedView("Task could not be added! Try again!");
        }
    }
}
