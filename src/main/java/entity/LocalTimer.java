package entity;

/**
 * A simple implementation of the local timer.
 */
public class LocalTimer implements TimerInterface {
    private long startTime;
    private boolean isRunning;
    private long totalPausedTime;
    private long pauseStart;

    public LocalTimer() {
        this.startTime = 0;
        this.isRunning = false;
        this.totalPausedTime = 0;
        this.pauseStart = 0;
    }

    @Override
    public void start() {
        if (!isRunning) {
            isRunning = true;
            startTime = System.nanoTime();
            totalPausedTime = 0;
        }
    }

    @Override
    public void pause() {
        if (isRunning) {
            isRunning = false;
            pauseStart = System.nanoTime();
        }
    }

    @Override
    public void resume() {
        if (!isRunning) {
            isRunning = true;
            totalPausedTime += System.nanoTime() - pauseStart;
        }
    }

    @Override
    public void stop() {
        isRunning = false;
        startTime = 0;
        totalPausedTime = 0;
        pauseStart = 0;
    }

    @Override
    public void reset() {
        startTime = System.nanoTime();
        totalPausedTime = 0;
        pauseStart = 0;
    }

    @Override
    public long getElapsedTime() {
        long elapsedTime = 0;
        if (startTime != 0) {
            long currentTime = 0;
            if (isRunning) {
                currentTime = System.nanoTime();
            }
            else {
                currentTime = pauseStart;
            }
            elapsedTime = currentTime - startTime - totalPausedTime;
        }
        return elapsedTime;
    }

    @Override
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public boolean canSave() {
        return getElapsedTime() > 0 && !isRunning;
    }

}
