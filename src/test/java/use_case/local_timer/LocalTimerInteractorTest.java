package use_case.local_timer;
import static org.junit.jupiter.api.Assertions.*;

import data_access.InMemoryLocalTimerDataAccess;
import entity.*;
import org.junit.jupiter.api.Test;
import data_access.InMemoryUserDataAccessObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class LocalTimerInteractorTest {

    @Test
    void successTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("start");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();

        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {

            @Override
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
                assertEquals("Running", timerOutputData.getTimerState());
                assertTrue(timerRepo.existsTimer());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, new LocalTimerFactory(), timerRepo);
        interactor.execute(inputData);
    }

    @Test
    void pauseFailTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("pause");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimerFactory timerFactory = new LocalTimerFactory();
        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
                fail("Use case success is unexpected.");
            }
            public void prepareFailView(String error) {
                assertEquals("Timer is not running", error);
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, timerFactory, timerRepo);
        interactor.execute(inputData);
    }

    @Test
    void alreadyRunningTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("pause");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimerFactory timerFactory = new LocalTimerFactory();


        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
                fail("Use case success is unexpected.");
            }
            public void prepareFailView(String error) {
                assertEquals("Timer is not running", error);
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, timerFactory, timerRepo);
        interactor.execute(inputData);
    }
}
