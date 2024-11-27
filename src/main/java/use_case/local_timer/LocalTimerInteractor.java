package use_case.local_timer;

import entity.TimerFactory;
import entity.TimerInterface;
import view.LocalTimerView;
import interface_adapter.local_timer.LocalTimerViewModel;

/**
 * The Local Timer Interactor.
 * Handles timer operations following Clean Architecture principles.
 */
public class LocalTimerInteractor implements LocalTimerInputBoundary {

    private final LocalTimerOutputBoundary timerPresenter;
    private final TimerInterface timer;
    private final LocalTimerDataAccessInterface timerDataAccess;

    /**
     * Creates a new LocalTimerInteractor.
     *
     * @param timerPresenter for presenting timer results
     * @param timerFactory for creating timer instances
     * @param timerDataAccess for accessing timer data
     */
    public LocalTimerInteractor(LocalTimerOutputBoundary timerPresenter,
                                TimerFactory timerFactory,
                                LocalTimerDataAccessInterface timerDataAccess) {
        this.timerPresenter = timerPresenter;
        this.timer = timerFactory.create();
        this.timerDataAccess = timerDataAccess;
    }

    /**
     * Executes timer operations and returns results through the presenter.
     *
     * @param timerInputData contains the operation to perform
     */
    @Override
    public void execute(LocalTimerInputData timerInputData) {
        try {
            final String operation = timerInputData.getOperation().toLowerCase();
            processTimerOperation(operation);
        }
        catch (IllegalArgumentException | IllegalStateException ex) {
            timerPresenter.prepareFailView(ex.getMessage());
        }
    }

    /**
     * Processes the timer operation and updates the presenter.
     *
     * @param operation the timer operation to perform
     * @throws IllegalArgumentException if the operation is invalid
     * @throws IllegalStateException if the timer state is invalid for the operation
     */
    private void processTimerOperation(final String operation) {
        String status = "";

        switch (operation.toLowerCase()) {
            case "start":
                if (timer.isRunning()) {
                    throw new IllegalStateException("Timer is already running");
                }
                else {
                    timer.start();
                    timerDataAccess.save(timer);
                    status = "Running";
                }
                break;

            case "pause":
                if (!timer.isRunning()) {
                    throw new IllegalStateException("Timer is not running");
                }
                else {
                    timer.pause();
                    status = "Paused";
                }
                break;

            case "resume":
                if (timer.isRunning()) {
                    throw new IllegalStateException("Timer is already running");
                }
                else {
                    timer.resume();
                    status = "Running";
                }
                break;

            case "stop":
                if (!timer.isRunning() && timer.getElapsedTime() == 0) {
                    throw new IllegalStateException("No active timer to stop");
                }
                else {
                    timer.stop();
                    status = "Stopped";
                }
                break;

            case "reset":
                timer.reset();
                status = "Stopped";
                break;

            case "update":
                status = timer.isRunning() ? "Running" : timer.getElapsedTime() > 0 ? "Paused" : "Stopped";
                break;

            case "save":
                if (timer.canSave()) {
                    long startTimeNanos = System.nanoTime() - timer.getElapsedTime();
                    long endTimeNanos = System.nanoTime();
                    long duration = timer.getElapsedTime();
                    timerDataAccess.saveSession(startTimeNanos, endTimeNanos, duration);
                    
                    LocalTimerOutputData outputData = new LocalTimerOutputData(
                        true,
                        LocalTimerViewModel.TIMER_SAVED,
                        timer.getElapsedTime()
                    );
                    timerPresenter.prepareSuccessView(outputData);
                }
                break;

            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }

        final LocalTimerOutputData outputData = new LocalTimerOutputData(
                true,
                status,
                timer.getElapsedTime()
        );
        timerPresenter.prepareSuccessView(outputData);
    }

    @Override
    public long getCurrentElapsedTime() {
        return timer.getElapsedTime();
    }
}
