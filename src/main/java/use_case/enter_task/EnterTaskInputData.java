package use_case.enter_task;

/**
 * Input data from the task.
 */
public class EnterTaskInputData {

    private String taskName;
    private String description;
    private double taskTime;
    private String status;

    public EnterTaskInputData(String taskName, String description, double taskTime) {
        this.taskName = taskName;
        this.description = description;
        this.taskTime = taskTime;
        this.status = "Uncompleted";
    }

    public String getTaskName() {
        return taskName;
    }

    String getDescription() {
        return description;
    }

    double getTaskTime() {
        return taskTime;
    }

    String getStatus() {
        return status;
    }
}
