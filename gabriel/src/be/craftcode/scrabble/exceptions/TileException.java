package be.craftcode.scrabble.exceptions;

public class TileException extends RuntimeException {
    public TileException() {
        this("Tile not found!");
    }

    public TileException(String message) {
        super(message);
    }
}
