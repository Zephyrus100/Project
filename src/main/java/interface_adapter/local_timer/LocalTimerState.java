package interface_adapter.local_timer;

import entity.LocalTimer;

/**
 * State class for the Local Timer.
 */
public class LocalTimerState {
    private String timerState;
    private long totalTime;
    private int sessionCount;

    public LocalTimerState() {
        this.timerState = "Stopped";
        this.totalTime = 0L;
        this.sessionCount = 0;
    }

    public String getTimerState() {
        return timerState;
    }

    public int getSessionCount() {
        return sessionCount;
    }

    public void setSessionCount(int sessionCount) {
        this.sessionCount = sessionCount;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public void setTimerState(String timerState) {
        this.timerState = timerState;
    }

    public long getTotalTime() {
        return totalTime;
    }
}

