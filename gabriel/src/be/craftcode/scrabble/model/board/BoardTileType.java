package be.craftcode.scrabble.model.board;

public enum BoardTileType {
    NORMAL(" ",1),
    TRIPPLE_LETTER_SCORE("3l",3),
    DOUBLE_LETTER_SCORE("2l",2),
    TRIPLE_WORD_SCORE("3w",3),
    DOUBLE_WORD_SCORE("2w",2),
    CENTER("*",1),
    ;

    private final String type;
    private final int multiplyer;

    BoardTileType(String type, int multiplyer) {
        this.type = type;
        this.multiplyer = multiplyer;
    }

    public String getType() {
        return type;
    }

    public int getMultiplyer() {
        return multiplyer;
    }

    @Override
    public String toString() {
        return String.format("(%s) %s", type, name());
    }
}
