package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.model.board.BoardTile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;

public class TileView extends HBox {
    private final int[] loc;
    private final Button value;
    private final Label label;
    private BoardTile boardTile;

    public TileView(String content, int[] loc) {
        value = new Button("   ");
        label = new Label(content);
        label.setPrefSize(20,20);
        this.loc = loc;
        setSpacing(5);
        setPadding(new Insets(10));
        getChildren().addAll(value, label);
        boardTile = new BoardTile();

        setOnMouseClicked(event -> {
            System.out.println("clicked on loc: "+getLocString());

        });
//
//        setOnMouseEntered(event -> {
//            System.out.println("entered on loc: "+getLocString());
//
//        });
//
//        setOnMouseExited(event -> {
//            System.out.println("exited on loc: "+getLocString());
//        });

    }

    public String getLocString(){
        return loc[0] + " | "+loc[1];
    }

    public BoardTile getBoardTile() {
        return boardTile;
    }

    public void update(){
        label.setText(boardTile.getType().getType());
        setBackground(new Background(new BackgroundFill(boardTile.getType().getColor(), null, null)));
    }
}
