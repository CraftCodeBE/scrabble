package be.craftcode.scrabble.calculator;

import be.craftcode.scrabble.domain.Letter;
import be.craftcode.scrabble.domain.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ScoreCalculator implements Calculator {

    @Override
    public int calculate(String word, List<String> dictionary) {
        int score = 0;

        if (dictionary.contains(word.toLowerCase())) {
            var charList = word.toUpperCase().toCharArray();

            for (char item : charList) {
                score += Letter.valueOf(item + "".toUpperCase()).getValue();
            }
        }

        return score;
    }

    public List<String> checkDictionary(Player player, List<String> dictionary) {
        List<String> possibilities = new ArrayList<>();
        var partialDict = dictionary.stream()
                .filter(e -> e.length() <= player.getTiles().size())
                .collect(Collectors.toList());
        var playerLetters = player.getTiles().stream()
                .map(Letter::getLetter)
                .collect(Collectors.toList());
        for (var word: partialDict) {
            StringBuilder matchingLetters = new StringBuilder();
            for (char c: word.toCharArray()) {
                if(playerLetters.contains(c)){
                  matchingLetters.append(c);
                }
            }
            if(matchingLetters.length() == word.length()){
                possibilities.add(word);
            }
        }
        return possibilities;
    }
}
