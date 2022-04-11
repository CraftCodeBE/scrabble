package be.craftcode.scrabble.fx.view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class SideInfo extends VBox {
    private Text rackContent;
    private Text possibleWordsWithMaxLenght;
    private Text allPossibleWords;
    private Text longestWordPossible;
    private Text longestScoringWord;
    private Button buttonRefresh;

    public SideInfo() {
        setPrefSize(200, 100);
        setPadding(new Insets(10));

        rackContent = new Text();
        rackContent.setText("rackContent");
        possibleWordsWithMaxLenght = new Text();
        possibleWordsWithMaxLenght.setText("possibleWordsWithMaxLenght");
        allPossibleWords = new Text();
        allPossibleWords.setText("allPossibleWords");
        longestWordPossible = new Text();
        longestWordPossible.setText("longestWordPossible");
        longestScoringWord = new Text();
        longestScoringWord.setText("longestScoringWord");
        buttonRefresh = new Button();
        buttonRefresh.setText("Refresh Words");

        getChildren().addAll(rackContent, possibleWordsWithMaxLenght, allPossibleWords, longestWordPossible, longestScoringWord, buttonRefresh);
    }

    public Text getRackContent() {
        return rackContent;
    }

    public Text getPossibleWordsWithMaxLenght() {
        return possibleWordsWithMaxLenght;
    }

    public Text getAllPossibleWords() {
        return allPossibleWords;
    }

    public Text getLongestWordPossible() {
        return longestWordPossible;
    }

    public Text getLongestScoringWord() {
        return longestScoringWord;
    }

    public Button getButtonRefresh() {
        return buttonRefresh;
    }

}
