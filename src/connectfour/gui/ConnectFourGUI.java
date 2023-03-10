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
 * @author Patricio Solis
 */
public class ConnectFourGUI extends Application implements Observer<ConnectFourBoard> {

    /** gives access to the grid in the model */
    private ConnectFourBoard model;
    /** grid of buttons to make each one distinct and functional */
    private Button[][] grid;
    /** image for start state and for where there is no pieces */
    private Image EMPTY;
    /** image for player 1 move */
    private Image Player1;
    /** image for player 2 move */
    private Image Player2;
    /** displays the total number of moves made */
    private Label movesMade;
    /** displays the current player to make a move */
    private Label currPlayer;
    /** displays the current state of the game */
    private Label gameStatus;


    @Override
    public void init() {
        // TODO
        this.model = new ConnectFourBoard();
        model.addObserver(this);
    }

    /**
     * Builds the grid of buttons with functionality
     *
     * @return GridPane with functional grid of buttons
     */
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

        // checks the connectFourBoard in the model and checks each cell and matches the cell
        // with the appropriate image
        for(int r = 0; r < ConnectFourBoard.ROWS; r++){
            for(int c = 0; c < ConnectFourBoard.COLS; c++){
                if(connectFourBoard.getContents(r, c).equals(ConnectFourBoard.Player.NONE)){
                    grid[r][c].setGraphic(new ImageView(EMPTY));
                }else if(connectFourBoard.getContents(r, c).equals(ConnectFourBoard.Player.P1)){
                    grid[r][c].setGraphic(new ImageView(Player1));
                }else{
                    grid[r][c].setGraphic(new ImageView(Player2));
                }
            }
        }

        // updates the labels
        movesMade.setText("Moves made: " +  model.getMovesMade());
        currPlayer.setText("Current Player: " + model.getCurrentPlayer());
        gameStatus.setText("Game Status: " + model.getGameStatus());

        // if the game is won or tied it disables the grid
        if(model.getGameStatus() == ConnectFourBoard.Status.P1_WINS ||
                model.getGameStatus() == ConnectFourBoard.Status.P2_WINS ||
                model.getGameStatus() == ConnectFourBoard.Status.TIE){
            for(int r = 0; r < ConnectFourBoard.ROWS; r++) {
                for (int c = 0; c < ConnectFourBoard.COLS; c++) {
                    grid[r][c].setDisable(true);
                }
            }
        }


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