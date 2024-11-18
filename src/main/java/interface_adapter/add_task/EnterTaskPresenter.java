package interface_adapter. add_task;

import use_case.enter_task.EnterTaskOutputBoundary;
import use_case.enter_task.EnterTaskOutputData;
import interface_adapter.ViewManagerModel;
import view.TaskEnteredView;

public class EnterTaskPresenter implements EnterTaskOutputBoundary {

    private final EnterTaskState currentState;
    private final EnterTaskViewModel enterTaskViewModel;
    private final ViewManagerModel viewManagerModel;
    private TaskEnteredView taskEnteredView;

    public EnterTaskPresenter(EnterTaskState enterTaskState, EnterTaskViewModel enterTaskViewModel,
                              ViewManagerModel viewManagerModel, TaskEnteredView taskEnteredView) {
        this.currentState = enterTaskState;
        this.enterTaskViewModel = enterTaskViewModel;
        this.viewManagerModel = viewManagerModel;
        this.taskEnteredView = taskEnteredView;
    }

    @Override
    public boolean prepareTaskAddedView(EnterTaskOutputData response) {
        if (response == null) {
            return false;
        }
        this.currentState.setTaskName(response.getTaskTitle());
        this.currentState.setTaskDescription(response.getTaskDescription());
        this.currentState.setTaskTime(response.getTaskTime());
        this.currentState.setTaskStatus(response.getTaskStatus());

        this.viewManagerModel.setState(enterTaskViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

        this.enterTaskViewModel.setState(this.currentState);
        return true;
    }

    @Override
    public void prepareTaskNotAddedView() {

    }

    @Override
    public boolean taskAdded() {
        return currentState.getTaskTime() > 0;
    }
}
