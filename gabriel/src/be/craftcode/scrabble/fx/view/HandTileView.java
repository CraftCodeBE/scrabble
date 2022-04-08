package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.Scrabble;
import be.craftcode.scrabble.model.Tile;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import jfxtras.labs.util.event.MouseControlUtil;

public class HandTileView extends HBox {
    private final Button btn;
    private final Label label;
    private Tile tile;
    private boolean isPlayer;

    public HandTileView(Tile tile, boolean isPlayer) {
        btn = new Button("   ");
        label = new Label(isPlayer ? tile.toString() : "  ");
        this.tile = tile;
        this.isPlayer = isPlayer;
        setHeight(80);
        setWidth(40);
        label.setPrefSize(40,20);
        setSpacing(5);
        setPadding(new Insets(10));
        if(isPlayer)
            getChildren().add(btn);
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
