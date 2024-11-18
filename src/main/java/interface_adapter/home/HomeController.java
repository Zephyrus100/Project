package interface_adapter.home;

import use_case.home.HomeInputBoundary;

/**
 * Controller for the Home Use Case.
 */
public class HomeController {

    private final HomeInputBoundary homeUseCaseInteractor;

    public HomeController(HomeInputBoundary homeUseCaseInteractor) {
        this.homeUseCaseInteractor = homeUseCaseInteractor;
    }

    /**
     * Executes the switch to current tasks use case.
     */
    public void switchtoCurrTasksView() {
        homeUseCaseInteractor.switchToCurrTasksView();
    }

    /**
     * Executes the switch to new task use case.
     */
    public void switchtoNewTaskView() {
        homeUseCaseInteractor.switchToNewTaskView();
    }
}
