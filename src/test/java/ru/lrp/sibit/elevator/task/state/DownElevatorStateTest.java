package ru.lrp.sibit.elevator.task.state;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.model.PassengerAction;
import ru.lrp.sibit.elevator.task.ElevatorContext;

import java.util.ArrayList;
import java.util.List;

public class DownElevatorStateTest {

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
        ElevatorState state = new DownElevatorState(elevatorContext, 5);

        Assert.assertEquals(DELAY_MOVE, state.getDelay());
    }

    @Test
    public void continueDownWithActionTest() {
        int floor = 5;
        ElevatorState state = new DownElevatorState(elevatorContext, floor);
        actions.add(new PassengerAction(Direction.UP, 7));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(floor - 1, nextState.getPosition());
        Assert.assertEquals(Direction.DOWN, nextState.getDirection());
    }

    @Test
    public void continueDownWithoutActionTest() {
        int floor = 5;
        ElevatorState state = new DownElevatorState(elevatorContext, floor);
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(floor - 1, nextState.getPosition());
        Assert.assertEquals(Direction.DOWN, nextState.getDirection());
    }

    @Test
    public void destinationFloorTest() {
        int floor = 5;
        ElevatorState state = new DownElevatorState(elevatorContext, floor);
        actions.add(new PassengerAction(Direction.DOWN, floor));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(floor, nextState.getPosition());
        Assert.assertEquals(Direction.DOWN, nextState.getDirection());
    }

    @Test
    public void needPickupTest() {
        int floor = 6;
        ElevatorState state = new DownElevatorState(elevatorContext, floor);
        actions.add(new PassengerAction(Direction.DOWN, 4));
        actions.add(new PassengerAction(Direction.DOWN, 6));
        ElevatorState nextState = state.getNextState();

        Assert.assertTrue(nextState instanceof StopElevatorState);
        Assert.assertEquals(floor, nextState.getPosition());
        Assert.assertEquals(Direction.DOWN, nextState.getDirection());
    }

    @Test
    public void firstFloorTest() {
        int floor = 0;
        ElevatorState state = new DownElevatorState(elevatorContext, floor);
        actions.add(new PassengerAction(Direction.UP, 5));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(floor, nextState.getPosition());
        Assert.assertEquals(Direction.NONE, nextState.getDirection());
    }
}