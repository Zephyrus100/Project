package entity;

import java.util.List;
import java.util.ArrayList;

/**
 * The representation of a users' report in our program.
 */
public class CommonReport implements ReportInterface {
    private final ArrayList<Task> tasks;
    private final ArrayList<TimerSession> timerSessions;

    public CommonReport(ArrayList<Task> tasks, ArrayList<TimerSession> timerSessions) {
        this.tasks = new ArrayList<>(tasks);
        this.timerSessions = new ArrayList<>(timerSessions);
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks);
    }

    @Override
    public ArrayList<TimerSession> getTimerSessions() {
        return new ArrayList<>(timerSessions);
    }

    @Override
    public long getTotalTaskTime() {
        // Unit of task time is minutes
        return tasks.stream()
                .mapToLong(task -> (long)task.getTaskTime())
                .sum();
    }

    @Override
    public int getTotalTaskCount() {
        return tasks.size();
    }

    @Override
    public long getFocusTimeByTimer() {
        // Convert nanoseconds to minutes
        return (long) (timerSessions.stream()
                .mapToLong(TimerSession::getDuration)
                .sum() / (60.0 * 1_000_000_000.0));
    }
}
