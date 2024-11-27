package data_access;

import entity.TimerInterface;
import entity.TimerSession;
import use_case.local_timer.LocalTimerDataAccessInterface;
import java.util.ArrayList;
import java.util.List;

public class InMemoryLocalTimerDataAccess implements LocalTimerDataAccessInterface {
    private TimerInterface currentTimer;
    private final ArrayList<TimerSession> timerSessions = new ArrayList<>();
    
    @Override
    public void save(TimerInterface timer) {
        this.currentTimer = timer;
    }

    @Override
    public TimerInterface getTimer() {
        return currentTimer;
    }

    @Override
    public boolean existsTimer() {
        return currentTimer != null;
    }

    @Override
    public void saveSession(long startTime, long endTime, long duration) {
        TimerSession session = new TimerSession(startTime, endTime, duration);
        timerSessions.add(session);
        System.out.println("Timer session saved to memory. Total sessions: " + timerSessions.size());
        System.out.println("Instance ID: " + System.identityHashCode(this));
    }

    @Override
    public long getTotalTime() {
        return timerSessions.stream()
                .mapToLong(TimerSession::getDuration)
                .sum();
    }

    @Override
    public int getSessionCount() {
        return timerSessions.size();
    }

    @Override
    public ArrayList<TimerSession> getAllSessions() {
        System.out.println("Getting all sessions from memory. Count: " + timerSessions.size());
        System.out.println("Instance ID: " + System.identityHashCode(this));
        return new ArrayList<>(timerSessions);
    }
}
