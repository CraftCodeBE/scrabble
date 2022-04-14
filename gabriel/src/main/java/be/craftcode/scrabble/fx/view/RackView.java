package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.model.board.Tile;
import be.craftcode.scrabble.model.player.ScrabblePlayer;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class RackView extends BorderPane {
    private static final int tileViewPrefWidth = 98;
    private final ScrabblePlayer player;
    private final boolean isPlayer;
    private final List<HandTileView> handTileViewList = new LinkedList<>();

    public RackView(ScrabblePlayer player, boolean isPlayer) {
        this.player = player;
        this.isPlayer = isPlayer;

        setPrefSize(1920,100);

        setPadding(new Insets(10));
        fillFromRack();
        setBackground(new Background(new BackgroundFill(Color.CYAN, null, null)));
    }

    public void fillFromRack(){
        for (HandTileView tileView : handTileViewList) {
            getChildren().remove(tileView);
        }
        handTileViewList.clear();

        int i = 1;
        final int spacingNumber = 110; //TODO unhardcode this!
        for (Tile tile : player.getRack()) {
            double x = spacingNumber*(i+1) + tileViewPrefWidth*i;
            addTile(tile, x, 30);
            i++;
        }
    }

    public HandTileView addTile(Tile tile, double x, double y){
        HandTileView tileView = new HandTileView(tile, isPlayer);
        tileView.setPrefSize(tileViewPrefWidth, 80);
        getChildren().addAll(tileView.update());
        handTileViewList.add(tileView);
        BorderPane.setMargin(tileView, new Insets(100));
        tileView.relocate(x, y);
        tile.getLastPosition().setXY(x,y);
        return tileView;
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
