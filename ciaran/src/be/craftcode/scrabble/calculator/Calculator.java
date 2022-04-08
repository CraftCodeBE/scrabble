package be.craftcode.scrabble.calculator;

import java.util.List;

public interface Calculator {
    int calculate(String word, List<String> dictionary);
}
