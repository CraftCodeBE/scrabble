package be.craftcode.scrabble.fx.presenter;

import be.craftcode.scrabble.fx.model.ScrabbleModel;
import be.craftcode.scrabble.fx.view.ScrabbleView;

public class ScrabblePresenter {
    private ScrabbleModel model;
    private ScrabbleView view;

    public ScrabblePresenter(ScrabbleModel model, ScrabbleView view) {
        this.model = model;
        this.view = view;
        addEventHandlers();
        updateView();
    }

    private void updateView() {
    }

    private void addEventHandlers() {
    }


}
