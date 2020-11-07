package DataStructures;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class GameController {
    public StackPane stackPane;
    private Board board;
    private int turn;
    private int oldY, oldX;
    private int newY, newX;
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
        if(oldY == 0 && oldX == 0){
            oldY = (int) mouseEvent.getX() / 100 + 1;
            oldX = (int) mouseEvent.getY() / 100 + 1;
        }
        else{
            newY = (int) mouseEvent.getX() / 100 + 1;
            newX = (int) mouseEvent.getY() / 100 + 1;
            ableToMove = true;
        }
        gameMechanics.checkForCaptures(players, turn);

        if(players[turn].getPossibleStrikeSize() > 0 && ableToMove){
            gameMechanics.captureDraught(board, players, turn, oldX, oldY, newX, newY);
            continuousStrike = true;
        }
        else if(players[turn].getPossibleStrikeSize() == 0 && continuousStrike && ableToMove){
            continuousStrike = false;
            turn = changeTurn(turn);
            gameMechanics.checkForCaptures(players, turn);
            if(players[turn].getPossibleStrikeSize() > 0){
                gameMechanics.captureDraught(board, players, turn, oldX, oldY, newX, newY);
                continuousStrike = true;
            }
        }

        if (!continuousStrike && ableToMove){
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
