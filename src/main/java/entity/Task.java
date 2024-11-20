package entity;

/**
 * Implements the Task interface.
 */
public class Task implements TaskInterface {

    private TimerSession timer;
    private String status;
    private String title;
    private String description;

    public Task(TimerSession timer, String status, String title, String description) {
        this.timer = timer;
        this.status = status;
        this.title = title;
        this.description = description;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getTaskTime() {
        return timer.getDuration() ;
    }
}
