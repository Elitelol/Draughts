package DataStructures;

import GameLogic.GameMechanics;
import GameLogic.GameRules;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class GameController {
    public StackPane stackPane;
    public Label turnLabel;
    private Board board;
    private GameState gameState;
    private int turn;
    private int oldY, oldX;
    private int newY, newX;
    private final Player[] players;
    private boolean gameOver;

    public GameController(){
        this.turn = 0;
        players = new Player[]{new WhitePlayer("White", -1), new BlackPlayer("Black", 1)};
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
            gameState = determineGameState();
        }
        else{
            newY = (int) mouseEvent.getX() / 100 + 1;
            newX = (int) mouseEvent.getY() / 100 + 1;
            moveDraught();
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
        GameRules.showAlert(players[turn].getColour() + " surrendered.");
    }

    private void moveDraught() {
        if(gameOver || players[0].getDraughtSize() == 0 || players[1].getDraughtSize() == 0){
            GameRules.showAlert("Game is over.");
        }
        Draught playerDraught = players[turn].getDraught(oldX, oldY);

        if(playerDraught == null){
            GameRules.showAlert("Invalid draught is selected.");
        }
        else if(gameState == GameState.STRIKE || (gameState == GameState.CONTINUOUS_STRIKE && players[turn].isStrikingDraught(playerDraught))){
            GameMechanics.captureDraught(board, players, turn, playerDraught, newX, newY);
        }
        else if (!players[turn].isStrikingDraught(playerDraught) && gameState == GameState.CONTINUOUS_STRIKE){
            GameRules.showAlert("Invalid continuous striking draught selected.");
        }
        else if(gameState == GameState.MOVE && GameRules.checkIfLegalMove(players, playerDraught, oldX, oldY, newX, newY, turn)){
            GameMechanics.moveDraught(board, playerDraught, newX, newY, players[turn].getColour());
            turn = GameRules.changeTurn(turn);
        }
        else{
            GameRules.showAlert("Unable to move to that position.");
        }

        turnLabel.setText("Turn: " + players[turn].getColour());
        clearCoordinates();
        AnchorPane root  = (AnchorPane) stackPane.getScene().getRoot();
        root.getChildren().removeAll(board.getCircles());
        root.getChildren().addAll(board.getCircles());
    }

    private GameState determineGameState(){
        GameRules.checkForStrikes(players, turn);

        if(players[turn].getStrikingDraught() == null && players[turn].isAbleToStrike()){
            return GameState.STRIKE;
        }
        else if(players[turn].getStrikingDraught() != null){
            if(players[turn].isContinuousStrike()){
                return GameState.CONTINUOUS_STRIKE;
            }
            else {
                players[turn].setStrikingDraught(null);
                turn = GameRules.changeTurn(turn);
                turnLabel.setText("Turn: " + players[turn].getColour());
                return determineGameState();
            }
        }

        return GameState.MOVE;
    }
}
