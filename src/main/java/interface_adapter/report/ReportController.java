package interface_adapter.report;

import use_case.report.ReportInputBoundary;
import use_case.report.ReportInputData;
import java.util.ArrayList;
import entity.Task;
import entity.TimerSession;
import use_case.report.ReportDataAccessInterface;
public class ReportController {
    final ReportInputBoundary reportUseCaseInteractor;
    final ReportDataAccessInterface reportDataAccessObject;

    public ReportController(ReportInputBoundary reportUseCaseInteractor, 
                          ReportDataAccessInterface reportDataAccessObject) {
        this.reportUseCaseInteractor = reportUseCaseInteractor;
        this.reportDataAccessObject = reportDataAccessObject;
    }

    public void execute() {
        ArrayList<Task> tasks = reportDataAccessObject.getTasks();
        ArrayList<TimerSession> timerSessions = reportDataAccessObject.getTimerSessions();
        
        ReportInputData reportInputData = new ReportInputData(tasks, timerSessions);
        reportUseCaseInteractor.execute(reportInputData);
    }
}
