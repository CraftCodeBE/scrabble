package be.craftcode.scrabble.fx.presenter;

import be.craftcode.scrabble.Scrabble;
import be.craftcode.scrabble.fx.view.HandTileView;
import be.craftcode.scrabble.fx.view.MainView;
import be.craftcode.scrabble.fx.view.TileView;
import be.craftcode.scrabble.model.Tile;
import be.craftcode.scrabble.model.player.ScrabblePlayer;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.input.*;
import javafx.scene.layout.Pane;

import java.util.Optional;

public class ScrabblePresenter {
    private final Scrabble model;
    private final MainView view;

    public ScrabblePresenter(Scrabble model, MainView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        updateView(true);
    }

    private void updateView(boolean refreshSide) {
        view.getPlayer().getHandTileViewList().forEach(HandTileView::update);
        for (TileView[] tile : view.getView().getTiles()) {
            for (TileView tileView : tile) {
                tileView.update();
            }
        }

        if(refreshSide)
            updateSideInfo(view.getPlayer().getOwner());
    }

    private void updateSideInfo(ScrabblePlayer player){
        player.printInfo();
        view.getSideInfo().getRackContent().setText("Rack Content "+player.getRack().toString());
        view.getSideInfo().getPossibleWordsWithMaxLenght().setText("Possible words with Max Length "+player.getPossibleWordsWithMaxLength().toString());
        view.getSideInfo().getAllPossibleWords().setText("All Possible "+player.getAllPossibleWords().toString());
        view.getSideInfo().getLongestWordPossible().setText("Longest "+player.getLongestWord());
        view.getSideInfo().getLongestScoringWord().setText("Longest Scoring: "+player.getLongestScoringWord());

    }

    private Optional<Node> findNode(Pane pane, double x, double y) {
        return pane.getChildren().stream().filter(n -> {
            Point2D point = n.sceneToLocal(x, y);
            return n.contains(point.getX(), point.getY());
        }).findAny();
    }

    private void setTile(TileView tileView){
        if(model.getSelectedTile() != null && !tileView.hasTile()){
            // todo check if current playing player is the actual owner of tile.
            tileView.getBoardTile().setTile(model.getSelectedTile());
            view.getPlayer().getOwner().getRack().remove(model.getSelectedTile());
            tileView.update();
            view.getPlayer().removeChildren(tileView.getBoardTile().getTile());

            view.getSideInfo().setPoints(model.calculatePoints(model.getSelectedTile().getOwner()));
            model.setSelectedTile(null);

        }

    }

    private void addEventHandlers() {
        view.getPlayer().getHandTileViewList().forEach(this::draggable);

        // register dragged tile on board
        view.setOnMouseEntered(event -> {
//            System.out.println("view setOnMouseEntered");
//            System.out.println("Mouse event at: "+event.getX() + " || " + event.getY());
            Optional<Node> maybeTileView = findNode(view.getView(), event.getX(), event.getY());
            if(maybeTileView.isPresent() && maybeTileView.get() instanceof TileView){
                TileView tileView = (TileView) maybeTileView.get();
                setTile(tileView);
            }
            event.consume();
        });

        for (TileView[] tile : view.getView().getTiles()) {
            for (TileView tileView : tile) {
                tileView.setOnMouseClicked(event -> {
//                    System.out.println("clicked on loc: "+tileView.getLocString());
                    setTile(tileView);
                });
                tileView.getBtn().setOnMouseClicked(mouseEvent -> {
                    if(tileView.hasTile()){
                        // todo add current user check with tile owner
//                        ScrabblePlayer owner = tileView.getBoardTile().getTile().getOwner();
                        Tile toAdd = tileView.getBoardTile().getTile();
                        toAdd.getOwner().getRack().add(toAdd);
                        HandTileView returnedTile = view.getPlayer().addTile(toAdd, toAdd.getLastPosition().getX(), toAdd.getLastPosition().getY());
                        returnedTile.update();
                        draggable(returnedTile);
                        tileView.resetTile();
//                        updateView(false);
                    }
                });
            }
        }

        view.getSideInfo().getButtonRefresh().setOnMouseClicked(mouseEvent -> {
            view.getPlayer().getOwner().refreshCanMakeWords();
            updateSideInfo(view.getPlayer().getOwner());
        });


    }

    /**
     * https://developpaper.com/javafx-to-achieve-the-effect-of-dragging-nodes/
     * https://stackoverflow.com/questions/54357180/javafx-mouse-drag-events-not-firing
     * @param node
     */
    private void draggable(HandTileView node) {
        node.setOnDragOver(event -> moveNode(event, node));
        node.setOnDragDetected(event -> {
//            System.out.println("Drag detected!");
            /* allow any transfer mode */
            Dragboard db = node.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(node.getTile().toString());
            db.setContent(content);
//
            event.consume();
        });

        //Prompt the user that the node can be clicked
        node.addEventHandler(MouseEvent.MOUSE_ENTERED, event -> node.setCursor(Cursor.HAND));
        node.addEventHandler(MouseEvent.MOUSE_EXITED, event -> node.setCursor(Cursor.DEFAULT));

        //Prompt the user that the node can be dragged
        node.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            node.setCursor(Cursor.MOVE);

            //When a press event occurs, the location coordinates of the event are cached
            node.getPos().setX(event.getX());
            node.getPos().setY(event.getY());

            if(!node.isPlayer())
                return;

            model.setSelectedTile(node.getTile());
            node.update();
            updateView(false);

        });

        node.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> node.setCursor(Cursor.DEFAULT));
    }

    private void moveNode(DragEvent event, HandTileView node){
        double distanceX = event.getX() - node.getPos().getX();
        double distanceY = event.getY() - node.getPos().getY();

        double x = node.getLayoutX() + distanceX;
        double y = node.getLayoutY() + distanceY;

        node.getTile().getLastPosition().setXY(x,y);
        //After calculating X and y, relocate the node to the specified coordinate point (x, y)
        node.relocate(x, y);
    }
}
