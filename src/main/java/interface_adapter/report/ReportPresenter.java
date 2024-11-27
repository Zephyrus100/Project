package interface_adapter.report;
import interface_adapter.ViewManagerModel;
import use_case.report.ReportOutputBoundary;
import use_case.report.ReportOutputData;

public class ReportPresenter implements ReportOutputBoundary {
    private final ReportViewModel reportViewModel;
    private final ViewManagerModel viewManagerModel;

    public ReportPresenter(ViewManagerModel viewManagerModel, ReportViewModel reportViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.reportViewModel = reportViewModel;
    }

    @Override
    public void prepareSuccessView(ReportOutputData data) {
        System.out.println("ReportPresenter: Preparing success view");
        System.out.println("Total focus time (minutes): " + data.getTotalTimerSessionTime());
        
        ReportState reportState = new ReportState();
        reportState.setTasks(data.getCompletedTasks());
        reportState.setTimerSessions(data.getTimerSessions());
        reportState.setTotalTaskTime(data.getTotalTaskTime());
        reportState.setTotalTimerSessionTime(data.getTotalTimerSessionTime());
        reportState.setTotalTaskCount(data.getTotalTaskCount());
        
        reportViewModel.setState(reportState);
        reportViewModel.firePropertyChanged();
        
        viewManagerModel.setState(reportViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        ReportState reportState = reportViewModel.getState();
        reportState.setError(error);
        reportViewModel.setState(reportState);
        reportViewModel.firePropertyChanged();
    }
}
