package data_access;

import entity.Task;
import entity.TimerSession;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import use_case.report.ReportDataAccessInterface;

public class InMemoryReportDataAccess implements ReportDataAccessInterface {
    private final InMemoryTaskData taskData;
    private final InMemoryLocalTimerDataAccess timerData;

    public InMemoryReportDataAccess(InMemoryTaskData taskData, InMemoryLocalTimerDataAccess timerData) {
        this.taskData = taskData;
        this.timerData = timerData;
        System.out.println("ReportDataAccess created with timerData instance: " + System.identityHashCode(timerData));
    }

    @Override
    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = taskData.getAllTasks();
        
        // Debug information
        System.out.println("Debug: Report Data after adding task");
        System.out.println("Total number of tasks: " + tasks.size());
        for (Task task : tasks) {
            System.out.println("Task Name: " + task.getTitle());
            System.out.println("Task Description: " + task.getDescription());
            System.out.println("Task Time: " + task.getTaskTime());
            System.out.println("Task Status: " + task.getStatus());
            System.out.println("--------------------");
        }
        
        return tasks;
    }

    @Override
    public ArrayList<TimerSession> getTimerSessions() {
        ArrayList<TimerSession> sessions = timerData.getAllSessions();
        System.out.println("ReportDataAccess getting timer sessions. Count: " + sessions.size());
        return sessions;
    }

    @Override
    public double getTotalTaskTime() {
        
        return taskData.getTotalTaskTime();
    }

    @Override
    public double getTotalTimerSessionTime() {
        return timerData.getTotalTime() / (60.0 * 1_000_000_000.0);
    }

    @Override
    public int getTotalTaskCount() {
        return taskData.getTotalTaskCount();
    }

    @Override
    public Map<String, Double> getTaskTimeMap() {
        Map<String, Double> taskTimeMap = new HashMap<>();
        ArrayList<Task> tasks = taskData.getAllTasks();
        
        for (Task task : tasks) {
            taskTimeMap.put(task.getTitle(), task.getTaskTime());
        }
        
        return taskTimeMap;
    }
}
