package ru.lrp.sibit.elevator.task.state;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.model.PassengerAction;
import ru.lrp.sibit.elevator.task.ElevatorContext;

import java.util.ArrayList;
import java.util.List;

public class StopElevatorStateTest {

    private static final long DELAY_STOP = -1;

    private static final long DELAY_MOVE = 0;

    private static final int NUMBER_OF_FLOORS = 10;

    private ElevatorContext elevatorContext;

    private List<PassengerAction> actions;

    @Before
    public void setUp() {
        actions = new ArrayList<>();
        elevatorContext = new ElevatorContext(NUMBER_OF_FLOORS, DELAY_STOP, DELAY_MOVE, actions);
    }

    @Test
    public void standWithoutActionTest() {
        ElevatorState state = new StopElevatorState(elevatorContext, 5, Direction.NONE);
        ElevatorState nextState = state.getNextState();
        Assert.assertEquals(state, nextState);
    }

    @Test
    public void standAndDestinationFloorIsAboveAndActionUp() {
        ElevatorState state = new StopElevatorState(elevatorContext, 5, Direction.NONE);
        actions.add(new PassengerAction(Direction.UP, 7));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(nextState.getPosition(), 6);
        Assert.assertEquals(nextState.getDirection(), Direction.NONE);
    }

    @Test
    public void standAndDestinationFloorIsAboveAndActionDown() {
        ElevatorState state = new StopElevatorState(elevatorContext, 5, Direction.NONE);
        actions.add(new PassengerAction(Direction.DOWN, 7));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(nextState.getPosition(), 6);
        Assert.assertEquals(nextState.getDirection(), Direction.NONE);
    }

    @Test
    public void standAndDestinationFloorIsBelowAndActionUp() {
        ElevatorState state = new StopElevatorState(elevatorContext, 5, Direction.NONE);
        actions.add(new PassengerAction(Direction.UP, 3));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(nextState.getPosition(), 4);
        Assert.assertEquals(nextState.getDirection(), Direction.NONE);
    }

    @Test
    public void standAndDestinationFloorIsBelowAndActionDown() {
        ElevatorState state = new StopElevatorState(elevatorContext, 5, Direction.NONE);
        actions.add(new PassengerAction(Direction.DOWN, 3));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(nextState.getPosition(), 4);
        Assert.assertEquals(nextState.getDirection(), Direction.NONE);
    }

    @Test
    public void directionDownAndLocatedOnDestinationFloorAndActionDown() {
        ElevatorState state = new StopElevatorState(elevatorContext, 5, Direction.DOWN);
        actions.add(new PassengerAction(Direction.DOWN, 5));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(nextState.getPosition(), 4);
        Assert.assertEquals(nextState.getDirection(), Direction.DOWN);
    }

    @Test
    public void directionUpAndLocatedOnDestinationFloorAndActionUp() {
        ElevatorState state = new StopElevatorState(elevatorContext, 5, Direction.UP);
        actions.add(new PassengerAction(Direction.UP, 5));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(nextState.getPosition(), 6);
        Assert.assertEquals(nextState.getDirection(), Direction.UP);
    }

    @Test
    public void directionDownOnFirstFloor() {
        ElevatorState state = new StopElevatorState(elevatorContext, 0, Direction.NONE);
        actions.add(new PassengerAction(Direction.DOWN, 0));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(nextState.getPosition(), 0);
        Assert.assertEquals(nextState.getDirection(), Direction.NONE);
    }

    @Test
    public void directionUpOnLastFloor() {
        ElevatorState state = new StopElevatorState(elevatorContext, NUMBER_OF_FLOORS - 1, Direction.NONE);
        actions.add(new PassengerAction(Direction.UP, NUMBER_OF_FLOORS - 1));
        ElevatorState nextState = state.getNextState();

        Assert.assertEquals(NUMBER_OF_FLOORS - 1, nextState.getPosition());
        Assert.assertEquals(Direction.NONE, nextState.getDirection());
    }

    @Test
    public void getDelayTest() {
        ElevatorState state = new StopElevatorState(elevatorContext, 5, Direction.UP);
        Assert.assertEquals(state.getDelay(), DELAY_STOP);
    }

    @Test(expected = IllegalArgumentException.class)
    public void floorLessThan0Test() {
        new StopElevatorState(elevatorContext, -10, Direction.UP);
    }

    @Test(expected = IllegalArgumentException.class)
    public void floorGreatThanLastFloorTest() {
        new StopElevatorState(elevatorContext, 100, Direction.UP);
    }
}