package be.craftcode.scrabble.model.utils;

public enum Movement {
    UP(-1,0),
    DOWN(+1,0),
    LEFT(0, -1),
    RIGHT(0, +1);

    int rowMod;
    int columnMod;

    Movement(int rowMod, int columnMod) {
        this.rowMod = rowMod;
        this.columnMod = columnMod;
    }

    public int getRowMod() {
        return rowMod;
    }

    public int getColumnMod() {
        return columnMod;
    }
}
