package ru.lrp.sibit.elevator.task.state;

import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.task.ElevatorContext;

/**
 * Состояние "Двигаться вверх"
 */
public class UpElevatorState extends AbstractElevatorState {

    public UpElevatorState(ElevatorContext elevatorContext, int position) {
        super(elevatorContext, position, Direction.UP);
    }

    public UpElevatorState(ElevatorContext elevatorContext, int position, Direction direction) {
        super(elevatorContext, position, direction);
    }

    @Override
    public long getDelay() {
        return elevatorContext.getMoveActionDelay();
    }

    @Override
    public ElevatorState getNextState() {
        if (elevatorContext.isLastFloor(position) || direction.isNone() && isDestinationFloor()) {
            return new StopElevatorState(elevatorContext, position, Direction.NONE);
        }
        if (!direction.isNone() && elevatorContext.needToPickup(position, Direction.UP)) {
            return new StopElevatorState(elevatorContext, position, Direction.UP);
        }
        return new UpElevatorState(elevatorContext, position + 1, direction);
    }

    private boolean isDestinationFloor() {
        return elevatorContext.getFirstAction()
                .filter(act -> act.getDestinationFloor() == position)
                .isPresent();
    }

}