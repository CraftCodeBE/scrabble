package be.craftcode.scrabble.model.board;

import be.craftcode.scrabble.exceptions.CouldNotPlaceTileException;
import be.craftcode.scrabble.helpers.BoardHelper;
import be.craftcode.scrabble.model.Scrabble;
import be.craftcode.scrabble.model.player.ScrabblePlayer;

import java.util.LinkedList;
import java.util.List;

public class Board {
    private final BoardTile[][] tiles = new BoardTile[15][15];

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

    public BoardTile[][] getTiles() {
        return tiles;
    }

    public boolean set(int row, int column, Tile tile) throws CouldNotPlaceTileException {
        try {
            BoardTile boardTile = tiles[row][column];
            if(boardTile.getTile() == null) {
                tile.setBoardTile(boardTile);
                boardTile.setTile(tile);
            }
            else
                throw new CouldNotPlaceTileException("This tile already contains an value: "+boardTile + " Trying to place: "+tile);
        }catch (ArrayIndexOutOfBoundsException e){
            e.printStackTrace();
            throw new CouldNotPlaceTileException(String.format("This tile is out of limits:: row: %d column: %d", row, column));
        }
        return true;
    }


    public void print(){
//        System.out.println("------------------------------");
//        for (int i = 0; i < tiles.length; i++) {
//            for (int j = 0; j < tiles[i].length; j++) {
//                System.out.printf("|%4.4s|", tiles[i][j]);
//            }
//            System.out.println();
//        }
//
//        printLegend();
//        System.out.println("------------------------------");
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

    public void checkTileInitialization(int row, int column, BoardTile tile){
        switch (row){
            case 0:
            case 7:
            case 14:
                setTypeForColumn(column,0, tile, BoardTileType.TRIPLE_WORD_SCORE);
                setTypeForColumn(column,3, tile, BoardTileType.DOUBLE_LETTER_SCORE);
                if(row == 7)
                    setTypeForColumn(column,7, tile, BoardTileType.CENTER);
                else
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
                setTypeForColumn(column,7, tile, BoardTileType.DOUBLE_LETTER_SCORE);
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

    public int calculatePoints(ScrabblePlayer player) {
        int points = calculateBoard(true, player);
        points += calculateBoard(false, player);
        return points;
    }

    private int calculateBoard(boolean row, ScrabblePlayer player){
        int points = 0;
        for (int i = 0;  i < 15; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 15; j++) {
                fillCharacters(row ? i : j, row ? j : i, getTiles(), player, sb);
            }
            if(sb.length() > 0) {
                String longest = BoardHelper.sortByIntAndGetReversed(getAllValidWords(sb), String::length);
                points += BoardHelper.getValueForWord(longest);
            }
        }
        return points;
    }

    //TODO check the best way to calculate words that are made with the first letter of another player.
    private void fillCharacters(int i, int j, BoardTile[][] tiles, ScrabblePlayer player, StringBuilder sb){
        BoardTile boardTile = tiles[i][j];
        if (boardTile.getTile() != null && (boardTile.getTile().getOwner() == player || boardTile.getTile().getCoOwner() == player) ) {
            sb.append(boardTile.getTile().getLetter());
        }
    }

    // this method is in test. There still should give an different between words that does not start with the same prefix
    // for now we will use for calculate only the longest word.
    private List<String> getAllValidWords(StringBuilder sb){
        List<String> words = new LinkedList<>();
        for (int i = 0; i < sb.length(); i++) {
            for (int j = 0; j <= sb.length()-i; j++) {
                String tempWord = sb.substring(i, i+j);
                if (Scrabble.getInstance().getDictionary().contains(tempWord)) {
                    System.out.printf("Found valid word: %s for %d points \n", tempWord, BoardHelper.getValueForWord(tempWord));
                    words.add(tempWord);
                }
            }
        }
//        if(!words.isEmpty())
//            System.out.println(words);
        return words;
    }


}
