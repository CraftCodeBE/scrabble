package be.craftcode.scrabble;

import be.craftcode.scrabble.helpers.TileHelper;
import be.craftcode.scrabble.model.Tile;
import be.craftcode.scrabble.model.board.Board;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * https://github.com/guardian/coding-exercises/tree/main/scrabble
 */
public class Scrabble {
    private final List<Tile> bag = new ArrayList<>();
    private final List<Tile> rack = new ArrayList<>();
    private final Board board;

    private final Function<Character, Tile> makeTile = Tile::new;
    private final BiConsumer<Integer, String> fillBag = (i, e) -> {
        for (char c : e.toCharArray()) {
            for (int j = 0; j < i; j++) {
                bag.add(makeTile.apply(c));
            }
        }
    };

    public Scrabble() {
        loadDictionary();
        board = new Board();
        loadbag();
        String word = "guardian";
        System.out.printf("Value for word: %s is: %d  \n", word, getValueForWord(word));
        distributeTiles(7, rack);
        System.out.println("Rack content: "+rack);
        board.print();
    }

    /**
     * Task 1 Calculate the score for a word. The score is the sum of the points for the letters that make up a word. For example: GUARDIAN = 2 + 1 + 1 + 1 + 2 + 1 + 1 + 1 = 10.
     * @param word
     * @return
     */
    private int getValueForWord(String word) {
        int value = 0;
        for (char c : word.toLowerCase().toCharArray()) {
            value += TileHelper.getPointForLetter(c);
        }
        return value;
    }

    /**
     * Task 2-3 Assign seven tiles chosen randomly from the English alphabet to a player's rack.
     * @param amountOfTiles
     * @param rack
     */
    private void distributeTiles(int amountOfTiles, List<Tile> rack){

        //shuffle 3 times just bcs ;)
        for (int i = 0; i < 3; i++) {
            Collections.shuffle(bag);
        }

        int tiles = 0;
        for (Iterator<Tile> iterator = bag.iterator(); iterator.hasNext();) {
            Tile next =  iterator.next();
            rack.add(next);
            iterator.remove();
            tiles++;
            if(tiles == amountOfTiles)
                break;
        }

        System.out.println("Rack Size after distributing: "+ rack.size());
        System.out.println("Bag Size after distributing: "+ bag.size());
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
            List<String> lines = Files.readAllLines(Path.of(System.getProperty("user.dir") + "/dictionary.txt"));
            System.out.printf("Loading %d valid words. \n", lines.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
