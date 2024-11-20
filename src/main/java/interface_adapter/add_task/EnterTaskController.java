package interface_adapter.add_task;

import use_case.enter_task.EnterTaskInputBoundary;
import use_case.enter_task.EnterTaskInputData;

public class EnterTaskController {

    private EnterTaskInputBoundary enterTaskInteractor;

    public EnterTaskController(EnterTaskInputBoundary enterTaskInteractor) {
        this.enterTaskInteractor = enterTaskInteractor;
    }

    public void addTask(String taskName, String description, double taskTime) {
        final EnterTaskInputData enterTaskInputData = new EnterTaskInputData(taskName, description, taskTime);
        enterTaskInteractor.addTask(enterTaskInputData);
    }
}
