package use_case.report;

import entity.Task;
import entity.TimerSession;
import java.util.ArrayList;
import java.util.Map;

public interface ReportDataAccessInterface {
    ArrayList<Task> getTasks();
    ArrayList<TimerSession> getTimerSessions();
    double getTotalTaskTime();
    double getTotalTimerSessionTime();
    int getTotalTaskCount();
    Map<String, Double> getTaskTimeMap();
}
