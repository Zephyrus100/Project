package use_case.local_timer;

/**
 * Output data for timer operations.
 */
public class LocalTimerOutputData {
    private final boolean useCaseSuccess;
    private final String timerState;
    private final long elapsedTime;

    /**
     * Creates a new LocalTimerOutputData.
     * @param useCaseSuccess whether the timer operation was successful
     * @param timerState the current state of the timer (running, paused, stopped)
     * @param elapsedTime the elapsed time in nanoseconds
     */
    public LocalTimerOutputData(boolean useCaseSuccess, String timerState, long elapsedTime) {
        this.useCaseSuccess = useCaseSuccess;
        this.timerState = timerState;
        this.elapsedTime = elapsedTime;
    }

    /**
     * Gets whether the operation was successful.
     * @return true if the operation was successful
     */
    public boolean isUseCaseSuccess() {
        return useCaseSuccess;
    }

    /**
     * Gets the current timer state.
     * @return the timer state
     */
    public String getTimerState() {
        return timerState;
    }

    /**
     * Gets the elapsed time.
     * @return the elapsed time in nanoseconds
     */
    public long getElapsedTime() {
        return elapsedTime;
    }

}

