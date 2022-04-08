package be.craftcode.scrabble.model;

import be.craftcode.scrabble.helpers.TileHelper;
import be.craftcode.scrabble.model.board.BoardTile;

public class Tile {
    private final char letter;
    private final int value;
    private BoardTile boardTile;

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

    @Override
    public String toString() {
        return String.format("%s (%d)", getLetter(), getValue());
    }
}
