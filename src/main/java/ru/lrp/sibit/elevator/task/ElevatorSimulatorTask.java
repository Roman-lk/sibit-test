package ru.lrp.sibit.elevator.task;

import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.model.PassengerAction;
import ru.lrp.sibit.elevator.task.state.ElevatorState;
import ru.lrp.sibit.elevator.task.state.StopElevatorState;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Автомат моделирующий работу лифта
 */
public class ElevatorSimulatorTask implements Runnable {

    /**
     * Список подписавшихся слушателей
     */
    private final Set<ChangeStateListener> listeners = new CopyOnWriteArraySet<>();
    /**
     * Список нажатых кнопок
     */
    private final List<PassengerAction> actions = new CopyOnWriteArrayList<>();
    /**
     * Текушее состояние лифта
     */
    private ElevatorState currentState;

    public ElevatorSimulatorTask(int numberOfFloors, int stopActionDelay, int moveActionDelay) {
        ElevatorContext initElevatorContext = new ElevatorContext(numberOfFloors, stopActionDelay, moveActionDelay, actions);
        currentState = new StopElevatorState(initElevatorContext, 0, Direction.NONE);
    }

    @Override
    public void run() {
        ElevatorState lastState = null;
        while (!Thread.currentThread().isInterrupted()) {
            currentState = currentState.getNextState();
            if (currentState != lastState) {
                lastState = currentState;
                notifyListeners();
            }
            delay(currentState.getDelay());
        }
    }

    /**
     * Добавить действие вызова лифта на этаж
     */
    public void addAction(PassengerAction action) {
        Objects.requireNonNull(action, "Action is null");
        actions.add(action);
    }

    /**
     * Добавить слушателя состояний
     */
    public void addListener(ChangeStateListener listener) {
        Objects.requireNonNull(listener, "Listener is null");
        listeners.add(listener);
        notifyListener(listener);
    }

    /**
     * Удалить слушателя состояний
     */
    public void removeListener(ChangeStateListener listener) {
        Objects.requireNonNull(listener, "Listener is null");
        listeners.remove(listener);
    }


    private void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void notifyListeners() {
        listeners.forEach(this::notifyListener);
    }

    private void notifyListener(ChangeStateListener listener) {
        listener.onChange(currentState.getDirection(), currentState.getPosition(), Collections.unmodifiableList(actions));
    }

}
