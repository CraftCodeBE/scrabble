package be.craftcode.scrabble.model.board;

import be.craftcode.scrabble.helpers.TileHelper;
import be.craftcode.scrabble.model.board.BoardTile;
import be.craftcode.scrabble.model.player.ScrabblePlayer;
import be.craftcode.scrabble.model.utils.Position;

/**
 * Tile Class. It will hold the actual value of the tile
 */
public class Tile {
    private final char letter;
    private final int value;
    private ScrabblePlayer owner;
    private ScrabblePlayer coOwner;
    private final Position lastPosition = new Position();

    public Tile(char letter) {
        this.letter = letter;
        this.value = TileHelper.getPointForLetter(letter);
    }

    public char getLetter() {
        return letter;
    }

    public int getValue() {
        return value;
    }

    public ScrabblePlayer getOwner() {
        return owner;
    }

    public void setOwner(ScrabblePlayer owner) {
        this.owner = owner;
    }

    public ScrabblePlayer getCoOwner() {
        return coOwner;
    }

    public void setCoOwner(ScrabblePlayer coOwner) {
        this.coOwner = coOwner;
    }

    public Position getLastPosition() {
        return lastPosition;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", getLetter(), getValue());
    }
}
