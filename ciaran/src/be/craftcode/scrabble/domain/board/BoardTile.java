package be.craftcode.scrabble.domain.board;

import be.craftcode.scrabble.domain.Letter;

public class BoardTile {
    private Letter tile;
    private BoardTileType type = BoardTileType.NORMAL;

    public Letter getTile() {
        return tile;
    }

    public BoardTileType getType() {
        return type;
    }

    public void setTile(Letter tile) {
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
