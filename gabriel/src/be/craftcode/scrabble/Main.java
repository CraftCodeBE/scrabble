package be.craftcode.scrabble;

import be.craftcode.scrabble.fx.model.ScrabbleModel;
import be.craftcode.scrabble.fx.presenter.ScrabblePresenter;
import be.craftcode.scrabble.fx.view.MainView;
import be.craftcode.scrabble.fx.view.RackView;
import be.craftcode.scrabble.fx.view.ScrabbleView;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
//        Scrabble.getInstance().start();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Scrabble ");
//        button = new Button();
//        button.setText("Click me");
//        button.setOnAction(event -> System.out.println("clicked"));
//


        ScrabbleModel model = new ScrabbleModel();
        MainView pane = new MainView();
        Scene scene = new Scene(pane, 1920,1080);
        stage.setScene(scene);
        stage.show();

        ScrabblePresenter presenter = new ScrabblePresenter(model, pane);
    }

}
