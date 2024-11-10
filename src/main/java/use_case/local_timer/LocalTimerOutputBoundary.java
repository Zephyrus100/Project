package use_case.local_timer;

/**
 * Output boundary for timer operations.
 */
public interface LocalTimerOutputBoundary {
    /**
     * Prepares the success view for a timer operation.
     * @param timerOutputData the output data containing timer operation results
     */
    void prepareSuccessView(LocalTimerOutputData timerOutputData);

    /**
     * Prepares the fail view for a timer operation.
     * @param error the error message
     */
    void prepareFailView(String error);

    // TODO: Switch to which view next?
    // void switchToLoginView();
}
