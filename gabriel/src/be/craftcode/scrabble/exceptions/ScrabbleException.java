package be.craftcode.scrabble.exceptions;

public class ScrabbleException extends RuntimeException {
    public ScrabbleException() {
        this("Tile not found!");
    }

    public ScrabbleException(String message) {
        super(message);
    }
}
