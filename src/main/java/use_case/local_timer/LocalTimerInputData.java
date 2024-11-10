package use_case.local_timer;

/**
 * Input data for timer operations.
 */
public class LocalTimerInputData {
    private final String operation;

    /**
     * Creates a new LocalTimerInputData.
     * @param operation the timer operation to perform (start, pause, resume, stop, reset)
     *                  This will be achieved in the Interactor.
     */
    public LocalTimerInputData(String operation) {
        this.operation = operation;
    }

    /**
     * Gets the timer operation.
     * @return the operation to perform
     */
    public String getOperation() {
        return operation;
    }
}
