package ru.lrp.sibit.elevator.model;

import java.util.Objects;

/**
 * Действие пассажира
 */
public class PassengerAction {

    /**
     * Направление движения
     */
    private final Direction direction;

    /**
     * Этаж на который движется лифт
     */
    private final int destinationFloor;

    public PassengerAction(Direction direction, int destinationFloor) {
        this.direction = direction;
        this.destinationFloor = destinationFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PassengerAction that = (PassengerAction) o;
        return destinationFloor == that.destinationFloor && direction == that.direction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(direction, destinationFloor);
    }

}
