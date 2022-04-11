package be.craftcode.scrabble.fx.view;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SideInfo extends VBox {
    private Text rackContent;
    private Text possibleWordsWithMaxLenght;
    private Text allPossibleWords;
    private Text longestWordPossible;
    private Text longestScoringWord;
    private Text myPoints;
    private Button buttonRefresh;
    private Button finishRound;
    private ScrollPane scrollPossibleWordsWithMaxLenght;
    private ScrollPane scrollAllPossibleWords;

    public SideInfo() {
        setPrefSize(200, 100);
        setPadding(new Insets(10));

        rackContent = new Text();
        rackContent.setText("rackContent");

        possibleWordsWithMaxLenght = new Text();
        possibleWordsWithMaxLenght.setText("possibleWordsWithMaxLenght");

        allPossibleWords = new Text();
        allPossibleWords.setText("allPossibleWords");

        scrollPossibleWordsWithMaxLenght = initScrollPane(possibleWordsWithMaxLenght);
        scrollAllPossibleWords = initScrollPane(allPossibleWords);

        longestWordPossible = new Text();
        longestWordPossible.setText("longestWordPossible");
        longestScoringWord = new Text();
        longestScoringWord.setText("longestScoringWord");
        buttonRefresh = new Button();
        buttonRefresh.setText("Refresh Words");

        finishRound = new Button();
        finishRound.setText("Finish Round");


        myPoints = new Text();
        myPoints.setText("MyPoints: 0");


        getChildren().addAll(rackContent, scrollPossibleWordsWithMaxLenght, scrollAllPossibleWords, longestWordPossible, longestScoringWord, buttonRefresh, myPoints, finishRound);
    }

    private ScrollPane initScrollPane(Node content){
        ScrollPane pane = new ScrollPane();
        pane.setContent(content);
        pane.setMinHeight(100);
        pane.setMaxHeight(100);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, null, null, null)));
        return pane;
    }

    public void setPoints(int points){
        myPoints.setText("My Points: "+points);
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

    public Button getFinishRound() {
        return finishRound;
    }
}
