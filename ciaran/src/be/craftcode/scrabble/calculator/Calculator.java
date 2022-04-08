package be.craftcode.scrabble.calculator;

import be.craftcode.scrabble.domain.Player;

import java.util.List;

public interface Calculator {
    int calculate(String word);
    List<String> checkDictionary(Player player, List<String> dictionary);

}
