package ru.lrp.sibit.elevator.task.state;

import ru.lrp.sibit.elevator.model.Direction;

/**
 * Интерфейс состояния лифта
 */
public interface ElevatorState {

    /**
     * Получить текущий этаж
     */
    int getPosition();

    /**
     * Получить текущее направление движения
     */
    Direction getDirection();

    /**
     * Получить следующее состояние
     */
    ElevatorState getNextState();

    /**
     * Получить значение временной задаержки для перехода в следующее состояние
     */
    long getDelay();

}
