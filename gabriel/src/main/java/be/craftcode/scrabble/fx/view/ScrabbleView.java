package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.Scrabble;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class ScrabbleView extends GridPane {
    private final TileView[][] tiles = new TileView[15][15];
    private final Scrabble scrabble = Scrabble.getInstance();

    public ScrabbleView() {
        init();
    }

    private void init() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                TileView boardTile = tiles[i][j];
                if(boardTile == null){
                    boardTile = new TileView(i+"|"+j, new int[]{i,j});
                    scrabble.getBoard().checkTileInitialization(i, j, boardTile.getBoardTile());
                    tiles[i][j] = boardTile;
                    boardTile.update();
                    this.add(boardTile, i, j);
                }
            }
        }

        setGridLinesVisible(true);
        setAlignment(Pos.CENTER);
        setHgap(2);
        setVgap(2);
    }

    public TileView[][] getTiles() {
        return tiles;
    }
}
