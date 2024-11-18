package interface_adapter.add_task;

/**
 * The state for the enter task.
 */
public class EnterTaskState {

    private String taskName;
    private String taskDescription;
    private String taskStatus;
    private double taskTime;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public void setTaskStatus(String status) {
        this.taskStatus = status;
    }

    public void setTaskTime(double time) {
        this.taskTime = time;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public double getTaskTime() {
        return taskTime;
    }

    public String getTaskError() {
        return "The task was not added!";
    }
}
