package ru.lrp.sibit.elevator.task;

import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.model.PassengerAction;

import java.util.List;
import java.util.Optional;

public class ElevatorContext {

    /**
     * Номер последнего этажа
     */
    private final int numberOfFloors;

    /**
     * Время(мс) на переход из состояния "Стоп"
     */
    private final long stopActionDelay;

    /**
     * Время(мс) на переход из состояний "Двигаться вверх" и "Двигаться вниз"
     */
    private final long moveActionDelay;

    /**
     * Список вызовов лифта
     */
    private final List<PassengerAction> actions;

    public ElevatorContext(int numberOfFloors, long stopActionDelay, long moveActionDelay, List<PassengerAction> actions) {
        this.numberOfFloors = numberOfFloors;
        this.actions = actions;
        this.stopActionDelay = stopActionDelay;
        this.moveActionDelay = moveActionDelay;
    }

    public int getLastFloor() {
        return numberOfFloors - 1;
    }

    public boolean isFirstFloor(int position) {
        return position == 0;
    }

    public boolean isLastFloor(int position) {
        return position == getLastFloor();
    }

    public long getStopActionDelay() {
        return stopActionDelay;
    }

    public long getMoveActionDelay() {
        return moveActionDelay;
    }

    public boolean needToPickup(int position, Direction direction) {
        return actions
                .stream()
                .anyMatch(act -> act.getDestinationFloor() == position && act.getDirection() == direction);
    }

    public void pickup(int position, Direction direction) {
        actions.removeIf(act -> act.getDestinationFloor() == position && act.getDirection() == direction);
    }

    public Optional<PassengerAction> getFirstAction() {
        return actions.stream().findFirst();
    }
}