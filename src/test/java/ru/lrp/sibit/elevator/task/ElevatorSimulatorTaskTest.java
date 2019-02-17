package ru.lrp.sibit.elevator.task;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.model.PassengerAction;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorSimulatorTaskTest {

    private static final int LAST_FLOOR = 5;

    private ExecutorService executor;


    @Before
    public void setUp() {
        executor = Executors.newFixedThreadPool(1);
    }

    @After
    public void tearDown() {
        executor.shutdownNow();
    }

    @Test(timeout = 300)
    public void runTest() throws InterruptedException {
        final Object sync = new Object();

        ChangeStateListener listener = (direction, position, actionList) -> {
            if (actionList.isEmpty()) {
                Assert.assertEquals(Direction.NONE, direction);
                Assert.assertEquals(LAST_FLOOR - 1, position);
                synchronized (sync) {
                    sync.notifyAll();
                }
            }
        };


        ElevatorSimulatorTask task = new ElevatorSimulatorTask(LAST_FLOOR, 10, 10);

        task.addAction(new PassengerAction(Direction.UP, LAST_FLOOR - 1));
        task.addListener(listener);
        executor.submit(task);

        synchronized (sync) {
            sync.wait();
        }
        task.removeListener(listener);
    }


}