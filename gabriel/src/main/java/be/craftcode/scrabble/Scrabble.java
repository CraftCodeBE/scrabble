package be.craftcode.scrabble;

import be.craftcode.scrabble.exceptions.ScrabbleException;
import be.craftcode.scrabble.helpers.BoardHelper;
import be.craftcode.scrabble.model.Tile;
import be.craftcode.scrabble.model.board.Board;
import be.craftcode.scrabble.model.board.BoardTile;
import be.craftcode.scrabble.model.player.ScrabblePlayer;
import be.craftcode.scrabble.model.utils.Movement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * https://github.com/guardian/coding-exercises/tree/main/scrabble
 * <p>
 * Scrabble rules: https://www.youtube.com/watch?v=K1KgvZwwJqo
 * Bag -> Shuffle -> each player takes 1 letter. Closest to A begins.
 * On your turn you place tiles on the board so that you spell a complete word that reads either top to down or left to right.
 * One of your tiles must touch a tile already in play, and if it alters that word it must turn it into a new word.
 * Additionally, any adjacent tiles your tiles touch must also form complete words, similar to a crossword.
 * <p>
 * You score points for each letter in all new words formed during your turn.
 * Some letters will be counted twice if they are in more than 1 new word.
 * If you didn’t change a word you don’t count the points. You are not allowed to shift or move tiles already played.
 * <p>
 * There are also bonus squares on the board. During the turn you place a tile on these squares you apply its effects to every new word that letter affects.
 * Bonus letter squares only affect the letter’s score, while bonus words affect the entire word’s score.
 * The premium star square in the middle of the board has two effects. First, it determines where the first play must be.
 * The first word played must cover that square. It’s second effect is that is doubles score of the first word.
 * <p>
 * If ever you play 7 tiles from your rack you receive a bonus 50 points after your total scoring for that turn.
 * Blank tiles can be used as whatever letter you like, however, once they are played, the letter they represent cannot be changed.
 * At the end of your turn draw tiles from the bag until you have 7 in your rack. Play passes to the left.
 * <p>
 * During your turn, instead of playing letters, you may swap tiles from your rack with new ones from the bag as long as there are more than 7 tiles left in the bag.
 * Place the discarding tiles face down, draw an equal number from the bag and add them to your rack, then place the discarded tiles in the bag.
 */
public class Scrabble {
    private Tile selectedTile;
    private ScrabblePlayer activePlayer;

    public ScrabblePlayer getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(ScrabblePlayer activePlayer) {
        this.activePlayer = activePlayer;
        distributeTiles(7 - activePlayer.getRack().size(), activePlayer);
        if(activePlayer.isBot()){
            think(activePlayer);
        }
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }

    private final int[] center = {7, 7};
    private final List<Tile> bag = new ArrayList<>();
    private List<String> dictionary = new ArrayList<>();

    private final Function<Character, Tile> makeTile = Tile::new;
    private final BiConsumer<Integer, String> fillBag = (i, e) -> {
        for (char c : e.toCharArray()) {
            for (int j = 0; j < i; j++) {
                bag.add(makeTile.apply(c));
            }
        }
    };

    public int calculatePoints(ScrabblePlayer player) {
        int points = calculateBoard(true, player);
        points += calculateBoard(false, player);
        return points;
    }

