package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.model.Tile;
import be.craftcode.scrabble.model.player.ScrabblePlayer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RackView extends BorderPane {
    private ScrabblePlayer player;
    private boolean isPlayer;
    private List<HandTileView> handTileViewList = new LinkedList<>();
//    private final Button value;
//    private final Label label;

    public RackView(ScrabblePlayer player, boolean isPlayer) {
        this.player = player;
        this.isPlayer = isPlayer;

        setPrefSize(1920,100);

//        setSpacing(5);
        setPadding(new Insets(10));
        int i = 1;
        final int spacingNumber = 110; //TODO unhardcode this!
        for (Tile tile : player.getRack()) {
            HandTileView tileView = new HandTileView(tile, isPlayer);

            tileView.setPrefSize(98, 80);
            getChildren().addAll(tileView.update());
            handTileViewList.add(tileView);

            BorderPane.setMargin(tileView, new Insets(100));

            double x = spacingNumber*(i+1) + tileView.getPrefWidth()*i;
            tileView.relocate(x, 30);  //TODO unhardcode this!

            System.out.println(isPlayer + " ||| "+ tileView.getLayoutX() + " || "+tileView.getLayoutY());
            i++;

        }
        System.out.println(isPlayer + " |||getHeight "+ getHeight() + " ||getWidth "+getWidth());

//        BorderPane.setMargin(this, new Insets(10,10,10,10));

        setBackground(new Background(new BackgroundFill(Color.CYAN, null, null)));
    }

    public void update(){
        handTileViewList.forEach(HandTileView::update);
    }

    public List<HandTileView> getHandTileViewList() {
        return handTileViewList;
    }

    public void removeChildren(Tile toRemove){
        Optional<HandTileView> temp = handTileViewList.stream().filter(e->e.getTile() == toRemove).findFirst();
        if(temp.isEmpty()) {
            System.out.println("Tile not found!");
            return;
        }
        removeChildren(temp.get());
    }

    public void removeChildren(HandTileView toRemove){
        handTileViewList.remove(toRemove);
        getChildren().remove(toRemove);
        update();
    }

    public ScrabblePlayer getOwner() {
        return player;
    }
}
