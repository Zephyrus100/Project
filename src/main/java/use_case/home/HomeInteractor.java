package use_case.home;

/**
 * The home Interactor.
 */
public class HomeInteractor implements HomeInputBoundary {
    private final HomeOutputBoundary homeOutputBoundary;
    public HomeInteractor(HomeOutputBoundary homeOutputBoundary) {
        this.homeOutputBoundary = homeOutputBoundary;
    }

    @Override
    public void switchToCurrTasksView() {
        homeOutputBoundary.switchToCurrTasksView();

    }

    @Override
    public void switchToNewTaskView() {
        homeOutputBoundary.switchToNewTaskView();

    }
}
