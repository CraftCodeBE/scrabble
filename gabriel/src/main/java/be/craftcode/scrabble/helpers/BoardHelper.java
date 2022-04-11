package be.craftcode.scrabble.helpers;

import be.craftcode.scrabble.model.Tile;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardHelper {
    public static final Function<List<Tile>, Map<Character, Long>> countForRack = rack -> rack.stream().collect(Collectors.groupingBy(Tile::getLetter, TreeMap::new, Collectors.counting()));
    public static final Function<String, Map<Character, Long>> countForWord = word -> IntStream.range(0, word.toCharArray().length).mapToObj(i -> word.toCharArray()[i]).collect(Collectors.groupingBy(Function.identity(), TreeMap::new, Collectors.counting()));

    /**
     * Task 1 Calculate the score for a word. The score is the sum of the points for the letters that make up a word. For example: GUARDIAN = 2 + 1 + 1 + 1 + 2 + 1 + 1 + 1 = 10.
     * @param word
     * @return
     */
    public static int getValueForWord(String word) {
        int value = 0;
        for (char c : word.toLowerCase().toCharArray()) {
            value += TileHelper.getPointForLetter(c);
        }
        return value;
    }

    /**
     * Task 4 Find a valid word formed from the seven tiles. A list of valid words can be found in dictionary.txt
     * @param toCheckWordMap
     * @param myHand
     * @return
     */
    public static boolean canBeUsed(Map<Character, Long> toCheckWordMap, Map<Character, Long> myHand){
//        System.out.println("wordMap: "+toCheckWordMap);
//        System.out.println("myHand: "+myHand);

        for (Map.Entry<Character, Long> entry : toCheckWordMap.entrySet()) {
            Character toCheckChar = entry.getKey();
            Long count = entry.getValue();
            long countOnMyHand = myHand.getOrDefault(toCheckChar, -1L);
            if(countOnMyHand < count)
                return false;
        }
        return true;
    }

    /**
     * Task 5 Find the longest valid word that can be formed from the seven tiles.
     * Task 6 Find the highest scoring word that can be formed.
     * @param list
     * @param keyExtractor
     * @return
     */
    public static String sortByIntAndGetReversed(List<String> list, Function<String, Integer> keyExtractor){
        List<String> temp = new LinkedList<>(list);
        temp.sort(Comparator.comparing(keyExtractor).reversed());
        return temp.get(0);
    }

    public static Collection<String> getAll(List<String> list, Predicate<String> predicate){
        return list.stream().filter(predicate).collect(Collectors.toList());
    }

}