    public int calculateBoard(boolean row, ScrabblePlayer player){
        int points = 0;
        for (int i = 0;  i < 15; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 15; j++) {
                fillCharacters(row ? i : j, row ? j : i, board.getTiles(), player, sb);
            }
            if(sb.length() > 0) {
                String longest = BoardHelper.sortByIntAndGetReversed(getAllValidWords(sb), String::length);
                points += BoardHelper.getValueForWord(longest);
            }
        }
        return points;
    }

    public void fillCharacters(int i, int j, BoardTile[][] tiles, ScrabblePlayer player, StringBuilder sb){
        BoardTile boardTile = tiles[i][j];
        if (boardTile.getTile() != null && boardTile.getTile().getOwner() == player) {
            sb.append(boardTile.getTile().getLetter());
        }
    }

    // this method is in test. There still should give an different between words that does not start with the same prefix
    // for now we will use for calculate only the longest word.
    public List<String> getAllValidWords(StringBuilder sb){
        List<String> words = new LinkedList<>();
        for (int i = 0; i < sb.length(); i++) {
            for (int j = 0; j <= sb.length()-i; j++) {
                String tempWord = sb.substring(i, i+j);
                if (dictionary.contains(tempWord)) {
                    System.out.printf("Found valid word: %s for %d points \n", tempWord, BoardHelper.getValueForWord(tempWord));
                    words.add(tempWord);
                }
            }
        }
//        if(!words.isEmpty())
//            System.out.println(words);
        return words;
    }

    private static Scrabble instance;

    public static Scrabble getInstance() {
        if (instance == null) {
            instance = new Scrabble();
        }
        return instance;
    }

    public List<String> getDictionary() {
        return new LinkedList<>(dictionary);
    }

    private final Board board;

    public Board getBoard() {
        return board;
    }

    private final List<ScrabblePlayer> players = new LinkedList<>();

    public ScrabblePlayer getPlayer(int index) {
        return players.get(index);
    }

    public List<ScrabblePlayer> getPlayers() {
        return players;
    }

    private Scrabble() {
        loadDictionary();
        dictionary = dictionary.stream().filter(e -> e.length() <= 7).collect(Collectors.toList()); // filter all words with a length > 7 since we cannot create other words with solo player
        board = new Board();
        loadbag();

        players.add(new ScrabblePlayer(1, "Gabriel", false));

        for (ScrabblePlayer player : players) {
            distributeTiles(7, player);
        }

        setActivePlayer(getPlayer(0));

        String word = "guardian";
        System.out.printf("Value for word: %s is: %d  \n", word, BoardHelper.getValueForWord(word));
        board.print();
    }

    public void start() {
        think(players.get(0));
        think(players.get(0));
    }

    private boolean firstPlay = true;
    private String currentlyPlayingWord = "";
    private int[] lastMove = new int[2];

    public void setLastMove(int[] lastMove) {
        this.lastMove = lastMove;
    }

    private void think(ScrabblePlayer player) {
        player.printInfo();
//        if (firstPlay) {
//            play(player, center[0], center[1], Movement.RIGHT, 3, 3);
//            firstPlay = false;
//        } else {
            //draw again since its another turn?
            play(player, lastMove[0], lastMove[1], Movement.UP, 3, 14);
//        }
    }

    private void play(ScrabblePlayer player, int row, int column, Movement mov, int letterCountMin, int letterCountMax) {
        boolean valid = false;
        while (!valid) {
            try {
                currentlyPlayingWord = player.getWordWithLength(board.getLastTileLetter(), letterCountMin, letterCountMax);
                valid = true;
            } catch (ScrabbleException e) {
                //give up on some tiles and redraw new ones.
                System.out.println("REDRAWING for: " + player);
                for (int i = 0; i < new Random().nextInt(player.getRack().size()); i++) {
                    player.getRack().remove(0);
                    distributeTiles(1, player);
                }
            }
        }

        System.out.println("Currently playing word: " + currentlyPlayingWord);

        //manipulate coord to place according the direction if last tile has been placed.
        if (!board.getLastTileLetter().isEmpty()) {
            row += mov.getRowMod();
            column += mov.getColumnMod();
        }

        for (Tile tile : player.getTilesForWord(board.getLastTileLetter(), currentlyPlayingWord)) {
            try {
                if (board.set(row, column, tile)) {
                    lastMove = new int[]{row, column};
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
        System.out.println(player);
    }

    /**
     * Task 2-3 Assign seven tiles chosen randomly from the English alphabet to a player's rack.
     *
     * @param amountOfTiles
     * @param player
     */
    private void distributeTiles(int amountOfTiles, ScrabblePlayer player) {

        if (bag.isEmpty())
            return;
        //shuffle 3 times just bcs ;)
        for (int i = 0; i < 3; i++) {
            Collections.shuffle(bag);
        }

        int tiles = 0;
        for (Iterator<Tile> iterator = bag.iterator(); iterator.hasNext(); ) {
            Tile next = iterator.next();
            if (next == null)
                return;
            next.setOwner(player);
            player.getRack().add(next);
            iterator.remove();
            tiles++;
            if (tiles == amountOfTiles)
                break;
        }

        System.out.println("Rack Size after distributing: " + player.getRack().size());
        System.out.println("Bag Size after distributing: " + bag.size());
    }

    private void loadbag() {
        fillBag.accept(12, "e");
        fillBag.accept(9, "ai");
        fillBag.accept(8, "o");
        fillBag.accept(6, "nrt");
        fillBag.accept(4, "lsud");
        fillBag.accept(3, "g");
        fillBag.accept(2, "bcmpfhvwy");
        fillBag.accept(1, "kjxqz");
        System.out.printf("Bag loaded with %d tiles.\n", bag.size());
    }

    private void loadDictionary() {
        try {
            dictionary = Files.readAllLines(Path.of(System.getProperty("user.dir") + "/../dictionary.txt"));
            System.out.printf("Loading %d valid words. \n", dictionary.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
