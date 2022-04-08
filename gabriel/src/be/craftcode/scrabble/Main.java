package be.craftcode.scrabble;

import be.craftcode.scrabble.fx.model.ScrabbleModel;
import be.craftcode.scrabble.fx.presenter.ScrabblePresenter;
import be.craftcode.scrabble.fx.view.ScrabbleView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Main extends Application {

    Button button;
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
//        BorderPane pane = new BorderPane();
//        pane.setCenter(button);
//        pane.setBottom(new TileView());
//        BorderPane.setAlignment(button, Pos.CENTER);
//        BorderPane.setMargin(button, new Insets(10,10,10,10));

        ScrabbleModel model = new ScrabbleModel();
        ScrabbleView view = new ScrabbleView();
        ScrabblePresenter presenter = new ScrabblePresenter(model, view);

        Scene scene = new Scene(view, 1920,1080);
        stage.setScene(scene);
        stage.show();
    }

}
