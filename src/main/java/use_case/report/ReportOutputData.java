package use_case.report;

import entity.Task;
import java.util.ArrayList;
import entity.TimerSession;
import interface_adapter.report.ReportState;

public class ReportOutputData {
    private final long totalTaskTime;  // in minutes
    private final long totalTimerSessionTime;  // in minutes
    private final int totalTaskCount;
    private final ArrayList<TaskReportData> completedTasks;
    private final ArrayList<TimerSessionData> timerSessions;

    
    public static class TaskReportData {
        private final String taskTitle;
        private final String taskDescription;
        private final double plannedTime;  // in minutes
        private final String status;

        public TaskReportData(String taskTitle, String taskDescription, 
                            double plannedTime, String status) {
            this.taskTitle = taskTitle;
            this.taskDescription = taskDescription;
            this.plannedTime = plannedTime;
            this.status = status;
        }

        public String getTaskTitle() { 
            return taskTitle; 
        }
        public String getTaskDescription() { 
            return taskDescription; 
        }
        public double getPlannedTime() { 
            return plannedTime; 
        }
        public String getStatus() { 
            return status; 
        }
    }

    public static class TimerSessionData {
        private final long startTime;
        private final long endTime;
        private final long duration;

        public TimerSessionData(long startTime, long endTime, long duration) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.duration = duration;
        }

        public long getStartTime() { return startTime; }
        public long getEndTime() { return endTime; }
        public long getDuration() { return duration; }
    }

    public ReportOutputData(long totalTaskTime, long totalTimerSessionTime, 
                          int totalTaskCount, ArrayList<Task> tasks,
                          ArrayList<TimerSession> timerSessions) {
        this.totalTaskTime = totalTaskTime;
        this.totalTimerSessionTime = totalTimerSessionTime;
        this.totalTaskCount = totalTaskCount;
        
        // Convert tasks to TaskReportData
        this.completedTasks = new ArrayList<>();
        for (Task task : tasks) {
            this.completedTasks.add(new TaskReportData(
                task.getTitle(),
                task.getDescription(),
                task.getTaskTime(),
                task.getStatus()
            ));
        }

        // Convert timer sessions to TimerSessionData
        this.timerSessions = new ArrayList<>();
        for (TimerSession session : timerSessions) {
            this.timerSessions.add(new TimerSessionData(
                session.getStartTime(),
                session.getEndTime(),
                session.getDuration()
            ));
        }
    }

    public long getTotalTaskTime() { return totalTaskTime; }
    public long getTotalTimerSessionTime() { return totalTimerSessionTime; }
    public int getTotalTaskCount() { return totalTaskCount; }
    public ArrayList<TaskReportData> getCompletedTasks() { 
        return new ArrayList<>(completedTasks); 
    }
    public ArrayList<TimerSessionData> getTimerSessions() {
        return new ArrayList<>(timerSessions);
    }
}
