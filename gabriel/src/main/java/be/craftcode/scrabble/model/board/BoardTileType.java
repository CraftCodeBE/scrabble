package be.craftcode.scrabble.model.board;
import javafx.scene.paint.Color;

public enum BoardTileType {
    NORMAL(" ",1, Color.TRANSPARENT),
    TRIPPLE_LETTER_SCORE("3l",3, Color.LIGHTBLUE),
    DOUBLE_LETTER_SCORE("2l",2, Color.AQUAMARINE),
    TRIPLE_WORD_SCORE("3w",3, Color.RED),
    DOUBLE_WORD_SCORE("2w",2, Color.PINK),
    CENTER("*",1, Color.GREY),
    ;

    private final String type;
    private final int multiplyer;
    private final Color color;

    BoardTileType(String type, int multiplyer, Color color) {
        this.type = type;
        this.multiplyer = multiplyer;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public int getMultiplyer() {
        return multiplyer;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return String.format("(%s) %s", type, name());
    }
}
