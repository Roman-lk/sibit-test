package ru.lrp.sibit.elevator.task.state;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.model.PassengerAction;
import ru.lrp.sibit.elevator.task.ElevatorContext;

import java.util.ArrayList;
import java.util.List;

public class UpElevatorStateTest {

    private static final long DELAY_STOP = -1;

    private static final long DELAY_MOVE = 0;

    private ElevatorContext elevatorContext;

    private List<PassengerAction> actions;

    @Before
    public void setUp() {
        actions = new ArrayList<>();
        elevatorContext = new ElevatorContext(10, DELAY_STOP, DELAY_MOVE, actions);
    }


    @Test
    public void getDelayTest() {
        ElevatorState state = new UpElevatorState(elevatorContext, 5);

        Assert.assertEquals(state.getDelay(), DELAY_MOVE);
    }

    @Test
    public void continueUpWithActionTest() {
        int floor = 5;
        ElevatorState state = new UpElevatorState(elevatorContext, floor);
        actions.add(new PassengerAction(Direction.DOWN, 4));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(floor + 1, nextState.getPosition());
        Assert.assertEquals(Direction.UP, nextState.getDirection());
    }

    @Test
    public void continueUpWithoutActionTest() {
        int floor = 5;
        ElevatorState state = new UpElevatorState(elevatorContext, floor);
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(floor + 1, nextState.getPosition());
        Assert.assertEquals(Direction.UP, nextState.getDirection());
    }

    @Test
    public void destinationFloorTest() {
        int floor = 5;
        ElevatorState state = new UpElevatorState(elevatorContext, floor);
        actions.add(new PassengerAction(Direction.UP, floor));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(floor, nextState.getPosition());
        Assert.assertEquals(Direction.UP, nextState.getDirection());
    }

    @Test
    public void needPickupTest() {
        int floor = 4;
        ElevatorState state = new UpElevatorState(elevatorContext, floor);
        actions.add(new PassengerAction(Direction.UP, 6));
        actions.add(new PassengerAction(Direction.UP, 4));
        ElevatorState nextState = state.getNextState();

        Assert.assertTrue(nextState instanceof StopElevatorState);
        Assert.assertEquals(floor, nextState.getPosition());
        Assert.assertEquals(Direction.UP, nextState.getDirection());
    }

    @Test
    public void lastFloorTest() {
        ElevatorState state = new UpElevatorState(elevatorContext, elevatorContext.getLastFloor());
        actions.add(new PassengerAction(Direction.UP, 5));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(elevatorContext.getLastFloor(), nextState.getPosition());
        Assert.assertEquals(Direction.NONE, nextState.getDirection());
    }
}