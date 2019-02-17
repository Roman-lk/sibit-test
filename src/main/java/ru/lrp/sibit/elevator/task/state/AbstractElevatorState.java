package ru.lrp.sibit.elevator.task.state;

import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.task.ElevatorContext;

/**
 * Базовый класс для всех состояний
 */
abstract class AbstractElevatorState implements ElevatorState {

    protected ElevatorContext elevatorContext;

    protected int position;

    protected Direction direction;

    protected AbstractElevatorState(ElevatorContext elevatorContext, int position, Direction direction) {
        if (position < 0 || position > elevatorContext.getLastFloor()) {
            throw new IllegalArgumentException("The value should be in the range [0, lastFloor)");
        }
        this.elevatorContext = elevatorContext;
        this.position = position;
        this.direction = direction;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public Direction getDirection() {
        return direction;
    }

}