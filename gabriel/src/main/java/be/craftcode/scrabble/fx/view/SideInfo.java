package be.craftcode.scrabble.fx.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class SideInfo extends VBox {
    private final Text rackContent;
    private final Text possibleWordsWithMaxLenght;
    private final Text allPossibleWords;
    private final Text longestWordPossible;
    private final Text longestScoringWord;
    private final Text myPoints;
    private final Text enemyPoints;
    private final HBox swap;
    private final Button buttonRefresh;
    private final Button finishRound;
    private final ScrollPane scrollPossibleWordsWithMaxLenght;
    private final ScrollPane scrollAllPossibleWords;

    public SideInfo() {
        setPrefSize(200, 100);
        setPadding(new Insets(10));

        rackContent = new Text();
        rackContent.setText("rackContent");

        possibleWordsWithMaxLenght = new Text();
        possibleWordsWithMaxLenght.setText("possibleWordsWithMaxLenght");

        VBox.setMargin(possibleWordsWithMaxLenght, new Insets(20, 0, 20, 0));

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
        VBox.setMargin(buttonRefresh, new Insets(20, 0, 20, 0));

        finishRound = new Button();
        finishRound.setText("Finish Round");
        VBox.setMargin(finishRound, new Insets(20, 0, 20, 0));


        myPoints = new Text();
        myPoints.setText("MyPoints: 0");
        enemyPoints = new Text();
        enemyPoints.setText("EnemyPoints: 0");



        Text swapCard = new Text(250, 100, "SWAP TILE");
        swapCard.setScaleX(2.0);
        swapCard.setScaleY(2.0);

        swap = new HBox();
        swap.setPrefSize(250,250);
        swap.getChildren().add(swapCard);
        swap.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        swap.setAlignment(Pos.CENTER);

        VBox.setMargin(swap, new Insets(50, 0, 0, 0));

        getChildren().addAll(rackContent, scrollPossibleWordsWithMaxLenght, scrollAllPossibleWords, longestWordPossible, longestScoringWord, buttonRefresh, myPoints, enemyPoints, finishRound, swap);
    }

    private ScrollPane initScrollPane(Node content){
        ScrollPane pane = new ScrollPane();
        pane.setContent(content);
        pane.setMinHeight(100);
        pane.setMaxHeight(100);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK, null, null, null)));
        return pane;
    }

    public void setPoints(int points, boolean bot){
        String msg = "Points: "+points;
        if(bot)
            enemyPoints.setText("Enemy "+msg);
        else
            myPoints.setText("My "+msg);
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

    public HBox getSwapCard() {
        return swap;
    }
}
