package be.craftcode.scrabble.fx.presenter;

import be.craftcode.scrabble.Scrabble;
import be.craftcode.scrabble.fx.model.ScrabbleModel;
import be.craftcode.scrabble.fx.view.HandTileView;
import be.craftcode.scrabble.fx.view.MainView;
import be.craftcode.scrabble.fx.view.TileView;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class ScrabblePresenter {
    private ScrabbleModel model;
    private MainView view;

    public ScrabblePresenter(ScrabbleModel model, MainView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void updateView() {
        view.getPlayer().getHandTileViewList().forEach(HandTileView::update);
        for (TileView[] tile : view.getView().getTiles()) {
            for (TileView tileView : tile) {
                tileView.update();
            }
        }
    }

    private void addEventHandlers() {
        view.getPlayer().getHandTileViewList().forEach(e -> {
            e.setOnMouseClicked(event -> {
                if(!e.isPlayer())
                    return;
                System.out.println("Selected: "+e.getTile());
                Scrabble.getInstance().setSelectedTile(e.getTile());
                e.update();
                updateView();
            });

        });

        for (TileView[] tile : view.getView().getTiles()) {
            for (TileView tileView : tile) {
                tileView.setOnMouseClicked(event -> {
                    System.out.println("clicked on loc: "+tileView.getLocString());
                    if(Scrabble.getInstance().getSelectedTile() != null){
                        tileView.getBoardTile().setTile(Scrabble.getInstance().getSelectedTile());
                        Scrabble.getInstance().setSelectedTile(null);
                        tileView.update();
                        view.getPlayer().removeChildren(tileView.getBoardTile().getTile());
                    }
                });
            }
        }
    }


}
