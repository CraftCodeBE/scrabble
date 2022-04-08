package be.craftcode.scrabble.model.player;

import be.craftcode.scrabble.Scrabble;
import be.craftcode.scrabble.exceptions.TileException;
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
        for (String possibleWord : Scrabble.getInstance().getDictionary()) {
            Map<Character, Long> wordMap = BoardHelper.countForWord.apply(possibleWord);
            Map<Character, Long> myHandTileCount = BoardHelper.countForRack.apply(getRack());

            boolean ok = BoardHelper.canBeUsed(wordMap, myHandTileCount);
//            System.out.println(possibleWord + " || I can make?: "+ ok);
            if(ok)
                canMakeWords.add(possibleWord);
        }
    }

    public String getWordWithLength(String begin, int min, int max){
        refreshCanMakeWords();
        Optional<String> wordOpt = BoardHelper.getAll(canMakeWords, e-> e.length() >= min && e.length() <= max && (begin.isEmpty() || e.startsWith(begin))).stream().findFirst();
        return wordOpt.orElseThrow();
    }

    public List<Tile> getTilesForWord(String word){
        List<Tile> temp = new LinkedList<>();
        for (char c : word.toCharArray()) {
            Tile tempTile = getRack().stream().filter(e->e.getLetter() == c).findAny().orElseThrow(TileException::new);
            temp.add(tempTile);
        }
        return temp;
    }

}
