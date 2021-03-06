package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.model.Scrabble;
import be.craftcode.scrabble.model.board.Tile;
import be.craftcode.scrabble.model.utils.Position;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class HandTileView extends HBox {
    private final Tile tile;
    private final boolean isPlayer;
    private final Position pos = new Position();


    public HandTileView(Tile tile, boolean isPlayer) {
        Label label = new Label(isPlayer ? tile.toString() : "  ");
        this.tile = tile;
        this.isPlayer = isPlayer;
        setHeight(50);
        setWidth(100);
        label.setPrefSize(50,50);
        setPrefSize(100,50);
        setSpacing(5);
        setPadding(new Insets(10));
        getChildren().add(label);
        getStyleClass().add("handTile");

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

    public Position getPos() {
        return pos;
    }
}
