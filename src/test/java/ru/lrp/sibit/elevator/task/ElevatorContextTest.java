package ru.lrp.sibit.elevator.task;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.model.PassengerAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ElevatorContextTest {

    private static final long DELAY_STOP = 1000;

    private static final long DELAY_MOVE = 500;

    private static final int NUMBER_OF_FLOORS = 10;

    private ElevatorContext elevatorContext;

    private List<PassengerAction> actions;

    @Before
    public void setUp() {
        actions = new ArrayList<>();
        elevatorContext = new ElevatorContext(NUMBER_OF_FLOORS, DELAY_STOP, DELAY_MOVE, actions);
    }

    @Test
    public void getLastFloor() {
        Assert.assertEquals(NUMBER_OF_FLOORS - 1, elevatorContext.getLastFloor());
    }

    @Test
    public void isFirstFloor() {
        Assert.assertTrue(elevatorContext.isFirstFloor(0));
        Assert.assertFalse(elevatorContext.isFirstFloor(NUMBER_OF_FLOORS - 1));
    }

    @Test
    public void isLastFloor() {
        Assert.assertFalse(elevatorContext.isLastFloor(0));
        Assert.assertTrue(elevatorContext.isLastFloor(NUMBER_OF_FLOORS - 1));
    }

    @Test
    public void getStopActionDelay() {
        Assert.assertEquals(DELAY_STOP, elevatorContext.getStopActionDelay());
    }

    @Test
    public void getMoveActionDelay() {
        Assert.assertEquals(DELAY_MOVE, elevatorContext.getMoveActionDelay());
    }

    @Test
    public void needToPickup() {
        actions.add(new PassengerAction(Direction.UP, 5));
        Assert.assertTrue(elevatorContext.needToPickup(5, Direction.UP));
        Assert.assertFalse(elevatorContext.needToPickup(6, Direction.UP));
        Assert.assertEquals(1, actions.size());
    }

    @Test
    public void pickup() {
        actions.add(new PassengerAction(Direction.UP, 5));
        elevatorContext.pickup(5, Direction.UP);
        Assert.assertEquals(0, actions.size());
    }

    @Test
    public void getFirstActionPositive() {
        actions.add(new PassengerAction(Direction.UP, 1));
        actions.add(new PassengerAction(Direction.UP, 2));
        actions.add(new PassengerAction(Direction.UP, 3));
        actions.add(new PassengerAction(Direction.UP, 4));
        Optional<PassengerAction> firstAction = elevatorContext.getFirstAction();
        Assert.assertTrue(firstAction.isPresent());
        Assert.assertEquals(Direction.UP, firstAction.get().getDirection());
        Assert.assertEquals(1, firstAction.get().getDestinationFloor());
    }

    @Test
    public void getFirstActionNegative() {
        Optional<PassengerAction> firstAction = elevatorContext.getFirstAction();
        Assert.assertFalse(firstAction.isPresent());
    }
}