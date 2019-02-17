package ru.lrp.sibit.elevator.model;

/**
 * Направление движения
 */
public enum Direction {

    NONE,
    UP,
    DOWN;

    public boolean isNone() {
        return this == NONE;
    }

    public boolean isUp() {
        return this == UP;
    }

    public boolean isDown() {
        return this == DOWN;
    }
}