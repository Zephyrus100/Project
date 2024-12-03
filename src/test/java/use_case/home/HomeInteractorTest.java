package use_case.home;

import org.junit.jupiter.api.Test;

class HomeInteractorTest {
    @Test
    void homeTest() {
        HomeOutputBoundary successPresenter = new HomeOutputBoundary() {
            @Override
            public void switchToCurrTasksView() { //These are expected
            }

            @Override
            public void switchToNewTaskView() { //These are expected
            }
        };
        HomeInputBoundary interactor = new HomeInteractor(successPresenter);
        interactor.switchToCurrTasksView();
        interactor.switchToNewTaskView();
    }
}
