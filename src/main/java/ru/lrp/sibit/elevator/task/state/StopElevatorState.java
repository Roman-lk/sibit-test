package ru.lrp.sibit.elevator.task.state;

import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.model.PassengerAction;
import ru.lrp.sibit.elevator.task.ElevatorContext;

import java.util.Optional;

/**
 * Состояние "Стоять"
 */
public class StopElevatorState extends AbstractElevatorState {

    public StopElevatorState(ElevatorContext elevatorContext, int position, Direction direction) {
        super(elevatorContext, position, direction);
    }

    @Override
    public long getDelay() {
        return elevatorContext.getStopActionDelay();
    }

    @Override
    public ElevatorState getNextState() {
        Optional<PassengerAction> action = elevatorContext.getFirstAction();
        if (!action.isPresent()) {
            return this;
        }

        int destinationFloor = action.map(PassengerAction::getDestinationFloor).get();
        Direction destinationDirection = action.map(PassengerAction::getDirection).orElse(Direction.NONE);

        if (destinationDirection.isUp()) {
            elevatorContext.pickup(position, Direction.UP);
        }
        if (direction.isNone() && position < destinationFloor) {
            return new UpElevatorState(elevatorContext, position + 1, Direction.NONE);
        }
        if (destinationDirection.isUp() && position == destinationFloor && !elevatorContext.isLastFloor(destinationFloor)) {
            return new UpElevatorState(elevatorContext, position + 1);
        }

        if (destinationDirection.isDown()) {
            elevatorContext.pickup(position, Direction.DOWN);
        }
        if (direction.isNone() && position > destinationFloor) {
            return new DownElevatorState(elevatorContext, position - 1, direction);
        }
        if (position == destinationFloor && destinationDirection.isDown() && !elevatorContext.isFirstFloor(destinationFloor)) {
            return new DownElevatorState(elevatorContext, position - 1);
        }
        return new StopElevatorState(elevatorContext, position, Direction.NONE);
    }
}