package entity;

/**
 * Factory for creating timers.
 */
public interface TimerFactory {
    /**
     * Creates a new Timer.
     * @return the new timer
     */
    TimerInterface create();
}
