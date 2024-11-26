package interface_adapter.home;

import interface_adapter.add_task.EnterTaskState;
import interface_adapter.add_task.EnterTaskViewModel;
import interface_adapter.local_timer.LocalTimerState;
import interface_adapter.local_timer.LocalTimerViewModel;
import use_case.home.HomeOutputBoundary;

import interface_adapter.ViewManagerModel;
import use_case.home.HomeOutputBoundary;

public class HomePresenter implements HomeOutputBoundary {

    private final HomeViewModel homeViewModel;
    private final ViewManagerModel viewManagerModel;
    private final EnterTaskViewModel enterTaskViewModel;
//    private final LocalTimerViewModel localTimerViewModel;

    public HomePresenter(ViewManagerModel viewManagerModel,
                         HomeViewModel homeViewModel,
                         EnterTaskViewModel enterTaskViewModel) {
        this.homeViewModel = homeViewModel;
        this.enterTaskViewModel = enterTaskViewModel;
        this.viewManagerModel = viewManagerModel;

    }
    @Override
    public void switchToCurrTasksView() {

    }

    @Override
    public void switchToNewTaskView() {
        EnterTaskState enterTaskState = new EnterTaskState();
        enterTaskViewModel.setState(enterTaskState);
        enterTaskViewModel.firePropertyChanged();

        // Then, tell the ViewManager to switch views
        viewManagerModel.setState("Enter Task");
        viewManagerModel.firePropertyChanged();

    }

//    @Override
//    public void switchToTimerView() {
//        LocalTimerState localTimerState = new LocalTimerState();
//        localTimerViewModel.setState(localTimerState);
//        localTimerViewModel.firePropertyChanged();
//
//        viewManagerModel.setState("Local Timer");
//        viewManagerModel.firePropertyChanged();
//
//    }
}
