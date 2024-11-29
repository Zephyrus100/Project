package interface_adapter.report;

import interface_adapter.ViewModel;

public class ReportViewModel extends ViewModel<ReportState> {
    public static final String TITLE_LABEL = "Report";
    public static final String TASK_NAME_LABEL = "Task Name";
    public static final String TIME_SPENT_LABEL = "Time Spent";
    public static final String TOTAL_TASK_TIME_LABEL = "Total Task Time: ";
    public static final String TOTAL_FOCUS_TIME_LABEL = "Total Timer Focus Time: ";
    public static final String TOTAL_TASKS_LABEL = "Total Tasks: ";
    public static final String TIMER_START_LABEL = "Start Time";
    public static final String TIMER_END_LABEL = "End Time";
    public static final String TIMER_DURATION_LABEL = "Duration";

    public ReportViewModel() {
        super("report");
        setState(new ReportState());
    }
}
