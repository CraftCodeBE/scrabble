package be.craftcode.scrabble;

import be.craftcode.scrabble.fx.presenter.ScrabblePresenter;
import be.craftcode.scrabble.fx.view.MainView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ScrabbleStarter extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Scrabble ");

        MainView pane = new MainView();
        Scene scene = new Scene(pane, 1920,1080);
        stage.setScene(scene);
        stage.show();

        new ScrabblePresenter(Scrabble.getInstance(), pane);
    }

}
