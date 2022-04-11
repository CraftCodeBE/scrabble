package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.Scrabble;
import be.craftcode.scrabble.model.board.BoardTile;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

public class ScrabbleView extends GridPane {
    private final TileView[][] tiles = new TileView[15][15];
    private final Scrabble scrabble = Scrabble.getInstance();

    public ScrabbleView() {
        init();
    }

    private void init() {
        BoardTile[][] boardTiles = scrabble.getBoard().getTiles();
        for (int i = 0; i < boardTiles.length; i++) {
            for (int j = 0; j < boardTiles[i].length; j++) {
                BoardTile c = boardTiles[i][j];
                TileView boardTile = tiles[i][j];
                if(boardTile == null){
                    boardTile = new TileView(i+"|"+j, new int[]{i,j}, c);
                    tiles[i][j] = boardTile;
                    boardTile.update();
                    add(boardTile, i, j);
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
