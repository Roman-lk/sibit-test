package ru.lrp.sibit.elevator.task;

import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.model.PassengerAction;

import java.util.List;

/**
 * Слушатель изменения состояний автомата, моделирующего лифт
 */
public interface ChangeStateListener {

    /**
     * Сообщает о факте изменения состояния
     *
     * @param direction  текущее направление движения кабины
     * @param position   текущее положения кабины(номер этажа)
     * @param actionList список нажатых кнопок
     */
    void onChange(Direction direction, int position, List<PassengerAction> actionList);

}
