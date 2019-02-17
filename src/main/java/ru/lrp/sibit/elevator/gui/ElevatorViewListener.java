package ru.lrp.sibit.elevator.gui;

import ru.lrp.sibit.elevator.model.PassengerAction;

/**
 * Слушатель нажатия пользователем кнопок вызова
 */
public interface ElevatorViewListener {

    void onAction(PassengerAction action);
}
