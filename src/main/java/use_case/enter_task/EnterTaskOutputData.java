package use_case.enter_task;

/**
 * Output data for the AddTask.
 */
public class EnterTaskOutputData {

    private String taskTitle;
    private boolean taskCompleted;
    private String taskDescription;
    private double tasktime;

    public EnterTaskOutputData(String taskTitle, String taskDescription, double tasktime) {
        this.taskTitle = taskTitle;
        this.taskCompleted = false;
        this.taskDescription = taskDescription;
        this.tasktime = tasktime;
    }

    String getTaskTitle() {
        return taskTitle;
    }

    boolean getTaskCompleted() {
        return taskCompleted;
    }

}
