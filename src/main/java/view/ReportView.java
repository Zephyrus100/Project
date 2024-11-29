package view;

import interface_adapter.report.ReportState;
import interface_adapter.report.ReportViewModel;
import use_case.report.ReportOutputData.TaskReportData;
import interface_adapter.report.ReportController;
import interface_adapter.ViewManagerModel;
import data_access.InMemoryLocalTimerDataAccess;
import data_access.InMemoryReportDataAccess;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import use_case.report.ReportOutputData.TimerSessionData;
import interface_adapter.local_timer.LocalTimerViewModel;
public class ReportView extends JPanel implements PropertyChangeListener {
    public final String viewName = "report";
    private final ReportViewModel reportViewModel;
    
    // Table components
    private final JTable taskTable;
    private final DefaultTableModel tableModel;
    
    // Labels for totals
    private final JLabel totalTaskTimeLabel;
    private final JLabel totalFocusTimeLabel;
    private final JLabel totalTaskCountLabel;

    private ReportController reportController;
    private final ViewManagerModel viewManagerModel;
    
    private final JTable timerSessionTable;
    private final DefaultTableModel timerTableModel;
    
    public ReportView(ReportViewModel reportViewModel, ViewManagerModel viewManagerModel) {
        this.reportViewModel = reportViewModel;
        this.viewManagerModel = viewManagerModel;
        this.reportController = null;

        // Set up the layout
        setLayout(new BorderLayout());

        // Create title
        JLabel title = new JLabel(ReportViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 24));

        // Create table
        String[] columnNames = {
            reportViewModel.TASK_NAME_LABEL,
            reportViewModel.TIME_SPENT_LABEL
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        taskTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(taskTable);

        // Create timer sessions table
        String[] timerColumnNames = {
            reportViewModel.TIMER_START_LABEL,
            reportViewModel.TIMER_END_LABEL,
            reportViewModel.TIMER_DURATION_LABEL
        };
        timerTableModel = new DefaultTableModel(timerColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        timerSessionTable = new JTable(timerTableModel);
        JScrollPane timerScrollPane = new JScrollPane(timerSessionTable);

        // Create split pane for both tables
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                                            scrollPane,  // existing task table
                                            timerScrollPane);
        splitPane.setResizeWeight(0.5);

        // Create labels panel
        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));
        
        totalTaskTimeLabel = new JLabel(ReportViewModel.TOTAL_TASK_TIME_LABEL + "0");
        totalFocusTimeLabel = new JLabel(ReportViewModel.TOTAL_FOCUS_TIME_LABEL + "0");
        totalTaskCountLabel = new JLabel(ReportViewModel.TOTAL_TASKS_LABEL + "0");

        labelsPanel.add(totalTaskTimeLabel);
        labelsPanel.add(totalFocusTimeLabel);
        labelsPanel.add(totalTaskCountLabel);

        // Add components to panel
        add(title, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(labelsPanel, BorderLayout.SOUTH);

        // Add a component listener to update data when the view becomes visible
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateReportData();
            }
        });

        // Add this line to register the view as a listener
        this.reportViewModel.addPropertyChangeListener(this);  // Make sure this exists

        // Add listeners for both task and timer changes
        this.reportViewModel.addPropertyChangeListener(this);
        
        // Add this line to listen for timer changes too
        LocalTimerViewModel timerViewModel = new LocalTimerViewModel();
        timerViewModel.addPropertyChangeListener(evt -> updateReportData());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Add debug statements
        System.out.println("ReportView: Property change event received");
        
        ReportState state = (ReportState) evt.getNewValue();
        if (state != null) {
            System.out.println("State tasks count: " + state.getTasks().size());
            updateTable(state.getTasks());
            updateTimerTable(state.getTimerSessions());
            updateLabels(state);
        } else {
            System.out.println("Received null state");
        }
    }

    private void updateTable(ArrayList<TaskReportData> tasks) {
        // Clear existing rows
        tableModel.setRowCount(0);
        
        // Add new rows
        for (TaskReportData task : tasks) {
            Object[] row = {
                task.getTaskTitle(),
                formatTime(task.getPlannedTime())
            };
            tableModel.addRow(row);
        }
    }

    private void updateTimerTable(ArrayList<TimerSessionData> sessions) {
        timerTableModel.setRowCount(0);
        
        for (TimerSessionData session : sessions) {
            Object[] row = {
                formatDateTime(session.getStartTime()),
                formatDateTime(session.getEndTime()),
                formatDuration(session.getDuration())
            };
            timerTableModel.addRow(row);
        }
    }

    private String formatDateTime(long timestampNs) {
        // Get current time in both milliseconds (since epoch) and nanoseconds (arbitrary time)
        long currentTimeMs = System.currentTimeMillis();
        long currentTimeNs = System.nanoTime();
        
        // Calculate the offset between the current nano time and the timestamp
        long nanoOffset = currentTimeNs - timestampNs;
        
        // Convert the offset to milliseconds and subtract from current time
        long actualTimeMs = currentTimeMs - (nanoOffset / 1_000_000);
        
        // Format the corrected timestamp
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        sdf.setTimeZone(java.util.TimeZone.getDefault());
        return sdf.format(new java.util.Date(actualTimeMs));
    }

    private String formatDuration(long durationNs) {
        long minutes = (long) (durationNs / (60.0 * 1_000_000_000.0));
        return formatTime(minutes);
    }

    private void updateLabels(ReportState state) {
        totalTaskTimeLabel.setText(ReportViewModel.TOTAL_TASK_TIME_LABEL + 
                                 formatTime(state.getTotalTaskTime()));
        totalFocusTimeLabel.setText(ReportViewModel.TOTAL_FOCUS_TIME_LABEL + 
                                  formatTime(state.getTotalTimerSessionTime()));
        totalTaskCountLabel.setText(ReportViewModel.TOTAL_TASKS_LABEL + 
                                  state.getTotalTaskCount());
    }

    private String formatTime(double minutes) {
        long hours = (long) (minutes / 60);
        long mins = (long) (minutes % 60);
        if (hours > 0) {
            return String.format("%dh %dm", hours, mins);
        }
        return String.format("%dm", mins);
    }

    private String formatTime(long minutes) {
        return formatTime((double) minutes);
    }

    public String getViewName() {
        return "report";
    }

    private void updateReportData() {
        if (reportController != null) {
            reportController.execute();
        }
    }

    public void setReportController(ReportController reportController) {
        this.reportController = reportController;
        System.out.println("ReportController set in ReportView");
    }
}
