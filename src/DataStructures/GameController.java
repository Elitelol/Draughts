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
    ExecutionStrategy executionStrategy;

    public GameController(){
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
            executionStrategy = determineGameState();
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
        AlertMessenger.showAlert(players[TurnState.getTurn()].getColour() + " surrendered.");
    }

    private void moveDraught() {
        Draught playerDraught = players[TurnState.getTurn()].getDraught(oldX, oldY);

        if(gameOver || players[0].getDraughtSize() == 0 || players[1].getDraughtSize() == 0){
            AlertMessenger.showAlert("Game is over.");
        }
        else if(playerDraught == null){
            AlertMessenger.showAlert("Draught selected incorrectly.");
        }
        else{
            executionStrategy.execute(board, players, playerDraught, newX, newY);
        }

        turnLabel.setText("Turn: " + players[TurnState.getTurn()].getColour());
        clearCoordinates();
        AnchorPane root  = (AnchorPane) stackPane.getScene().getRoot();
        root.getChildren().removeAll(board.getCircles());
        root.getChildren().addAll(board.getCircles());
    }

    private ExecutionStrategy determineGameState(){
        int turn = TurnState.getTurn();
        GameRules.checkForStrikes(players);

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
                return determineGameState();
            }
        }
        return new MoveStrategy();
    }
}
