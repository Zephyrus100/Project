package use_case.report;

import entity.CommonReport;
import entity.Task;
import entity.TimerSession;

import java.util.ArrayList;

public class ReportInputData {

    private final ArrayList<Task> tasks;
    private final ArrayList<TimerSession> timerSessions;

    public ReportInputData(ArrayList<Task> tasks, ArrayList<TimerSession> timerSessions) {
        this.tasks = tasks;
        this.timerSessions = timerSessions;

    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }
    public ArrayList<TimerSession> getTimerSessions() {
        return timerSessions;
    }

}
