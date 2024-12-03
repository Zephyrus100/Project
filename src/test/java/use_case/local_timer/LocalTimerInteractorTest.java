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
    void startSuccessAndSaveFailTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("start");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimer timer = new LocalTimer();
        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {

            @Override
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
                assertEquals("Running", timerOutputData.getTimerState());
                assertTrue(timerRepo.existsTimer());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure unexpected");
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, timer, timerRepo);
        assertEquals(0, interactor.getCurrentElapsedTime());
        assertFalse(timerRepo.existsTimer());
        interactor.execute(inputData);
    }

    @Test
    void pauseFailTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("pause");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimer timer = new LocalTimer();
        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
                fail("Use case success is unexpected.");
            }
            public void prepareFailView(String error) {
                assertEquals("Timer is not running", error);
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, timer, timerRepo);
        interactor.execute(inputData);
    }

    @Test
    void alreadyRunningTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("start");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimer timer = new LocalTimer();
        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
            }
            public void prepareFailView(String error) {
                assertEquals("Timer is already running", error);
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, timer, timerRepo);
        interactor.execute(inputData);
        interactor.execute(inputData);
    }
    @Test
    void pauseSuccessTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("Pause");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimer timer = new LocalTimer();
        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
            }
            public void prepareFailView(String error) {
                assertEquals("Timer is already running", error);
                assertTrue(timerRepo.getTimer().isRunning());
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, timer, timerRepo);
        interactor.execute(new LocalTimerInputData("start"));
        interactor.execute(inputData);
    }
    @Test
    void resumeSuccessAndFailTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("resume");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimer timer = new LocalTimer();
        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
                assertEquals("Running", timerOutputData.getTimerState());
            }
            public void prepareFailView(String error) {
                assertEquals("Timer is already running", error);
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, timer, timerRepo);
        interactor.execute(inputData);
        interactor.execute(inputData);
    }
    @Test
    void stopFailTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("stop");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimer timer = new LocalTimer();
        LocalTimerOutputBoundary pausePresenter = new LocalTimerOutputBoundary() {
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
                assertEquals("Stopped", timerOutputData.getTimerState());
            }
            public void prepareFailView(String error) {
                assertEquals("No active timer to stop", error);
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(pausePresenter, timer, timerRepo);
        interactor.execute(inputData);
        timer.start();
        interactor.execute(inputData);
    }
    @Test
    void resetTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("RESET");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimer timer = new LocalTimer();
        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
                assertEquals("Stopped", timerOutputData.getTimerState());
            }
            public void prepareFailView(String error) {
                fail("Use case failure unexpected");
            }
        };
        timer.start();
        timer.pause();
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, timer, timerRepo);
        interactor.execute(inputData);
    }
    @Test
    void saveSuccessTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("save");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimer timer = new LocalTimer();
        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
            }
            public void prepareFailView(String error) {
                fail("Use case failure unexpected");
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, timer, timerRepo);
        timer.start();
        timer.pause();
        long time = timer.getElapsedTime();
        interactor.execute(inputData);
        assertEquals(time, timerRepo.getTotalTime());
    }
    @Test
    void unknownOperationTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("unknown");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimer timer = new LocalTimer();
        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
                fail("Use case success unexpected");
            }
            public void prepareFailView(String error) {
                assertEquals("Invalid operation: unknown", error);
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, timer, timerRepo);
        interactor.execute(inputData);
    }
    @Test
    void updateTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("update");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimer timer = new LocalTimer();
        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
            }
            public void prepareFailView(String error) {
                fail("Use case failure unexpected");
            }
        };
        LocalTimerInteractor interactor = new LocalTimerInteractor(successPresenter, timer, timerRepo);
        timer.start();
        timer.pause();
        interactor.execute(inputData);
    }
}
