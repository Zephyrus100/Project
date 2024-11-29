package use_case.report;

public interface ReportOutputBoundary {
    
    void prepareSuccessView(ReportOutputData reportOutputData);
    void prepareFailView(String error);
}
