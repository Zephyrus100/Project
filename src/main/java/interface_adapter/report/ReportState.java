package interface_adapter.report;

import use_case.report.ReportOutputData.TaskReportData;
import use_case.report.ReportOutputData.TimerSessionData;
import java.util.ArrayList;

public class ReportState {
    private ArrayList<TaskReportData> tasks;
    private long totalTaskTime;
    private long totalTimerSessionTime;
    private int totalTaskCount;
    private String error;
    private ArrayList<TimerSessionData> timerSessions;

    // Copy constructor
    public ReportState(ReportState copy) {
        this.tasks = new ArrayList<>(copy.tasks);
        this.totalTaskTime = copy.totalTaskTime;
        this.totalTimerSessionTime = copy.totalTimerSessionTime;
        this.totalTaskCount = copy.totalTaskCount;
        this.error = copy.error;
        this.timerSessions = new ArrayList<>(copy.timerSessions);
    }

    public ReportState() {
        tasks = new ArrayList<>();
    }

    public ArrayList<TaskReportData> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<TaskReportData> tasks) {
        this.tasks = tasks;
    }

    public long getTotalTaskTime() {
        return totalTaskTime;
    }

    public void setTotalTaskTime(long time) {
        this.totalTaskTime = time;
    }

    public long getTotalTimerSessionTime() {
        return totalTimerSessionTime;
    }

    public void setTotalTimerSessionTime(long time) {
        this.totalTimerSessionTime = time;
    }

    public int getTotalTaskCount() {
        return totalTaskCount;
    }

    public void setTotalTaskCount(int count) {
        this.totalTaskCount = count;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public ArrayList<TimerSessionData> getTimerSessions() {
        return timerSessions;
    }

    public void setTimerSessions(ArrayList<TimerSessionData> timerSessions) {
        this.timerSessions = timerSessions;
    }
}
