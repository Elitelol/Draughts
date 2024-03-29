package DataStructures;

import GameLogic.GameRules;
import GameUtilities.AlertMessenger;
import GameUtilities.TurnState;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class GameController {
    public StackPane stackPane;
    public Label turnLabel;
    private Board board;
    private int oldY, oldX;
    private int newY, newX;
    private final Player[] players;
    private boolean gameOver;
    MovementStrategy movementStrategy;

    public GameController(){
        players = new Player[]{new WhitePlayer(), new BlackPlayer()};
        players[0].generateDraughts();
        players[1].generateDraughts();
        oldY = 0;
        oldX = 0;
        newY = 0;
        newX = 0;
        gameOver = false;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public void setCoordinates(MouseEvent mouseEvent){
        if(oldY == 0 && oldX == 0){
            oldY = (int) mouseEvent.getX() / 100 + 1;
            oldX = (int) mouseEvent.getY() / 100 + 1;
            movementStrategy = determineMovement();
        }
        else{
            newY = (int) mouseEvent.getX() / 100 + 1;
            newX = (int) mouseEvent.getY() / 100 + 1;
            executeMovement();
        }
    }

    public void clearCoordinates(){
        oldY = 0;
        oldX = 0;
        newY = 0;
        newX = 0;
    }

    public void setGameOver(ActionEvent actionEvent) {
        gameOver = true;
        AlertMessenger.showAlert(players[TurnState.getTurn()].getColour() + " surrendered.");
    }

    private void executeMovement() {
        Draught playerDraught = players[TurnState.getTurn()].getDraught(oldX, oldY);

        if(gameOver || players[0].getDraughtSize() == 0 || players[1].getDraughtSize() == 0){
            AlertMessenger.showAlert("Game is over.");
        }
        else if(playerDraught == null){
            AlertMessenger.showAlert("Draught selected incorrectly.");
        }
        else{
            movementStrategy.execute(board, players, playerDraught, new Position(newX, newY));
        }

        turnLabel.setText("Turn: " + players[TurnState.getTurn()].getColour());
        clearCoordinates();

        AnchorPane root  = (AnchorPane) stackPane.getScene().getRoot();

        root.getChildren().removeAll(board.getCircles());
        root.getChildren().addAll(board.getCircles());
    }

    private MovementStrategy determineMovement(){
        int turn = TurnState.getTurn();
        GameRules gameRules = GameRules.getInstance();

        gameRules.checkForStrikes(players);

        if(players[turn].getStrikingDraught() == null && players[turn].isAbleToStrike()){
            return new StrikeStrategy();
        }
        else if(players[turn].getStrikingDraught() != null){
            if(players[turn].isContinuousStrike()){
                return new ContinuousStrikeStrategy();
            }
            else {
                players[TurnState.getTurn()].setStrikingDraught(null);
                TurnState.changeTurn();
                turnLabel.setText("Turn: " + players[TurnState.getTurn()].getColour());

                return determineMovement();
            }
        }
        return new SimpleMoveStrategy();
    }
}
