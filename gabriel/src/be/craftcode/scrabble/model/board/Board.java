package be.craftcode.scrabble.model.board;

public class Board {
    BoardTile[][] tiles = new BoardTile[15][15];

    public Board() {
        init();
    }

    private void init() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                BoardTile boardTile = tiles[i][j];
                if(boardTile == null){
                    boardTile = new BoardTile();
                    checkTileInitialization(i, j, boardTile);
                    tiles[i][j] = boardTile;
                }
            }
        }
    }

    public void print(){
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                System.out.printf("|%4.4s|", tiles[i][j]);
            }
            System.out.println();
        }

        printLegend();
    }

    private void printLegend(){
        System.out.print("Legend");
        for (BoardTileType value : BoardTileType.values()) {
            if(value == BoardTileType.NORMAL)
                continue;
            System.out.print(" | "+value);
        }
        System.out.println();
    }

    private void checkTileInitialization(int row, int column, BoardTile tile){
        switch (row){
            case 0:
            case 7:
            case 14:
                setTypeForColumn(column,0, tile, BoardTileType.TRIPLE_WORD_SCORE);
                setTypeForColumn(column,3, tile, BoardTileType.DOUBLE_LETTER_SCORE);
                setTypeForColumn(column,7, tile, BoardTileType.TRIPLE_WORD_SCORE);
                setTypeForColumn(column,11, tile, BoardTileType.DOUBLE_LETTER_SCORE);
                setTypeForColumn(column,14, tile, BoardTileType.TRIPLE_WORD_SCORE);
                break;
            case 1:
            case 13:
                setTypeForColumn(column,1, tile, BoardTileType.DOUBLE_WORD_SCORE);
                setTypeForColumn(column,5, tile, BoardTileType.TRIPPLE_LETTER_SCORE);
                setTypeForColumn(column,9, tile, BoardTileType.TRIPPLE_LETTER_SCORE);
                setTypeForColumn(column,13, tile, BoardTileType.DOUBLE_WORD_SCORE);
                break;
            case 2:
            case 12:
                setTypeForColumn(column,2, tile, BoardTileType.DOUBLE_WORD_SCORE);
                setTypeForColumn(column,6, tile, BoardTileType.DOUBLE_LETTER_SCORE);
                setTypeForColumn(column,8, tile, BoardTileType.DOUBLE_LETTER_SCORE);
                setTypeForColumn(column,12, tile, BoardTileType.DOUBLE_WORD_SCORE);
                break;
            case 3:
            case 11:
                setTypeForColumn(column,0, tile, BoardTileType.DOUBLE_LETTER_SCORE);
                setTypeForColumn(column,3, tile, BoardTileType.DOUBLE_WORD_SCORE);
                setTypeForColumn(column,11, tile, BoardTileType.DOUBLE_WORD_SCORE);
                setTypeForColumn(column,14, tile, BoardTileType.DOUBLE_LETTER_SCORE);
                break;
            case 4:
            case 10:
                setTypeForColumn(column,4, tile, BoardTileType.DOUBLE_WORD_SCORE);
                setTypeForColumn(column,10, tile, BoardTileType.DOUBLE_WORD_SCORE);
                break;
            case 5:
            case 9:
                setTypeForColumn(column,1, tile, BoardTileType.TRIPPLE_LETTER_SCORE);
                setTypeForColumn(column,5, tile, BoardTileType.TRIPPLE_LETTER_SCORE);
                setTypeForColumn(column,9, tile, BoardTileType.TRIPPLE_LETTER_SCORE);
                setTypeForColumn(column,13, tile, BoardTileType.TRIPPLE_LETTER_SCORE);
                break;
            case 6:
            case 8:
                setTypeForColumn(column,2, tile, BoardTileType.DOUBLE_LETTER_SCORE);
                setTypeForColumn(column,6, tile, BoardTileType.DOUBLE_LETTER_SCORE);
                setTypeForColumn(column,8, tile, BoardTileType.DOUBLE_LETTER_SCORE);
                setTypeForColumn(column,12, tile, BoardTileType.DOUBLE_LETTER_SCORE);
                break;
        }
    }

    private void setTypeForColumn(int column, int expected, BoardTile tile, BoardTileType type){
        if(column == expected)
            tile.setType(type);
    }


}
