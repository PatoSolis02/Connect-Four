package connectfour.gui;

import connectfour.model.ConnectFourBoard;
import connectfour.model.Observer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;

/**
 * A JavaFX GUI for the networked Connect Four game.
 *
 * @author RIT CS
 * @author YOUR NAME HERE
 */
public class ConnectFourGUI extends Application implements Observer<ConnectFourBoard> {

    private ConnectFourBoard model;
    private Button[][] grid;
    private Image EMPTY;
    private Image Player1;
    private Image Player2;
    private Label movesMade;
    private Label currPlayer;
    private Label gameStatus;


    @Override
    public void init() {
        // TODO
        this.model = new ConnectFourBoard();
        model.addObserver(this);
    }

    private GridPane makeGridPane(){
        GridPane gridPane = new GridPane();
        grid = new Button[ConnectFourBoard.ROWS][ConnectFourBoard.COLS];
        for(int row = 0; row < ConnectFourBoard.ROWS; row++){
            for(int col = 0; col < ConnectFourBoard.COLS; col++){
                Button button = new Button();
                int currCol = col;
                button.setGraphic(new ImageView(EMPTY));
                button.setOnAction(event -> model.makeMove(currCol));
                grid[row][col] = button;
                gridPane.add(button, col, row);
            }
        }
        return gridPane;
    }

    /**
     * Construct the layout for the game.
     *
     * @param stage container (window) in which to render the GUI
     * @throws Exception if there is a problem
     */
    public void start( Stage stage ) throws Exception {
        // TODO
        EMPTY = new Image(new FileInputStream("src/connectfour/gui/empty.png"));
        Player1 = new Image(new FileInputStream("src/connectfour/gui/p1black.png"));
        Player2 = new Image(new FileInputStream("src/connectfour/gui/p2red.png"));

        BorderPane mainBorderPane = new BorderPane();
        BorderPane labelBorderPane = new BorderPane();
        GridPane gridPane = makeGridPane();

        movesMade = new Label("Moves made: " + model.getMovesMade());
        currPlayer = new Label("Current Player: " + model.getCurrentPlayer());
        gameStatus = new Label("Game Status: " + model.getGameStatus());
        labelBorderPane.setLeft(movesMade);
        labelBorderPane.setCenter(currPlayer);
        labelBorderPane.setRight(gameStatus);

        mainBorderPane.setCenter(gridPane);
        mainBorderPane.setBottom(labelBorderPane);

        stage.setScene(new Scene(mainBorderPane));
        stage.setTitle("Connect Four GUI");
        stage.show();

    }

    /**
     * Called by the model, model.ConnectFourBoard, whenever there is a state change
     * that needs to be updated by the GUI.
     *
     * @param connectFourBoard the board
     */
    @Override
    public void update(ConnectFourBoard connectFourBoard) {
        // TODO
        for()


    }

    /**
     * The main method expects the host and port.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }
}