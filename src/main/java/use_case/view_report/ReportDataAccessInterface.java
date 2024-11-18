package use_case.view_report;

import entity.Report;

/**
 * Interface to store and access information relating to a report.
 */
public interface ReportDataAccessInterface {

    /**
     * Access the report.
     */
    void getReportTasks();

    /**
     * Get the number of tasks from a gievn report.
     */
    void getTaskCount();
}
