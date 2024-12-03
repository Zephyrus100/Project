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

    @Test
    void testEnterTaskRepoSuccess() {
        EnterTaskInputData inputData = new EnterTaskInputData("Example Task", "Creating an example task.", 1.0);
        EnterTaskDataInterface taskRepo = new InMemoryTaskData();

        Task task = new Task(1.0, "Example Task", "Creating an example task.");
        taskRepo.addTask(task);

        EnterTaskOutputBoundary successPresenter = new EnterTaskOutputBoundary() {
            @Override
            public boolean prepareTaskAddedView(EnterTaskOutputData addTaskData) {
                assertEquals("Example Task", taskRepo.getTaskName("Example Task"));
                assertEquals("Creating an example task.", taskRepo.getTaskDescription("Example Task"));
                assertEquals(1.0, taskRepo.getTaskTime("Example Task"));
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
    @Test
    void testEnterTaskFail() {
        EnterTaskInputData inputData = new EnterTaskInputData("Example Task", "Creating an example task.", 0);
        EnterTaskDataInterface taskRepo = new InMemoryTaskData();

        Task task = new Task(0, "Example Task", "Creating an example task.");
        taskRepo.addTask(task);

        EnterTaskOutputBoundary successPresenter = new EnterTaskOutputBoundary() {
            @Override
            public boolean prepareTaskAddedView(EnterTaskOutputData addTaskData) {
                fail("Use case success is unexpected.");
                return false;
            }

            @Override
            public void prepareTaskNotAddedView(String error) {
                assertEquals(task.getTaskTime(), taskRepo.getTaskTime("Example Task"));
            }
        };
        EnterTaskInteractor interactor = new EnterTaskInteractor(taskRepo, successPresenter);
        interactor.addTask(inputData);
    }

}
