package DataStructures;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class GameController {
    public StackPane stackPane;
    public Label turnLabel;
    private Board board;
    private int turn;
    private int oldY, oldX;
    private int newY, newX;
    private int strikeX, strikeY;
    private boolean continuousStrike;
    private boolean ableToMove;
    private Player[] players;
    private boolean gameOver;

    public GameController(){
        this.turn = 0;
        this.continuousStrike = false;
        players = new Player[]{new Player("White"), new Player("Black")};
        players[0].generateDraughts();
        players[1].generateDraughts();
        oldY = 0;
        oldX = 0;
        newY = 0;
        newX = 0;
        strikeX = 0;
        strikeY = 0;
        ableToMove = false;
        gameOver = false;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public int changeTurn(int turn){
        return turn == 0 ? 1 : 0;
    }

    public void clearCoordinates(){
        oldY = 0;
        oldX = 0;
        newY = 0;
        newX = 0;
        ableToMove = false;
    }

    public void moveDraught(MouseEvent mouseEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(null);
        int option;
        int tempTurn = turn;

        GameRules.checkForCaptures(players, turn);

        if(players[0].getDraughtSize() == 0 || players[1].getDraughtSize() == 0 || gameOver){
            option = 0;
        }
        else if(players[turn].getPossibleStrikeSize() > 0 && !continuousStrike){
            option = 1;
        }
        else if(continuousStrike){
            StrikingDraught strikingDraught = players[turn].getStrikingDraught(strikeX, strikeY);

            if(strikingDraught != null){
                option = 2;
            }
            else {
                option = 3;
                continuousStrike = false;
                strikeX = 0;
                strikeY = 0;
                turn = changeTurn(turn);
            }
        }
        else{
            strikeX = 0;
            strikeY = 0;
            option = 3;
        }

        if(oldY == 0 && oldX == 0){
            oldY = (int) mouseEvent.getX() / 100 + 1;
            oldX = (int) mouseEvent.getY() / 100 + 1;
        }
        else{
            newY = (int) mouseEvent.getX() / 100 + 1;
            newX = (int) mouseEvent.getY() / 100 + 1;
            ableToMove = true;
        }

        if(option == 0){
            if(gameOver){
                alert.setHeaderText("Game is over.");
            }
            else if(players[0].getDraughtSize() == 0){
                alert.setHeaderText("Black won.");
            }
            else{
                alert.setHeaderText("White won.");
            }
            alert.showAndWait();
        }

        if(option == 1 && ableToMove){
            if(GameMechanics.captureDraught(board, players, turn, oldX, oldY, newX, newY)){
                strikeX = newX;
                strikeY = newY;
                continuousStrike = true;
            }
        }
        if (option == 2 && ableToMove){
            if(strikeX == oldX && strikeY == oldY && GameMechanics.captureDraught(board, players, turn, oldX, oldY, newX, newY)){
                strikeX = newX;
                strikeY = newY;
            }
        }
        if(option == 3 && ableToMove){
            Draught draught = players[turn].getDraught(oldX, oldY);

            if(draught == null || !GameRules.checkIfLegalMove(players, draught, oldX, oldY, newX, newY, turn)){
                alert.setHeaderText("Draught incorrectly selected.");
                alert.showAndWait();
            }
            else{
                GameMechanics.moveDraught(board, draught, newX, newY, players[turn].getColour());
                turn = changeTurn(turn);
            }
        }

        if(turn != tempTurn){
            turnLabel.setText(players[turn].getColour() + " turn.");
        }

        if(oldY != 0 && oldX != 0 && newY != 0 && newX != 0){
            clearCoordinates();
            AnchorPane root  = (AnchorPane) stackPane.getScene().getRoot();
            root.getChildren().removeAll(board.getCircles());
            root.getChildren().addAll(board.getCircles());
        }
    }

    public void setGameOver(ActionEvent actionEvent) {
        gameOver = true;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(null);
        alert.setHeaderText(players[turn].getColour() + " surrendered.");
        alert.showAndWait();
    }
}
