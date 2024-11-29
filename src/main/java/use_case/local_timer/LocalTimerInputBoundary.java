package use_case.local_timer;

/**
 * Input boundary for timer operations.
 */
public interface LocalTimerInputBoundary {
    /**
     * Execute the timer operation.
     * @param localTimerInputData the input data containing the operation to perform
     */
    void execute(LocalTimerInputData localTimerInputData);

    long getCurrentElapsedTime();

    // TODO: switch to which view next?
    // void switchToLoginView();
}
