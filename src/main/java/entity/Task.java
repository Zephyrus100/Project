package entity;

/**
 * Implements the Task interface.
 */
public class Task implements TaskInterface {

    private double time;
    private String status;
    private String title;
    private String description;

    public Task(double time, String status, String title, String description) {
        this.time = time;
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
        return time;
    }
}
