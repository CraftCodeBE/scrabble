package be.craftcode.scrabble.model.board;

import be.craftcode.scrabble.model.Tile;

public class BoardTile {
    private Tile tile;
    private BoardTileType type = BoardTileType.NORMAL;

    public Tile getTile() {
        return tile;
    }

    public BoardTileType getType() {
        return type;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public void setType(BoardTileType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        if(tile == null)
            return " " + type.getType();
        return tile.getLetter() + " " + type.getType();
    }
}
