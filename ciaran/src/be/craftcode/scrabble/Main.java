package be.craftcode.scrabble;

import be.craftcode.scrabble.calculator.ScoreCalculator;
import be.craftcode.scrabble.domain.Bag;
import be.craftcode.scrabble.domain.Player;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        List<Player> players = new ArrayList<>();

        try {
            List<String> dictionary = Files.readAllLines(Path.of(System.getProperty("user.dir") + "/dictionary.txt"));
            System.out.printf("Loaded %d words from dictionary\n", dictionary.size());
            Bag bag = new Bag();
            bag.fillBag();
            players.add(new Player(1));
            for (Player p : players) {
                bag.startTiles(p);
                System.out.println("Player rack: ");
                p.getTiles().forEach(System.out::println);
            }

            var results = scoreCalculator.checkDictionary(players.get(0), dictionary);
            var result = scoreCalculator.getHighestValueWord(results);
            System.out.println("=====================");
            System.out.println("Word: " + result + "");
            System.out.println("Score: " + scoreCalculator.calculate(result));
            System.out.println("=====================");

            var highestScoring = scoreCalculator.findHighestScoring(dictionary);
            var highestTriple = scoreCalculator.findHighestTriple(dictionary);
            System.out.printf("Highest scoring word: %s \nWith score: %d\n", highestScoring, scoreCalculator.calculate(highestScoring));
            System.out.println("=====================");
            System.out.printf("Highest scoring word with random tripled letter: %s \nWith score: %d\n", highestTriple.getValue(), highestTriple.getScore());
            System.out.println("=====================");

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
