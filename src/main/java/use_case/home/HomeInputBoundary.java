package use_case.home;

/**
 * Input Boundary for actions related to the home screen.
 */
public interface HomeInputBoundary {
    /**
     * Executes the switch to current tasks view use case.
     */
    void switchToCurrTasksView();

    /**
     * Executes the switch to new task view use case.
     */
    void switchToNewTaskView();

//    /**
//     * Executes the switch to the timer use case.
//     */
//    void switchToTimerView();
}
