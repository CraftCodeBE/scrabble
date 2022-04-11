package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.model.board.BoardTile;
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
    private BoardTile boardTile;

    public TileView(String content, int[] loc) {
        btn = new Button("   ");
        label = new Label(content);
        label.setPrefSize(20,20);
        this.loc = loc;
        setSpacing(5);
        setPadding(new Insets(10));
        getChildren().addAll(btn, label);
        boardTile = new BoardTile();
    }

    public String getLocString(){
        return loc[0] + " | "+loc[1];
    }

    public BoardTile getBoardTile() {
        return boardTile;
    }

    public void update(){
        if(boardTile.getTile() != null)
            btn.setText(boardTile.getTile().toString());
        label.setText(boardTile.getType().getType());
        setBackground(new Background(new BackgroundFill(boardTile.getType().getColor(), null, null)));
    }

    public boolean hasTile(){
        return getBoardTile().getTile() != null;
    }
}
