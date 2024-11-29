package entity;

/**
 * The simple representation of timer in our program.
 */
public interface TimerInterface {

    /**
     * Start the timer.
     */
    void start();

    /**
     * Pause the timer.
     */
    void pause();

    /**
     * Stop the timer.
     */
    void stop();

    /**
     * Resume the timer.
     */
    void resume();

    /**
     * Reset the timer.
     */
    void reset();

    /**
     * Get the timer time last.
     * @return the elapsed time.
     */
    long getElapsedTime();

    /**
     * Check the timer if is running.
     * @return if the timer is running.
     */
    boolean isRunning();

    /**
     * Checks if the timer can be saved in its current state
     * @return true if the timer can be saved
     */
    boolean canSave();

}
