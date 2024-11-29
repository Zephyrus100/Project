package entity;

import java.util.ArrayList;

public class CommonReportFactory implements ReportFactory {
    @Override
    public ReportInterface create(ArrayList<Task> tasks, ArrayList<TimerSession> timerSessions) {
        
        return new CommonReport(tasks, timerSessions);
    }
}
