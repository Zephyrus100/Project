package entity;

/**
 * Interface for a Report entity.
 */
public interface ReportEntity {
    int getTotalTasks();
    int getTasksCompleted();
    int getFocusTime();
    int getRemainingTasks();
    int getTimeToDoTasks();
}
