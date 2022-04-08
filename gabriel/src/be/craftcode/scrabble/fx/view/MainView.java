package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.Scrabble;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {
    ScrabbleView view;
    RackView player;
    RackView opponent;


    public MainView() {
        view = new ScrabbleView();
        setCenter(view);

        player = new RackView(Scrabble.getInstance().getPlayer(0), true);
        setBottom(player);

        opponent = new RackView(Scrabble.getInstance().getPlayer(0), false);
        setTop(opponent);


        BorderPane.setMargin(view, new Insets(10,10,10,10));
    }

    public ScrabbleView getView() {
        return view;
    }

    public RackView getPlayer() {
        return player;
    }

    public RackView getOpponent() {
        return opponent;
    }
}
