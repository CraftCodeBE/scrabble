package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.model.board.BoardTile;
import be.craftcode.scrabble.model.utils.Position;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;

public class TileView extends HBox {
    private final int[] loc;
    private final Button btn;
    private final Label label;
    private final BoardTile boardTile;
    private boolean isLocked;

    public TileView(String content, int[] loc, BoardTile tile) {
        btn = new Button("  ");
        label = new Label(content);
        label.setPrefSize(30,60);
        btn.setPrefSize(60,60);
        this.loc = loc;
        this.boardTile = tile;
        setSpacing(5);
        setPadding(new Insets(10));
        getChildren().addAll(btn, label);
    }

    public int[] getLoc() {
        return loc;
    }

    public String getLocString(){
        return loc[0] + " | "+loc[1];
    }

    public BoardTile getBoardTile() {
        return boardTile;
    }

    public void update(){
        btn.setText(boardTile.getTile() != null ? boardTile.getTile().toString() : "   ");
        label.setText(boardTile.getType().getType());
        setBackground(new Background(new BackgroundFill(boardTile.getType().getColor(), null, null)));
    }

    public boolean hasTile(){
        return getBoardTile().getTile() != null;
    }

    public Button getBtn() {
        return btn;
    }

    public void resetTile(){
        getBoardTile().setTile(null);
        update();
    }

    public boolean isLocked() {
        return isLocked;
    }

    public void lock(){
        isLocked = true;
    }
}
