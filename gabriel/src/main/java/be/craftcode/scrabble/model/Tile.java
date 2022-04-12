package be.craftcode.scrabble.model;

import be.craftcode.scrabble.helpers.TileHelper;
import be.craftcode.scrabble.model.board.BoardTile;
import be.craftcode.scrabble.model.player.ScrabblePlayer;
import be.craftcode.scrabble.model.utils.Position;

public class Tile {
    private final char letter;
    private final int value;
    private BoardTile boardTile;
    private ScrabblePlayer owner;
    private ScrabblePlayer coOwner;
    private Position lastPosition = new Position();

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

    public BoardTile getBoardTile() {
        return boardTile;
    }

    public void setBoardTile(BoardTile boardTile) {
        this.boardTile = boardTile;
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

    public void setLastPosition(Position lastPosition) {
        this.lastPosition = lastPosition;
    }

    public Position getLastPosition() {
        return lastPosition;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", getLetter(), getValue());
    }
}
