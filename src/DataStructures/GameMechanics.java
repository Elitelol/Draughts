package DataStructures;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Scanner;

public class GameMechanics {
    public static void moveDraught(Board board, Draught draught, int newX, int newY, String colour){
        board.removeCircle(draught.getPosition().getX(), draught.getPosition().getY());
        draught.setX(newX);
        draught.setY(newY);

        if(newX == 1 && colour.equals("White") || newX == 8 && colour.equals("Black") && !draught.getIsDame()){
            draught.setDame();
        }

        board.addCircle(newX, newY, colour, draught.getIsDame());
    }

    public static boolean captureDraught(Board board, Player[] players, int turn, int x1, int y1, int x2, int y2){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(null);
        int opponentTurn = getOpponentTurnNumber(turn);
        StrikingDraught strikingDraught = players[turn].getStrikingDraught(x1, y1);

        if(strikingDraught != null){
            Position playerPos = strikingDraught.getPlayerDraught();
            Position opponentPos = strikingDraught.getOpponentDraught();
            Position jumpingPos = strikingDraught.getJumpingPosition();
            Draught playerDraught = players[turn].getDraught(playerPos.getX(), playerPos.getY());
            Draught opponentDraught = players[opponentTurn].getDraught(opponentPos.getX(), opponentPos.getY());

            if(x2 == jumpingPos.getX() && y2 == jumpingPos.getY()){
                moveDraught(board, playerDraught, x2, y2, players[turn].getColour());
                removeDraught(board, players[opponentTurn], opponentDraught);
                players[turn].clearStrikes();
                return true;
            }
            else{
                alert.setHeaderText("Incorrect jumping position.");
                alert.showAndWait();
            }
        }
        else{
            alert.setHeaderText("You must choose a striking draught.");
            alert.showAndWait();
        }
        players[turn].clearStrikes();
        return false;
    }
    public static void removeDraught(Board board, Player player, Draught draught){
        board.removeCircle(draught.getX(), draught.getY());
        player.deleteDraught(draught.getX(), draught.getY());
    }

    public static int getOpponentTurnNumber(int turn){
        return turn == 0 ? 1 : 0;
    }
}