package be.craftcode.scrabble.model.player;

import be.craftcode.scrabble.Scrabble;
import be.craftcode.scrabble.exceptions.ScrabbleException;
import be.craftcode.scrabble.helpers.BoardHelper;
import be.craftcode.scrabble.model.Tile;

import java.util.*;

public class ScrabblePlayer {
    private final int playerId;
    private final List<Tile> rack = new ArrayList<>();
    List<String> canMakeWords = new ArrayList<>();

    public ScrabblePlayer(int playerId) {
        this.playerId = playerId;
    }

    public List<Tile> getRack() {
        return rack;
    }

    public void printInfo(){
        System.out.println("=========================================");
        System.out.println("Rack content: "+getRack());
        System.out.println("Rack content: "+ BoardHelper.countForRack.apply(getRack()));

        refreshCanMakeWords();

        if(!canMakeWords.isEmpty()){
            String longestWord = BoardHelper.sortByIntAndGetReversed(canMakeWords, String::length);
            Collection<String> possibleWordsWithMaxLength = BoardHelper.getAll(canMakeWords, e->e.length() == longestWord.length());
            System.out.println("Possible words with max length: "+possibleWordsWithMaxLength);
            Collection<String> allPossibleWords = BoardHelper.getAll(canMakeWords, e->e.length() > 0);
            System.out.println("All Possible words: "+allPossibleWords);
            System.out.println("Longest word possible: "+longestWord);
            String longestScoringWord = BoardHelper.sortByIntAndGetReversed(canMakeWords, BoardHelper::getValueForWord);
            System.out.println("Longest scoring word: "+longestScoringWord + " || "+BoardHelper.getValueForWord(longestScoringWord));
        }
    }

    private void refreshCanMakeWords(){
        canMakeWords.clear();
        for (String possibleWord : Scrabble.getInstance().getDictionary()) {
            Map<Character, Long> wordMap = BoardHelper.countForWord.apply(possibleWord);
            Map<Character, Long> myHandTileCount = BoardHelper.countForRack.apply(getRack());
            boolean ok = BoardHelper.canBeUsed(wordMap, myHandTileCount);
//            System.out.println(possibleWord + " || I can make?: "+ ok);
            if(ok) {
                canMakeWords.add(possibleWord);
            }
        }
    }

    public String getWordWithLength(String firstLetter, int min, int max){
        refreshCanMakeWords();
        Optional<String> wordOpt = BoardHelper.getAll(canMakeWords, e-> e.length() >= min && e.length() <= max && (firstLetter.isEmpty() || e.startsWith(firstLetter))).stream().findFirst();
        if(wordOpt.isEmpty())
            throw new ScrabbleException(String.format("Could not find word with the criteria: firstLetter: %s, min %d, max %d. Possibly because you cannot create any valid word.", firstLetter, min, max));
        return wordOpt.get();
    }

    public List<Tile> getTilesForWord(String firstLetter, String word){
        System.out.printf("getTilesForWord:: %s || %s%n", firstLetter, word);
        List<Tile> temp = new LinkedList<>();
        boolean skipped = false;
        for (char c : word.toCharArray()) {
            if(!firstLetter.isEmpty() && !skipped){
                skipped = true;
                continue;
            }
            // if firstLetter is empty then check if e.getLetter = c
            Optional<Tile> tempTile = getRack().stream().filter(e->(e.getLetter() == c)).findAny();
            if(tempTile.isEmpty())
                throw new ScrabbleException("Could not find tile with letter: "+c);

            temp.add(tempTile.get());
        }
        return temp;
    }

}
