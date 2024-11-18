package entity;

/**
 * Factory for creating LocalTimer instances.
 */
public class LocalTimerFactory implements TimerFactory {
    /**
     * Creates a new LocalTimer.
     * @return the new LocalTimer instance
     */
    @Override
    public TimerInterface create() {
        return new LocalTimer();
    }
}
