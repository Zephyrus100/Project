package data_access;

import entity.TimerInterface;
import entity.TimerSession;
import use_case.local_timer.LocalTimerDataAccessInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory data access implementation for timer operations.
 * Stores current timer state and history of completed sessions.
 * Test test.
 */

public class InMemoryLocalTimerDataAccess implements LocalTimerDataAccessInterface {
    private TimerInterface currentTimer;
    private final List<TimerSession> timerHistory;

    /**
     * Creates a new LocalTimerDataAccess.
     */
    public InMemoryLocalTimerDataAccess() {
        this.currentTimer = null;
        this.timerHistory = new ArrayList<>();
    }

    /**
     * Saves the current timer state.
     * @param timer the timer to save
     */
    @Override
    public void save(TimerInterface timer) {
        this.currentTimer = timer;
    }

    /**
     * Gets the current timer.
     * @return the current timer
     */
    @Override
    public TimerInterface getTimer() {
        return currentTimer;
    }

    /**
     * Checks if a timer exists.
     * @return true if a timer exists
     */
    @Override
    public boolean existsTimer() {
        return currentTimer != null;
    }

    /**
     * Saves a completed timer session.
     * @param startTime when the timer started
     * @param endTime when the timer ended
     * @param duration how long the timer ran
     */
    @Override
    public void saveSession(long startTime, long endTime, long duration) {
        final TimerSession session = new TimerSession(startTime, endTime, duration);
        timerHistory.add(session);
    }

    /**
     * Gets the total time of all completed sessions.
     * @return total duration in nanoseconds
     */
    @Override
    public long getTotalTime() {
        return timerHistory.stream()
                .mapToLong(TimerSession::getDuration)
                .sum();
    }

    /**
     * Gets the number of completed timer sessions.
     * @return number of sessions
     */
    @Override
    public int getSessionCount() {
        return timerHistory.size();
    }

    /**
     * Gets all timer sessions.
     * @return list of all timer sessions
     */
    @Override
    public List<TimerSession> getAllSessions() {
        return new ArrayList<>(timerHistory);
        // Return a copy to prevent modification
    }
}
