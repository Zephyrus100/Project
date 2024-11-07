package interface_adapter.add_task;

import use_case.enter_task.EnterTaskInputBoundary;
import use_case.enter_task.EnterTaskInputData;

/**
 * Controller to handle changes from the view.
 */
public class EnterTaskController {

    private EnterTaskInputBoundary enterTaskInteractor;

    public EnterTaskController(EnterTaskInputBoundary enterTaskInteractor) {
        this.enterTaskInteractor = enterTaskInteractor;
    }

    public void addTask(EnterTaskInputData enterTaskInputData) {
        enterTaskInteractor.addTask(enterTaskInputData);
    }

}
