package ru.lrp.sibit.elevator.gui;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import ru.lrp.sibit.elevator.model.Direction;
import ru.lrp.sibit.elevator.model.PassengerAction;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static ru.lrp.sibit.elevator.ResourcesPath.FXML_BUTTONS;
import static ru.lrp.sibit.elevator.ResourcesPath.FXML_CABIN;

public class ElevatorViewImpl implements ElevatorView {

    private static final String STYLE_BTN_SELECTED = "button-selected";

    private static final String STYLE_CABIN_STOP = "cabin-stop";

    private static final String CLASS_BUTTON = ".button";

    private static final String CLASS_BUTTON_UP = ".button-up";

    private static final String CLASS_BUTTON_DOWN = ".button-down";

    private static final String KEY_FLOOR = "floor";

    private static final String KEY_DIRECTION = "direction";

    private ElevatorViewListener listener;

    private int floorCount;

    private GridPane mainPane;

    private Pane elevatorCabinPane;


    public ElevatorViewImpl(int floorCount) {
        if (floorCount < 2) {
            throw new IllegalArgumentException("The number of floors must be greater or equal to 2");
        }
        this.floorCount = floorCount;
        this.mainPane = createPane();
    }

    @Override
    public void setListener(ElevatorViewListener listener) {
        Objects.requireNonNull(listener, "The listener can't be null");
        this.listener = listener;
    }

    @Override
    public void update(Direction direction, int position, List<PassengerAction> currentActions) {
        Platform.runLater(() -> {
            for (Node node : mainPane.lookupAll(CLASS_BUTTON)) {
                if (node instanceof Button) {
                    Integer floorProperty = (Integer) node.getProperties().get(KEY_FLOOR);
                    Direction directionProperty = (Direction) node.getProperties().get(KEY_DIRECTION);
                    if (floorProperty != null && directionProperty != null) {
                        if (currentActions.contains(new PassengerAction(directionProperty, floorProperty))) {
                            node.getStyleClass().add(STYLE_BTN_SELECTED);
                        } else {
                            node.getStyleClass().removeAll(STYLE_BTN_SELECTED);
                        }
                    }
                }
            }

            if (direction.isNone()) {
                elevatorCabinPane.getStyleClass().add(STYLE_CABIN_STOP);
            } else {
                elevatorCabinPane.getStyleClass().removeAll(STYLE_CABIN_STOP);
            }

            mainPane.getChildren().remove(elevatorCabinPane);
            mainPane.add(elevatorCabinPane, 1, floorCount - position);
        });

    }

    @Override
    public Pane getPane() {
        return mainPane;
    }

    private GridPane createPane() {
        try {
            GridPane shaftPane = new GridPane();
            shaftPane.setGridLinesVisible(true);
            for (int i = 0; i < floorCount; i++) {
                shaftPane.add(createFloorButtons(i, floorCount - 1), 0, floorCount - i);
            }
            elevatorCabinPane = createCabin();
            shaftPane.add(elevatorCabinPane, 1, floorCount);

            return shaftPane;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Pane createFloorButtons(int currentFloor, int lastFloor) throws IOException {
        AnchorPane buttonsPane = FXMLLoader.load(getClass().getResource(FXML_BUTTONS));

        Button upButton = (Button) buttonsPane.lookup(CLASS_BUTTON_UP);
        upButton.setVisible(currentFloor != lastFloor);
        upButton.getProperties().put(KEY_FLOOR, currentFloor);
        upButton.getProperties().put(KEY_DIRECTION, Direction.UP);
        upButton.setOnAction(new ButtonClickListener());

        Button downButton = (Button) buttonsPane.lookup(CLASS_BUTTON_DOWN);
        downButton.setVisible(currentFloor != 0);
        downButton.getProperties().put(KEY_FLOOR, currentFloor);
        downButton.getProperties().put(KEY_DIRECTION, Direction.DOWN);
        downButton.setOnAction(new ButtonClickListener());

        return buttonsPane;
    }

    private Pane createCabin() throws IOException {
        return FXMLLoader.load(getClass().getResource(FXML_CABIN));
    }

    private class ButtonClickListener implements EventHandler<ActionEvent> {

        @Override
        public void handle(ActionEvent event) {
            if (listener != null) {
                Button source = (Button) event.getSource();
                source.getStyleClass().add(STYLE_BTN_SELECTED);
                Integer floor = (Integer) source.getProperties().get(KEY_FLOOR);
                Direction direction = (Direction) source.getProperties().get(KEY_DIRECTION);
                listener.onAction(new PassengerAction(direction, floor));
            }
        }
    }

}
