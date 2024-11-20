package interface_adapter. add_task;

import use_case.enter_task.EnterTaskOutputBoundary;
import use_case.enter_task.EnterTaskOutputData;
import interface_adapter.ViewManagerModel;

public class EnterTaskPresenter implements EnterTaskOutputBoundary {

    private final EnterTaskViewModel enterTaskViewModel;
    private final TaskEnteredViewModel taskEnteredViewModel;
    private final ViewManagerModel viewManagerModel;

    public EnterTaskPresenter(EnterTaskViewModel enterTaskViewModel, TaskEnteredViewModel taskEnteredViewModel,
                              ViewManagerModel viewManagerModel) {
        this.enterTaskViewModel = enterTaskViewModel;
        this.taskEnteredViewModel = taskEnteredViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public boolean prepareTaskAddedView(EnterTaskOutputData response) {
        System.out.println("Presenter: prepareTaskAddedView called");
        if (response == null) {
            System.out.println("Presenter: Response is null");
            return false;
        }

        System.out.println("Presenter: Response details:");
        System.out.println("Task Title: " + response.getTaskTitle());
        System.out.println("Task Description: " + response.getTaskDescription());
        System.out.println("Task Time: " + response.getTaskTime());

        final TaskEnteredState taskEnteredState = taskEnteredViewModel.getState();
        taskEnteredState.setTaskName(response.getTaskTitle());
        taskEnteredState.setTaskDescription(response.getTaskDescription());
        taskEnteredState.setTaskTime(response.getTaskTime());

        System.out.println("Presenter: State after setting:");
        System.out.println("State Task Name: " + taskEnteredState.getTaskName());
        System.out.println("State Task Description: " + taskEnteredState.getTaskDescription());
        System.out.println("State Task Time: " + taskEnteredState.getTaskTime());

        taskEnteredViewModel.setState(taskEnteredState);
        taskEnteredViewModel.firePropertyChanged();

        this.viewManagerModel.setState(taskEnteredViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();

        return true;
    }
    @Override
    public void prepareTaskNotAddedView(String error) {
        final EnterTaskState enterTaskState = new EnterTaskState();
        enterTaskState.setEnterTaskError(error);
        enterTaskViewModel.setState(enterTaskState);
        enterTaskViewModel.firePropertyChanged(); // Notify listeners
    }
}
