package entity;

/**
 * Represents a completed timer session.
 */
public class TimerSession {
    private final long startTime;
    private final long endTime;
    private final long duration;

    /**
     * Creates a new TimerSession.
     * @param startTime when the timer started (in nanoseconds)
     * @param endTime when the timer ended (in nanoseconds)
     * @param duration how long the timer ran (in nanoseconds)
     */
    public TimerSession(long startTime, long endTime, long duration) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    /**
     * Gets the start time of the session.
     * @return start time in nanoseconds
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * Gets the end time of the session.
     * @return end time in nanoseconds
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * Gets the duration of the session.
     * @return duration in nanoseconds
     */
    public long getDuration() {
        return duration;
    }
}