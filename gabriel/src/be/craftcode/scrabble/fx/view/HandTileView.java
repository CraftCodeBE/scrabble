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
        setHeight(50);
        setWidth(50);
        label.setPrefSize(50,50);
        setSpacing(5);
        setPadding(new Insets(10));
        getChildren().add(label);

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
