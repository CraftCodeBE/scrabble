package be.craftcode.scrabble.exceptions;

public class CouldNotPlaceTileException extends RuntimeException {
    public CouldNotPlaceTileException(String message) {
        super(message);
    }
}
