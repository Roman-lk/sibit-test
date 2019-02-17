package ru.lrp.sibit.elevator.gui;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.Set;

public class ElevatorViewImplTest {

    private static final int NUMBER_OF_FLOORS = 10;

    @Rule
    public JavaFXThreadingRule javafxRule = new JavaFXThreadingRule();

    @Test(expected = IllegalArgumentException.class)
    public void constructor() {
        new ElevatorViewImpl(0);
    }

    @Test
    public void getPane() {
        ElevatorViewImpl elevatorView = new ElevatorViewImpl(NUMBER_OF_FLOORS);

        Pane pane = elevatorView.getPane();
        Assert.assertEquals(pane.getChildren().size(), 12);

        Set<Node> buttons = pane.lookupAll(".button");
        Assert.assertEquals(2 * NUMBER_OF_FLOORS, buttons.size());


        Set<Node> cabins = pane.lookupAll(".cabin");
        Assert.assertEquals(1, cabins.size());
    }

}