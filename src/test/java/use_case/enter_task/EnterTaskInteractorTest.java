package use_case.enter_task;

import data_access.InMemoryTaskData;
import entity.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EnterTaskInteractorTest {
    @Test
    void testEnterTaskSuccess() {
        EnterTaskInputData inputData = new EnterTaskInputData("Example Task", "Creating an example task.", 1.0);
        EnterTaskDataInterface taskRepo = new InMemoryTaskData();

        Task task = new Task(1.0, "Example Task", "Creating an example task.");
        taskRepo.addTask(task);

        EnterTaskOutputBoundary successPresenter = new EnterTaskOutputBoundary() {
            @Override
            public boolean prepareTaskAddedView(EnterTaskOutputData addTaskData) {
                assertEquals("Example Task", task.getTitle());
                assertEquals("Creating an example task.", task.getDescription());
                assertEquals(1.0, task.getTaskTime());
                return true;
            }

            @Override
            public void prepareTaskNotAddedView(String error) {
                fail("Use case failure is unexpected.");
            }
        };
        EnterTaskInteractor interactor = new EnterTaskInteractor(taskRepo, successPresenter);
        interactor.addTask(inputData);
    }
}
