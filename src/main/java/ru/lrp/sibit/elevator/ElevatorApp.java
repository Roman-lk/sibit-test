package ru.lrp.sibit.elevator;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ru.lrp.sibit.elevator.gui.ElevatorView;
import ru.lrp.sibit.elevator.gui.ElevatorViewImpl;
import ru.lrp.sibit.elevator.task.ElevatorSimulatorTask;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ElevatorApp extends Application {

    private static final int ELEVATORS_COUNT = 2;

    private static final int LAST_FLOOR = 5;

    private static final int STOP_DELAY = 500;

    private static final int MOVE_DELAY = 250;

    private final ExecutorService executor = Executors.newFixedThreadPool(ELEVATORS_COUNT);


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        List<ElevatorView> elevatorViewList = new ArrayList<>();
        for (int i = 0; i < ELEVATORS_COUNT; i++) {
            ElevatorView elevatorView = new ElevatorViewImpl(LAST_FLOOR);
            ElevatorSimulatorTask elevatorSimulatorTask = new ElevatorSimulatorTask(LAST_FLOOR, STOP_DELAY, MOVE_DELAY);

            elevatorView.setListener(elevatorSimulatorTask::addAction);
            elevatorSimulatorTask.addListener(elevatorView::update);

            elevatorViewList.add(elevatorView);
            executor.submit(elevatorSimulatorTask);
        }
        primaryStage.setScene(createScene(elevatorViewList));
        primaryStage.show();
    }

    private Scene createScene(List<ElevatorView> elevatorViewList) {
        Pane[] elevatorsPanes = elevatorViewList.stream()
                .map(ElevatorView::getPane)
                .toArray(Pane[]::new);

        return new Scene(new HBox(elevatorsPanes), 300, 500);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        executor.shutdownNow();
    }
}