package entity;

import java.util.ArrayList;

public interface ReportFactory {
    ReportInterface create(ArrayList<Task> tasks, ArrayList<TimerSession> timerSessions);
}
