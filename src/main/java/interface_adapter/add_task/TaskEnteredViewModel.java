package interface_adapter.add_task;

import interface_adapter.ViewModel;

/**
 * The view model for the EnterTask view.
 */
public class TaskEnteredViewModel extends ViewModel<TaskEnteredState> {
    public TaskEnteredViewModel() {
        super("task entered");
        super.setState(new TaskEnteredState());
    }

    public void setTaskDetails(String taskTitle, String taskDescription, double taskTime) {
        TaskEnteredState currentState = getState();
        if (currentState == null) {
            currentState = new TaskEnteredState();
        }
        currentState.setTaskName(taskTitle);
        currentState.setTaskDescription(taskDescription);
        currentState.setTaskTime(taskTime);
//        currentState.setProgress(0.0);

        setState(currentState);
        firePropertyChanged();
    }

    public void updateProgress(double progress) {
        TaskEnteredState currentState = getState();
        currentState.setProgress(progress);

        setState(currentState);
        firePropertyChanged();
    }
}
