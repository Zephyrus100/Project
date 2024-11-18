package use_case.local_timer;

import entity.TimerInterface;
import entity.TimerSession;
import java.util.List;

/**
 * Interface for timer data access operations.
 */
public interface LocalTimerDataAccessInterface {
    /**
     * Saves the current timer state.
     * @param timer the timer to save
     */
    void save(TimerInterface timer);

    /**
     * Gets the current timer.
     * @return the current timer
     */
    TimerInterface getTimer();

    /**
     * Checks if a timer exists.
     * @return true if a timer exists
     */
    boolean existsTimer();

    /**
     * Saves a completed timer session.
     * @param startTime when the timer started
     * @param endTime when the timer ended
     * @param duration how long the timer ran
     */
    void saveSession(long startTime, long endTime, long duration);

    /**
     * Gets the total time of all completed sessions.
     * @return total duration in nanoseconds
     */
    long getTotalTime();

    /**
     * Gets the number of completed timer sessions.
     * @return number of sessions
     */
    int getSessionCount();

    /**
     * Gets all timer sessions.
     * @return list of all timer sessions
     */
    List<TimerSession> getAllSessions();
}
