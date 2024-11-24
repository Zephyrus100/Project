package data_access;

import java.util.HashMap;
import java.util.Map;
import use_case.enter_task.EnterTaskDataInterface;

public class InMemoryTaskData implements EnterTaskDataInterface {

    // A map to store tasks by their names
    private final Map<String, Task> tasks = new HashMap<>();

    @Override
    public void addTask(String taskName, String taskDescription, double taskTime, String taskStatus) {
        Task task = new Task(taskName, taskDescription, taskTime, taskStatus);
        tasks.put(taskName, task);
    }

    @Override
    public String getTaskName(String taskName) {
        Task task = tasks.get(taskName);
        return (task != null) ? task.getTaskName() : null;
    }

    @Override
    public double getTaskTime(String taskName) {
        Task task = tasks.get(taskName);
        return (task != null) ? task.getTaskTime() : -1;
    }

    @Override
    public String getTaskStatus(String taskName) {
        Task task = tasks.get(taskName);
        return (task != null) ? task.getTaskStatus() : null;
    }

    @Override
    public String getTaskDescription(String taskName) {
        Task task = tasks.get(taskName);
        return (task != null) ? task.getTaskDescription() : null;
    }

    // Inner Task class to store individual task information
    private static class Task {
        private final String taskName;
        private final String taskDescription;
        private final double taskTime;
        private final String taskStatus;

        public Task(String taskName, String taskDescription, double taskTime, String taskStatus) {
            this.taskName = taskName;
            this.taskDescription = taskDescription;
            this.taskTime = taskTime;
            this.taskStatus = taskStatus;
        }

        public String getTaskName() {
            return taskName;
        }

        public String getTaskDescription() {
            return taskDescription;
        }

        public double getTaskTime() {
            return taskTime;
        }

        public String getTaskStatus() {
            return taskStatus;
        }
    }
}
