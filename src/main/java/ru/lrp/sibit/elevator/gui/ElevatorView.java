package ru.lrp.sibit.elevator.gui;

import javafx.scene.layout.Pane;
import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.model.PassengerAction;

import java.util.List;

/**
 * Интерфейс предстваления лифта
 */
public interface ElevatorView {

    /**
     * Получить панель со всеми элементами
     */
    Pane getPane();

    /**
     * Добавить слушателя нажатия кнопок вызова
     */
    void setListener(ElevatorViewListener listener);

    /**
     * Обновить предстваление
     *
     * @param direction      направление движения кабины
     * @param position       текущее положение кабины
     * @param currentActions список нажатых кнопок вызова
     */
    void update(Direction direction, int position, List<PassengerAction> currentActions);


}
