package entity;

import java.util.ArrayList;

/**
 * Representation of our Report in our application.
 */
public interface ReportInterface {

    /**
     * Returns a list of tasks.
     * @return List of tasks.
     */
    ArrayList<Task> getTasks();

    /**
     * Returns the total time spent on all tasks.
     * @return Total time spent on all tasks.
     */
    long getTotalTaskTime();

    /**
     * Returns the total number of tasks.
     * @return Total number of tasks.
     */
    int getTotalTaskCount();

    /**
     * Return the focus time by timer. 
     * 
     */
    long getFocusTimeByTimer();

    /**
     * Add a task to the list of Tasks.
     * @param task a task.
     */
    // void addTask(Task task);
    ArrayList<TimerSession> getTimerSessions();
}
