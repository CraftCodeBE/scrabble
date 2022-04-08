package be.craftcode.scrabble.model.board;

public enum BoardTileType {
    NORMAL(" "),
    TRIPPLE_LETTER_SCORE("3l"),
    DOUBLE_LETTER_SCORE("2l"),
    TRIPLE_WORD_SCORE("3w"),
    DOUBLE_WORD_SCORE("2w"),
    CENTER("*"),
    ;

    private String type;

    BoardTileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return String.format("(%s) %s", type, name());
    }
}
