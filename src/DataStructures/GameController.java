package DataStructures;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;

public class GameController {
    public StackPane stackPane;
    private Board board;
    private int turn;
    private int oldY, oldX;
    private int newY, newX;
    private int strikeX, strikeY;
    private int option;
    private boolean continuousStrike;
    private boolean ableToMove;
    private Player[] players;
    private GameMechanics gameMechanics;

    public GameController(){
        this.turn = 0;
        this.continuousStrike = false;
        players = new Player[]{new Player("White"), new Player("Black")};
        players[0].generateDraughts();
        players[1].generateDraughts();
        gameMechanics = new GameMechanics();
        oldY = 0;
        oldX = 0;
        newY = 0;
        newX = 0;
        strikeX = 0;
        strikeY = 0;
        option = 0;
        ableToMove = false;
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
        int option = 0;

        gameMechanics.checkForCaptures(players, turn);

        if(players[turn].getPossibleStrikeSize() > 0 && !continuousStrike){
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

        if(option == 1 && ableToMove){
            if(gameMechanics.captureDraught(board, players, turn, oldX, oldY, newX, newY)){
                strikeX = newX;
                strikeY = newY;
                continuousStrike = true;
            }
        }
        if (option == 2 && ableToMove){
            if(strikeX == oldX && strikeY == oldY && gameMechanics.captureDraught(board, players, turn, oldX, oldY, newX, newY)){
                strikeX = newX;
                strikeY = newY;
            }
        }
        if(option == 3 && ableToMove){
            Draught draught = players[turn].getDraught(oldX, oldY);

            if(draught == null || !gameMechanics.checkIfLegalMove(players, draught, oldX, oldY, newX, newY, turn)){
                System.out.println("Invalid");
            }
            else{
                gameMechanics.moveDraught(board, draught, newX, newY, players[turn].getColour());
                turn = changeTurn(turn);
            }
        }

        if(oldY != 0 && oldX != 0 && newY != 0 && newX != 0){
            clearCoordinates();
            AnchorPane root  = (AnchorPane) stackPane.getScene().getRoot();
            root.getChildren().removeAll(board.getCircles());
            root.getChildren().addAll(board.getCircles());
        }
    }
}
