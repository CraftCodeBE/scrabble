package be.craftcode.scrabble.calculator;

import be.craftcode.scrabble.domain.Letter;

import java.util.ArrayList;
import java.util.List;

public class ScoreCalculator implements Calculator {

    @Override
    public int calculate(String word) {
        var charList = word.toUpperCase().toCharArray();
        int score = 0;
        for (char item : charList) {
            score += Letter.valueOf(item + "".toUpperCase()).getValue();
        }
        return score;
    }
}
