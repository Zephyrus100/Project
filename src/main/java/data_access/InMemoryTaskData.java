package data_access;

import entity.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import use_case.enter_task.EnterTaskDataInterface;

public class InMemoryTaskData implements EnterTaskDataInterface {

    // A map to store tasks by their names
    private final Map<String, Task> tasks = new HashMap<>();

    @Override
    public void addTask(String taskName, String taskDescription, double taskTime, String taskStatus) {
        Task task = new Task(taskTime, taskName, taskDescription);
        task.setStatus(taskStatus);
        tasks.put(taskName, task);
        
        // Debug print statements
        System.out.println("Task added to InMemoryTaskData:");
        System.out.println("Task Name: " + taskName);
        System.out.println("Task Description: " + taskDescription);
        System.out.println("Task Time: " + taskTime);
        System.out.println("Task Status: " + taskStatus);
        System.out.println("Total tasks in memory: " + tasks.size());
    }

    @Override
    public String getTaskName(String taskName) {
        Task task = tasks.get(taskName);
        return (task != null) ? task.getTitle() : null;
    }

    @Override
    public double getTaskTime(String taskName) {
        Task task = tasks.get(taskName);
        return (task != null) ? task.getTaskTime() : -1;
    }

    @Override
    public String getTaskStatus(String taskName) {
        Task task = tasks.get(taskName);
        return (task != null) ? task.getStatus() : null;
    }

    @Override
    public String getTaskDescription(String taskName) {
        Task task = tasks.get(taskName);
        return (task != null) ? task.getDescription() : null;
    }

    public ArrayList<Task> getAllTasks() {
        System.out.println("\nDebug: InMemoryTaskData.getAllTasks() called");
        System.out.println("Number of tasks in memory: " + tasks.size());
        
        for (Task task : tasks.values()) {
            System.out.println("Task in memory: " + task.getTitle());
        }
        
        return new ArrayList<>(tasks.values());
    }

    public double getTotalTaskTime() {
        return tasks.values().stream()
                   .mapToDouble(Task::getTaskTime)
                   .sum();
    }

    public int getTotalTaskCount() {
        return tasks.size();
    }
}
