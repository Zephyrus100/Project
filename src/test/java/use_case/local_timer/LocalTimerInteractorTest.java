package use_case.local_timer;
import static org.junit.jupiter.api.Assertions.*;

import data_access.InMemoryLocalTimerDataAccess;
import org.junit.jupiter.api.Test;
import entity.LocalTimer;
import entity.LocalTimerFactory;
import entity.User;
import entity.UserFactory;
import data_access.InMemoryUserDataAccessObject;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocalTimerInteractorTest {

    @Test
    void successTest() {
        LocalTimerInputData inputData = new LocalTimerInputData("start");
        LocalTimerDataAccessInterface timerRepo = new InMemoryLocalTimerDataAccess();
        LocalTimerOutputBoundary successPresenter = new LocalTimerOutputBoundary() {

            @Override
            public void prepareSuccessView(LocalTimerOutputData timerOutputData) {
                assertEquals("Timer started", timerOutputData.getTimerState());
                assertTrue(timerRepo.existsTimer());

            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
            // TODO: Maybe more test in the future but not sure
            // TODO: Still need to test the time duration.
        };
    }
}
