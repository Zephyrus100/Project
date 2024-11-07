package use_case.edit_report;

public interface EditReportOutputBoundary {

    /**
     * Show the successfull view.
     */
    void prepareSucessView();

    /**
     * Show unsucessfull view.
     */
    void prepareFailedView();

}
