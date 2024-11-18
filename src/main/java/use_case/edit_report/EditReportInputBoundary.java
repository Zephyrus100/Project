package use_case.edit_report;

/**
 * Input boundary to handle edits made to the report.
 */
public interface EditReportInputBoundary {

    /**
     * Adding a task to the report.
     */
    void addToReport();

    /**
     * Delete a task from the report.
     */
    void removeFromReport();


}
