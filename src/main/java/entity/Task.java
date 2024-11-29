package entity;

/**
 * Implements the Task interface.
 */
public class Task implements TaskInterface {

    private final double plannedDuration;
    private String status;
    private final String title;
    private final String description;
    private double elapsedMinutes;

    // I delete status as the parameter. The initial status is Uncompleted,
    // Then write new function to determine which status.
    public Task(double plannedDuration, String title, String description) {
        this.plannedDuration = plannedDuration;
        this.status = "Uncompleted";
        this.title = title;
        this.description = description;
        this.elapsedMinutes = 0.0;
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
        return plannedDuration ;
    }

    // Return progress for the task. Return the progress from 0 to 100.
    public double getProgress() {
        return (elapsedMinutes / plannedDuration) * 100;
    }

    // Update the progress.
    public void updateProgress(double elapsedMinutes) {
        this.elapsedMinutes =Math.min(elapsedMinutes, plannedDuration);
        updateStatus();
    }
    private void updateStatus() {
        if (elapsedMinutes >= plannedDuration) {
            status = "Completed";
        }
        else {
            status = "Uncompleted";
        }
    }

    // New: If the duration is completed we can use it as a timerSession! Then for the report.
//    public double getActualDuration() {
//        if (completedTimer != null) {
//
//            return completedTimer.getDuration() / (60.0 * 1_000_000_000L);
//        }
//        return 0.0;
//    }

    /**
     * Sets the status of the task.
     * @param status The new status to set
     */
    public void setStatus(String status) {
        if (status != null && (status.equals("Completed") || status.equals("Uncompleted"))) {
            this.status = status;
        }
    }
}
