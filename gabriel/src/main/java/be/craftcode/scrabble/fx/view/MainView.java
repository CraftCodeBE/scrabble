package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.Scrabble;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class MainView extends BorderPane {
    ScrabbleView view;
    RackView player;
    RackView opponent;
    SideInfo sideInfo;

    public MainView() {
        view = new ScrabbleView();
        setCenter(view);

        player = new RackView(Scrabble.getInstance().getPlayer(0), true);
        setBottom(player);

        opponent = new RackView(Scrabble.getInstance().getPlayer(1), false);
        setTop(opponent);

        sideInfo = new SideInfo();
        setRight(sideInfo);


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

    public SideInfo getSideInfo() {
        return sideInfo;
    }
}
