package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * The representation of a users' report in our program.
 */
public class Report implements ReportInterface {

    private List<Task> taskList;

    public Report() {
        this.taskList = new ArrayList<>();
    }

    @Override
    public List<Task> getTasks() {
        return taskList;
    }

    @Override
    public void addTask(Task task) {
        this.taskList.add(task);
    }
}
