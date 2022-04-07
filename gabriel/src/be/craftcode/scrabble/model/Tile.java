package be.craftcode.scrabble.model;

import be.craftcode.scrabble.helpers.TileHelper;

public class Tile {
    private final char letter;
    private final int value;

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

    @Override
    public String toString() {
        return String.format("%s (%d)", getLetter(), getValue());
    }
}
