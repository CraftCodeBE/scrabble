package be.craftcode.scrabble.model.player;

import be.craftcode.scrabble.exceptions.ScrabbleException;
import be.craftcode.scrabble.fx.presenter.ScrabblePresenter;
import be.craftcode.scrabble.model.Scrabble;
import be.craftcode.scrabble.model.board.Board;
import be.craftcode.scrabble.model.board.BoardTile;
import be.craftcode.scrabble.model.board.Tile;
import be.craftcode.scrabble.model.utils.Movement;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BotAI {
    private ScrabblePlayer player;
    private String currentlyPlayingWord = "";

    public BotAI(ScrabblePlayer owner) {
        this.player = owner;
    }

    public void think(){
        player.printInfo();
        thinkActive();
    }

    /**
     * Looks for placed tiles in the board and checks if rack can make any word out of those tiles.
     */
    private void thinkActive(){
        if(!player.isBot())
            return;
        String wordToPlay = "";
        int row = -1;
        int column = -1;
        String lastLetter = "";
        boolean found = false;
        //filters longest word first.
        List<String> possibleWords = player.getAllPossibleWords().stream().sorted(Comparator.comparingInt(String::length).reversed()).collect(Collectors.toList());
        BoardTile[][] tiles = Scrabble.getInstance().getBoard().getTiles();
        loop: for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[i].length; j++) {
                BoardTile boardTile = tiles[i][j];
                if(boardTile.getTile() != null){
                    //check for board for any placed letter, if matches any of our words then gg
                    for (String allPossibleWord : possibleWords) {
                        lastLetter = String.valueOf(boardTile.getTile().getLetter());
                        if(allPossibleWord.startsWith(lastLetter)){
                            wordToPlay = allPossibleWord;
                            row = i;
                            column = j;
                            found = true;
                            break loop;
                        }
                    }
                }
            }
        }
        handlePlay(found, row, column, wordToPlay, lastLetter);
    }


    /**
     * If no valid word has been found, then look for an random possible word to be placed. Calculates a valid movement depending on random generated coord
     * TODO: check the row/column generation. If board is empty, should always go through 7,7 (center)
     * @param found
     * @param row
     * @param column
     * @param wordToPlay
     * @param lastLetter
     */
    private void handlePlay(boolean found, int row, int column, String wordToPlay, String lastLetter){
        if(!found){
            // TODO handle delete cards, redraw and change turn
            // TODO handle chose own start word and place it
            int tries = 0;

            //small cheat for the bot xD
            if(player.isBot() && player.getAllPossibleWords().isEmpty()){
                player.getRack().clear();
                Scrabble.getInstance().distributeTiles(7, player);
            }

            do{
                String randomWord = new LinkedList<>(player.getAllPossibleWords()).get(new Random().nextInt(player.getAllPossibleWords().size()));
                row = new Random().nextInt(15);
                column = new Random().nextInt(15);
                Movement mov = getMovement(row, column, randomWord.length());
                if(mov != Movement.NONE) {
                    play(row, column, mov, randomWord, "");
                    return;
                }
                tries++;
            }while (tries <= 3); // try 3 times, bcs why not?
            return;
        }

        play(row, column, getMovement(row, column, wordToPlay.length()), wordToPlay, lastLetter);
    }

    /**
     * Finds the ideal movement for the given row-column and desired lettercount.
     * @param row init position
     * @param column init position
     * @param letterCount amount of blocks to check in any position.
     * @return valid movement if there are no blocks between init and lettercount positions.
     */
    private Movement getMovement(int row, int column, int letterCount){
        boolean canGoRight = false;
        boolean canGoLeft = false;
        boolean canGoUp = false;
        boolean canGoDown = false;

        //checks for the next upcoming cell to see if we can move that direction
        int columnToCheckRight = column + 1;
        int columnToCheckLeft = column - 1;
        int rowToCheckUp = row - 1;
        int rowToCheckDown = row + 1;
        if(columnToCheckRight <= 14)
            canGoRight = canGo(row, columnToCheckRight);
        if(columnToCheckLeft  > 0)
            canGoLeft = canGo(row, columnToCheckLeft);

        if(rowToCheckUp >= 0)
            canGoUp = canGo(rowToCheckUp, column);
        if(rowToCheckDown <= 14)
            canGoDown = canGo(rowToCheckDown, column);

        Tile initTile = Scrabble.getInstance().getBoard().getTiles()[row][column].getTile();

        if(canGoRight && checkBoardForCrossFilledTiles(row, column, row, column + letterCount, initTile)){
            return Movement.RIGHT;
        }else if(canGoLeft && checkBoardForCrossFilledTiles(row , column - letterCount, row, column, initTile)) {
            return Movement.LEFT;
        }

        if(canGoUp && checkBoardForCrossFilledTiles(row - letterCount, column, row, column, initTile)){
            return Movement.UP;
        }else if(canGoDown && checkBoardForCrossFilledTiles(row , column, row + letterCount, column, initTile)) {
            return Movement.DOWN;
        }

        return Movement.NONE;
    }

    /**
     * Checks if tile on [row][column] is present on the board.
     * @return true if is empty
     */
    private boolean canGo(int row, int column){
        return Scrabble.getInstance().getBoard().getTiles()[row][column].getTile() == null;
    }

    /**
     * Returns true if word can be placed between selected rows and columns
     * returns false if there is any tile in between the given rows
     * @param row init row to start check
     * @param column init column to start check
     * @param endRow end row to finish check
     * @param endColumn end column to finish check
     * @return
     */
    private boolean checkBoardForCrossFilledTiles(int row, int column, int endRow, int endColumn, Tile initTile){
        //TODO find beter way to check this!
        if(row < 0 || column < 0 || endRow < 0 || endColumn < 0 || row > 14 || column > 14 || endRow > 14 || endColumn > 14)
            return false;
        System.out.printf("row: %d column: %d endRow: %d endColumn: %d %n", row, column, endRow, endColumn);
        BoardTile[][] tiles = Scrabble.getInstance().getBoard().getTiles();
        for (int i = row; i <= endRow; i++) {
            for (int j = column; j <= endColumn; j++) {
                BoardTile boardTile = tiles[i][j];
                if(boardTile.getTile() == initTile)
                    continue;
                System.out.println(i + " ||| " + j);
                if(boardTile.getTile() != null){
                    System.out.println("Return at: "+boardTile.getTile());
                    return false;
                }
            }
        }

        return true;
    }

    private void play(int row, int column, Movement mov, int letterCountMin, int letterCountMax, String validStartLetter) {
        play(row, column, mov, player.getWordWithLength(validStartLetter, letterCountMin, letterCountMax), validStartLetter);
    }

    /**
     * Set an word using one or none valid found letter from the board.
     * @param row
     * @param column
     * @param mov
     * @param wordToPlay
     * @param validStartLetter
     */
    private void play(int row, int column, Movement mov, String wordToPlay, String validStartLetter) {
        boolean valid = false;
        while (!valid) {
            try {
                currentlyPlayingWord = wordToPlay;
                valid = true;
            } catch (ScrabbleException e) {
                //give up on some tiles and redraw new ones.
                if(player.isBot()){ // this just for playable experience. TODO effectively change how this works.
                    for (int i = 0; i < new Random().nextInt(player.getRack().size()); i++) {
                        player.getRack().remove(0);
                        Scrabble.getInstance().distributeTiles(1, player);
                    }
                }

            }
        }

        System.out.println("Currently playing word: " + currentlyPlayingWord);

        //manipulate coord to place according the direction if validStartLetter has been placed.
        if (!validStartLetter.isEmpty()) {
            row += mov.getRowMod();
            column += mov.getColumnMod();
        }

        Board board = Scrabble.getInstance().getBoard();
        for (Tile tile : player.getTilesForWord(validStartLetter, currentlyPlayingWord)) {
            try {
                if (board.set(row, column, tile)) {
//                    lastMove = new int[]{row, column};
                    ScrabblePresenter.getInstance().getView().getSideInfo().setPoints(board.calculatePoints(tile.getOwner()), true);
                    ScrabblePresenter.getInstance().updateAroundTilesToCoOwner(row, column);
                    board.print();
                    row += mov.getRowMod();
                    column += mov.getColumnMod();
                    player.addPlayedTile(tile);
                    player.getRack().remove(tile);
                }
            } catch (Exception e) {
                e.printStackTrace();
                board.print();
            }
        }
        player.printInfo();
//        player.increaseScore(currentlyPlayingWord);
        System.out.println("Currently played word: " + currentlyPlayingWord);
    }
}
