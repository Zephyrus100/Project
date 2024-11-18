package use_case.enter_task;

/**
 * Output data for the AddTask.
 */
public class EnterTaskOutputData {

    private String taskTitle;
    private String taskStatus;  // Changed to taskStatus to match the field purpose
    private String taskDescription;
    private double taskTime;  // Fixed naming consistency for taskTime

    // Constructor
    public EnterTaskOutputData(String taskTitle, String taskDescription, double taskTime, String taskStatus) {
        this.taskTitle = taskTitle;
        this.taskStatus = taskStatus != null ? taskStatus : "Uncompleted";  // Default to "Uncompleted" if null
        this.taskDescription = taskDescription;
        this.taskTime = taskTime;
    }

    // Getter methods
    public String getTaskTitle() {
        return taskTitle;
    }

    public String getTaskStatus() {
        return taskStatus;  // Get the task status
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public double getTaskTime() {
        return taskTime;  // Fixed variable name to match the field
    }
}
