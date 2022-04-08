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
        view.getPlayer().getHandTileViewList().forEach(e -> {
            e.update();
            draggable(e);
        });
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
//                tileView.setOnMouseDragReleased(event -> {
//                    System.out.println("setOnMouseDragReleased!");
//                });
//                tileView.setOnMouseDragEntered(event -> {
//                    System.out.println("setOnMouseDragEntered!");
//                });
            }
        }
    }



    private static class Position {
        double x;
        double y;
    }

    /**
     * https://developpaper.com/javafx-to-achieve-the-effect-of-dragging-nodes/
     * @param node
     */
    private void draggable(HandTileView node) {
        final Position pos = new Position();

        //Prompt the user that the node can be clicked
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> node.setCursor(Cursor.HAND));
        node.addEventHandler(MouseEvent.MOUSE_EXITED, event -> node.setCursor(Cursor.DEFAULT));

        //Prompt the user that the node can be dragged
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
//            Scrabble.getInstance().setSelectedTile(node.getTile());
            node.setCursor(Cursor.MOVE);

            //When a press event occurs, the location coordinates of the event are cached
            pos.x = event.getX();
            pos.y = event.getY();
        });
        node.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            node.setCursor(Cursor.DEFAULT);

//            node.update();
//            updateView();

//            if(Scrabble.getInstance().getSelectedTile() != null){
//                tileView.getBoardTile().setTile(Scrabble.getInstance().getSelectedTile());
//                Scrabble.getInstance().setSelectedTile(null);
//                tileView.update();
//                view.getPlayer().removeChildren(tileView.getBoardTile().getTile());
//            }

        });

        //Realize drag and drop function
        node.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {

            double distanceX = event.getX() - pos.x;
            double distanceY = event.getY() - pos.y;

            double x = node.getLayoutX() + distanceX;
            double y = node.getLayoutY() + distanceY;

            //After calculating X and y, relocate the node to the specified coordinate point (x, y)
//            System.out.println("RELOCATING: "+x +" || "+y);
            node.relocate(x, y);
        });
    }


}
