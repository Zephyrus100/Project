package interface_adapter.local_timer;

import use_case.local_timer.LocalTimerInputBoundary;
import use_case.local_timer.LocalTimerInputData;

/**
 * Controller for the Local Timer Use Case.
 */
public class LocalTimerController {

    private final LocalTimerInputBoundary timerUseCaseInteractor;

    public LocalTimerController(LocalTimerInputBoundary timerUseCaseInteractor) {
        this.timerUseCaseInteractor = timerUseCaseInteractor;
    }

    /**
     * Executes timer operations (start, pause, resume, stop, reset).
     * @param operation the timer operation to perform
     */
    public void execute(String operation) {
        final LocalTimerInputData timerInputData = new LocalTimerInputData(operation);
        timerUseCaseInteractor.execute(timerInputData);
    }

    public void saveTimerSession() {
        System.out.println("Controller: Saving session");
        LocalTimerInputData timerInputData = new LocalTimerInputData("save");
        timerInputData.setElapsedTime(timerUseCaseInteractor.getCurrentElapsedTime());
        timerUseCaseInteractor.execute(timerInputData);
    }

    // TODO: Write the switch view method.
    // public void switchToLoginView() {
    //     timerUseCaseInteractor.switchToLoginView();
    // }
}
