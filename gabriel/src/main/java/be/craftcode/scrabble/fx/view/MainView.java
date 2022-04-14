package be.craftcode.scrabble.fx.view;

import be.craftcode.scrabble.model.Scrabble;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;

public class MainView extends BorderPane {
    private final ScrabbleView view;
    private final RackView player;
    private final RackView opponent;
    private final SideInfo sideInfo;

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

    public ScrabbleView getScrabbleView() {
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
