package use_case.report;

import entity.ReportFactory;
import entity.Task;
import entity.TimerSession;
import entity.ReportInterface;
import java.util.ArrayList;

public class ReportInteractor implements ReportInputBoundary {
    private final ReportOutputBoundary reportPresenter;
    private final ReportDataAccessInterface reportDataAccessObject;
    private final ReportFactory reportFactory;

    public ReportInteractor(ReportOutputBoundary reportOutputBoundary, ReportDataAccessInterface reportDataAccessInterface, ReportFactory reportFactory) {
        this.reportPresenter = reportOutputBoundary;
        this.reportDataAccessObject = reportDataAccessInterface;
        this.reportFactory = reportFactory;
    }

    @Override
    public void execute(ReportInputData reportInputData) {
        try {
            ArrayList<Task> tasks = reportDataAccessObject.getTasks();
            ArrayList<TimerSession> timerSessions = reportDataAccessObject.getTimerSessions();
            double totalTimerSessionTime = reportDataAccessObject.getTotalTimerSessionTime();
            
            System.out.println("Debug - Report Generation:");
            System.out.println("Number of timer sessions: " + timerSessions.size());
            System.out.println("Raw timer total (ns): " + totalTimerSessionTime);
            
            ReportInterface report = reportFactory.create(tasks, timerSessions);
            
            long focusTime = report.getFocusTimeByTimer();
            System.out.println("Converted focus time (minutes): " + focusTime);
            
            ReportOutputData reportOutputData = new ReportOutputData(
                report.getTotalTaskTime(),
                focusTime,
                report.getTotalTaskCount(),
                tasks,
                timerSessions
            );
            
            reportPresenter.prepareSuccessView(reportOutputData);
            
        } catch (Exception e) {
            reportPresenter.prepareFailView("Failed to generate report: " + e.getMessage());
        }
    }
}
