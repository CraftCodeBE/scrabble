package be.craftcode.scrabble.calculator;

import be.craftcode.scrabble.domain.Letter;
import be.craftcode.scrabble.domain.Player;
import be.craftcode.scrabble.domain.Word;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class ScoreCalculator implements Calculator {

    @Override
    public int calculate(String word) {
        int score = 0;

        var charList = word.toUpperCase().toCharArray();

        for (char item : charList) {
            score += Letter.valueOf(item + "".toUpperCase()).getValue();
        }

        return score;
    }

    public int calculateWithRandomTriple(String word){
        int score = 0;

        var charList = word.toUpperCase().toCharArray();
        var toTriple= ThreadLocalRandom.current().nextInt(charList.length);
        for (char item : charList) {
            score += Letter.valueOf(item + "".toUpperCase()).getValue();
        }
        var extraScore =Letter.valueOf(charList[toTriple]+"".toUpperCase()).getValue() * 2;
        score += extraScore;
        return score;
    }

    public String getHighestValueWord(List<String> possibilities) {
        int score = 0;
        String result = "";
        for (var word : possibilities) {
            if (calculate(word) > score) {
                score = calculate(word);
                result = word;
            }
        }
        return result;
    }

    public String findHighestScoring(List<String> dictionary) {
        int score = 0;
        String result = "";
        for (var word : dictionary) {
            if (calculate(word) > score) {
                score = calculate(word);
                result = word;
            }
        }
        return result;
    }

    public Word findHighestTriple(List<String> dictionary){
        var result = new Word(0,"");
//        int score = 0;
//        String result = "";
        for (var word : dictionary) {
            int score = calculateWithRandomTriple(word);
            if ( score > result.getScore()) {
                result.setScore(score);
                result.setValue(word);
            }
        }
        return result;
    }

    public List<String> checkDictionary(Player player, List<String> dictionary) {
        List<String> possibilities = new ArrayList<>();
        var partialDict = dictionary.stream()
                .filter(e -> e.length() <= player.getTiles().size())
                .collect(Collectors.toList());

        var playerLetters = player.getTiles().stream()
                .map(Letter::getLetter)
                .collect(Collectors.toList());

        for (var word : partialDict) {
            StringBuilder matchingLetters = new StringBuilder();
            var lettersClone = new ArrayList<>(playerLetters);

            for (char c : word.toCharArray()) {
                if (lettersClone.contains(c)) {
                    matchingLetters.append(c);
                    lettersClone.remove(Character.valueOf(c));
                }
            }
            if (matchingLetters.length() == word.length()) {
                possibilities.add(word);
            }
        }
        return possibilities;
    }
}
