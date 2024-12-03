package use_case.report;

import data_access.InMemoryLocalTimerDataAccess;
import data_access.InMemoryReportDataAccess;
import data_access.InMemoryTaskData;
import entity.*;
import org.junit.jupiter.api.Test;
import use_case.enter_task.EnterTaskDataInterface;
import use_case.local_timer.LocalTimerDataAccessInterface;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class ReportInteractorTest {
    @Test
    void successTest() {
        Task task1 = new Task(1, "Test", "Testing");
        Task task2 = new Task(2, "Test2", "Testing2");
        TimerSession timer = new TimerSession(1000, 2000, 1000);
        ArrayList<Task> tasks = new ArrayList<>();
        ArrayList<TimerSession> timers = new ArrayList<>();
        timers.add(timer);
        tasks.add(task1);
        tasks.add(task2);
        ReportInputData inputData = new ReportInputData(tasks,timers);
        InMemoryTaskData taskRepo = new InMemoryTaskData();
        taskRepo.addTask(task1);
        taskRepo.addTask(task2);
        InMemoryLocalTimerDataAccess localTimerRepo = new InMemoryLocalTimerDataAccess();
        localTimerRepo.saveSession(1000,2000,1000);
        ReportDataAccessInterface reportRepo = new InMemoryReportDataAccess(taskRepo, localTimerRepo);
        CommonReport report = new CommonReport(tasks, timers);

        ReportOutputBoundary successPresenter = new ReportOutputBoundary() {
            @Override
            public void prepareSuccessView(ReportOutputData reportOutputData) {
                assertEquals(report.getTotalTaskCount(), reportOutputData.getTotalTaskCount());
                assertEquals(report.getTotalTaskTime(),reportOutputData.getTotalTaskTime());
                assertEquals(report.getTimerSessions(), inputData.getTimerSessions());
                assertEquals(report.getTasks(), inputData.getTasks());
            }
        };
        ReportInteractor interactor = new ReportInteractor(successPresenter, reportRepo, new CommonReportFactory());
        interactor.execute(inputData);
    }
}
