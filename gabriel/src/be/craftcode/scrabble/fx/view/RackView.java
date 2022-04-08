package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.model.Tile;
import be.craftcode.scrabble.model.board.BoardTile;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class RackView extends HBox {
    private Tile tile;
    private final Button value;
    private final Label label;

    public RackView(String content, Tile tile) {
        this.tile = tile;
        value = new Button("   ");
        label = new Label(content);
        label.setPrefSize(20,20);
        setSpacing(5);
        setPadding(new Insets(10));
        getChildren().addAll(value, label);

        setOnMouseClicked(event -> {
            System.out.println("clicked on loc: "+tile.toString());

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

    public void update(){
        setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
    }
}
