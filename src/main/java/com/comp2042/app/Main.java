package com.comp2042.app;

import com.comp2042.board.SimpleBoard;
import com.comp2042.input.event_controllers.GameController;
import com.comp2042.ui.ui_systems.GameView;
import com.comp2042.ui.ui_systems.GuiController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Main extends Application {

    /**
     * Called automatically by launch(args) in main().<br>
     * Creates a URL object, location, and load the game's FXML file into it .<br>
     * Pass the URL object to a FXMLLoader object to properly load and build the UI of the game.<br>
     * <p>
     *     Self note: root now holds the full UI structure (GridPane, GameOverPanel).
     * </p>
     * A controller class is created and set as a GuiController object.<br>
     * Creates a Scene object and adjusts the dimensions of the window.<br>
     * <p>
     *     Self note: Scene is what is inside the stage (main window).
     * </p>
     * Attach scene to stage and display the window on player's screen.<br>
     * -----------------------NEW REFACTORING-----------------------<br>
     * Upcasting of objects: SimpleBoard > Board, GuiController > GameView<br>
     * Connects GuiController to GameController by passing an object of GameView > GuiController in:<br>
     * new GameController(c);
     * -----------------------NEW REFACTORING-----------------------<br>
     * @param primaryStage  Main window of the game (args).
     * @throws Exception    Self note: This means that this method might throw an exception
     * because FXML files and resource file might fail to load, but the exception is not handled here.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        URL location = getClass().getClassLoader().getResource("gameLayout.fxml");
        ResourceBundle resources = null;
        FXMLLoader fxmlLoader = new FXMLLoader(location, resources);
        Parent root = fxmlLoader.load();
        GuiController c = fxmlLoader.getController();

        primaryStage.setTitle("TetrisJFX");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        GameView gameView = c;
        SimpleBoard board = new SimpleBoard(25, 10);
        new GameController(gameView, board);
        c.setSimpleBoard(board);
//        c.refreshAllPanels();
        gameView.refreshHoldBrick();
        gameView.refreshPreviewPanel();
    }

    /**
     * Self note: launch() is a static method from Application that sets up the JavaFX environment and automatically calls start().
     */
    public static void main(String[] args) {
        launch(args);
    }
}
