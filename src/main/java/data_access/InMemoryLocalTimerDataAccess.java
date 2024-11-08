package data_access;

import entity.TimerInterface;
import entity.TimerSession;
import use_case.local_timer.LocalTimerDataAccessInterface;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access class for timer operations with history tracking.
 */
public class LocalTimerDataAccess implements LocalTimerDataAccessInterface {
    private TimerInterface currentTimer;
    private final List<TimerSession> timerHistory;

    /**
     * Creates a new LocalTimerDataAccess.
     */
    public LocalTimerDataAccess() {
        this.currentTimer = null;
        this.timerHistory = new ArrayList<>();
    }

    // ... other existing methods ...

    @Override
    public void saveSession(long startTime, long endTime, long duration) {
        timerHistory.add(new TimerSession(startTime, endTime, duration));
    }

    @Override
    public long getTotalTime() {
        return timerHistory.stream()
                .mapToLong(TimerSession::getDuration)
                .sum();
    }

    // ... rest of the class ...
}