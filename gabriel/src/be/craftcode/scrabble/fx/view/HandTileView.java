package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.Scrabble;
import be.craftcode.scrabble.model.Tile;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class HandTileView extends HBox {
    private final Label label;
    private Tile tile;
    private boolean isPlayer;

    public HandTileView(Tile tile, boolean isPlayer) {
        label = new Label(isPlayer ? tile.toString() : "  ");
        this.tile = tile;
        this.isPlayer = isPlayer;
        setHeight(100);
        setWidth(80);
        label.setPrefSize(80,100);
        setSpacing(5);
        setPadding(new Insets(10));
        getChildren().add(label);

        if(isPlayer) {
            draggable(this);
        }
    }

    private static class Position {
        double x;
        double y;
    }

    /**
     * https://developpaper.com/javafx-to-achieve-the-effect-of-dragging-nodes/
     * @param node
     */
    private void draggable(Node node) {
        final Position pos = new Position();

        //Prompt the user that the node can be clicked
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> node.setCursor(Cursor.HAND));
        node.addEventHandler(MouseEvent.MOUSE_EXITED, event -> node.setCursor(Cursor.DEFAULT));

        //Prompt the user that the node can be dragged
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            node.setCursor(Cursor.MOVE);

            //When a press event occurs, the location coordinates of the event are cached
            pos.x = event.getX();
            pos.y = event.getY();
        });
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> node.setCursor(Cursor.DEFAULT));

        //Realize drag and drop function
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            double distanceX = event.getX() - pos.x;
            double distanceY = event.getY() - pos.y;

            double x = node.getLayoutX() + distanceX;
            double y = node.getLayoutY() + distanceY;

            //After calculating X and y, relocate the node to the specified coordinate point (x, y)
            node.relocate(x, y);
        });
    }

    public HandTileView update(){
        setBackground(new Background(new BackgroundFill(Scrabble.getInstance().getSelectedTile() == tile ? Color.GREEN : Color.SANDYBROWN, null, null)));
        return this;
    }

    public Tile getTile() {
        return tile;
    }

    public boolean isPlayer() {
        return isPlayer;
    }
}
