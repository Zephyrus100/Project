package use_case.local_timer;

/**
 * Input data for timer operations.
 */
public class LocalTimerInputData {
    private final String operation;
    private long elapsedTime;

    /**
     * Creates a new LocalTimerInputData.
     * @param operation the timer operation to perform (start, pause, resume, stop, reset)
     *                  This will be achieved in the Interactor.
     */
    public LocalTimerInputData(String operation) {
        this.operation = operation;
        this.elapsedTime = 0;
    }

    /**
     * Gets the timer operation.
     * @return the operation to perform
     */
    public String getOperation() {
        return operation;
    }

    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime = elapsedTime;
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}
