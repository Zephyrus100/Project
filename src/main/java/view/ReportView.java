package view;

import interface_adapter.report.ReportState;
import interface_adapter.report.ReportViewModel;
import use_case.report.ReportOutputData.TaskReportData;
import interface_adapter.report.ReportController;
import interface_adapter.ViewManagerModel;
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
    private final String viewName = "report";
    private final ReportViewModel reportViewModel;
    private final ViewManagerModel viewManagerModel;
    private ReportController reportController;

    private final JTable taskTable;
    private final DefaultTableModel tableModel;
    private final JTable timerSessionTable;
    private final DefaultTableModel timerTableModel;

    private final JLabel totalTaskTimeLabel;
    private final JLabel totalFocusTimeLabel;
    private final JLabel totalTaskCountLabel;

    private static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private static final Color ACCENT_COLOR = new Color(102, 102, 102);
    private static final Color HEADER_COLOR = new Color(51, 51, 51);
    private static final Color TABLE_HEADER_COLOR = new Color(220, 220, 220);
    private static final Color TABLE_STRIPE_COLOR = new Color(240, 240, 240);

    public ReportView(ReportViewModel reportViewModel, ViewManagerModel viewManagerModel) {
        this.reportViewModel = reportViewModel;
        this.viewManagerModel = viewManagerModel;
        this.reportController = null;

        setLayout(new BorderLayout(20, 20));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        JPanel headerPanel = new JPanel(new BorderLayout(15, 0));
        headerPanel.setBackground(BACKGROUND_COLOR);

        JButton backButton = createStyledButton("Back");
        backButton.setForeground(HEADER_COLOR);
        backButton.addActionListener(e -> {
            viewManagerModel.setState("Home View");
            viewManagerModel.firePropertyChanged();
        });

        JLabel title = new JLabel("Report");
        title.setFont(new Font("Georgia", Font.BOLD, 24));
        title.setForeground(HEADER_COLOR);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(title, BorderLayout.CENTER);

        taskTable = createStyledTable();
        timerSessionTable = createStyledTable();

        String[] taskColumns = {"Task", "Time"};
        tableModel = new DefaultTableModel(taskColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        taskTable.setModel(tableModel);

        String[] timerColumns = {"Start", "End", "Duration"};
        timerTableModel = new DefaultTableModel(timerColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        timerSessionTable.setModel(timerTableModel);

        JScrollPane taskScrollPane = createStyledScrollPane(taskTable);
        JScrollPane timerScrollPane = createStyledScrollPane(timerSessionTable);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                taskScrollPane, timerScrollPane);
        splitPane.setResizeWeight(0.5);
        splitPane.setBorder(null);
        splitPane.setDividerSize(8);

        totalTaskTimeLabel = createStyledStatsLabel("Total Time: 0");
        totalFocusTimeLabel = createStyledStatsLabel("Focus Time: 0");
        totalTaskCountLabel = createStyledStatsLabel("Tasks: 0");

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.Y_AXIS));
        statsPanel.setBackground(BACKGROUND_COLOR);
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        statsPanel.add(totalTaskTimeLabel);
        statsPanel.add(Box.createVerticalStrut(8));
        statsPanel.add(totalFocusTimeLabel);
        statsPanel.add(Box.createVerticalStrut(8));
        statsPanel.add(totalTaskCountLabel);

        add(headerPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(statsPanel, BorderLayout.SOUTH);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                updateReportData();
            }
        });
        this.reportViewModel.addPropertyChangeListener(this);
        LocalTimerViewModel timerViewModel = new LocalTimerViewModel();
        timerViewModel.addPropertyChangeListener(evt -> updateReportData());
    }

    private JTable createStyledTable() {
        JTable table = new JTable() {
            @Override
            public Component prepareRenderer(javax.swing.table.TableCellRenderer renderer, int row, int col) {
                Component comp = super.prepareRenderer(renderer, row, col);
                if (!isCellSelected(row, col)) {
                    comp.setBackground(row % 2 == 0 ? Color.WHITE : TABLE_STRIPE_COLOR);
                }
                return comp;
            }
        };

        table.getTableHeader().setBackground(TABLE_HEADER_COLOR);
        table.getTableHeader().setFont(new Font("Georgia", Font.BOLD, 14));
        table.setFont(new Font("Georgia", Font.PLAIN, 14));
        table.setRowHeight(35);
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        return table;
    }

    private JScrollPane createStyledScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(TABLE_HEADER_COLOR, 1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        return scrollPane;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Georgia", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(ACCENT_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(100, 35));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(ACCENT_COLOR);
            }
        });

        return button;
    }

    private JLabel createStyledStatsLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Georgia", Font.BOLD, 16));
        label.setForeground(HEADER_COLOR);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        ReportState state = (ReportState) evt.getNewValue();
        if (state != null) {
            updateTable(state.getTasks());
            updateTimerTable(state.getTimerSessions());
            updateLabels(state);
        }
    }

    private void updateTable(ArrayList<TaskReportData> tasks) {
        tableModel.setRowCount(0);
        for (TaskReportData task : tasks) {
            tableModel.addRow(new Object[]{
                    task.getTaskTitle(),
                    formatTime(task.getPlannedTime())
            });
        }
    }

    private void updateTimerTable(ArrayList<TimerSessionData> sessions) {
        timerTableModel.setRowCount(0);
        for (TimerSessionData session : sessions) {
            timerTableModel.addRow(new Object[]{
                    formatDateTime(session.getStartTime()),
                    formatDateTime(session.getEndTime()),
                    formatDuration(session.getDuration())
            });
        }
    }

    private void updateLabels(ReportState state) {
        totalTaskTimeLabel.setText("Total Time: " + formatTime(state.getTotalTaskTime()));
        totalFocusTimeLabel.setText("Focus Time: " + formatTime(state.getTotalTimerSessionTime()));
        totalTaskCountLabel.setText("Tasks: " + state.getTotalTaskCount());
    }

    private String formatDateTime(long timestampNs) {
        long currentTimeMs = System.currentTimeMillis();
        long currentTimeNs = System.nanoTime();
        long nanoOffset = currentTimeNs - timestampNs;
        long actualTimeMs = currentTimeMs - (nanoOffset / 1_000_000);

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MM/dd HH:mm");
        return sdf.format(new java.util.Date(actualTimeMs));
    }

    private String formatDuration(long durationNs) {
        long minutes = (long) (durationNs / (60.0 * 1_000_000_000.0));
        return formatTime(minutes);
    }

    private String formatTime(double minutes) {
        long hours = (long) (minutes / 60);
        long mins = (long) (minutes % 60);
        return hours > 0 ? String.format("%dh %02dm", hours, mins) : String.format("%02dm", mins);
    }

    private String formatTime(long minutes) {
        return formatTime((double) minutes);
    }

    public void setReportController(ReportController reportController) {
        this.reportController = reportController;
    }

    private void updateReportData() {
        if (reportController != null) {
            reportController.execute();
        }
    }

    public String getViewName() {
        return viewName;
    }
}