package ru.lrp.sibit.elevator.task.state;

import ru.lrp.sibit.elevator.task.ElevatorContext;
import ru.lrp.sibit.elevator.model.Direction;

/**
 * Состояние "Двигаться вниз"
 */
public class DownElevatorState extends AbstractElevatorState {

    public DownElevatorState(ElevatorContext elevatorContext, int position) {
        super(elevatorContext, position, Direction.DOWN);
    }

    public DownElevatorState(ElevatorContext elevatorContext, int position, Direction direction) {
        super(elevatorContext, position, direction);
    }

    @Override
    public long getDelay() {
        return elevatorContext.getMoveActionDelay();
    }

    @Override
    public ElevatorState getNextState() {
        if (elevatorContext.isFirstFloor(position) || direction.isNone() && isDestinationFloor()) {
            return new StopElevatorState(elevatorContext, position, Direction.NONE);
        }
        if (!direction.isNone() && elevatorContext.needToPickup(position, Direction.DOWN)) {
            return new StopElevatorState(elevatorContext, position, Direction.DOWN);
        }
        return new DownElevatorState(elevatorContext, position - 1, direction);
    }

    private boolean isDestinationFloor() {
        return elevatorContext.getFirstAction()
                .filter(act -> act.getDestinationFloor() == position)
                .isPresent();
    }
}