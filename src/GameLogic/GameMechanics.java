package GameLogic;
import DataStructures.*;

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

    public static void captureDraught(Board board, Player[] players, int turn, Draught playerDraught, int x2, int y2){
        int opponentTurn = GameRules.changeTurn(turn);
        StrikeableDraught strikeableDraught = playerDraught.getStrikingDraught(x2, y2);

        if(strikeableDraught == null){
            GameRules.showAlert("You must use a striking draught properly.");
        }
        else {
            Position opponentPos = strikeableDraught.getOpponentDraught();
            Draught opponentDraught = players[opponentTurn].getDraught(opponentPos.getX(), opponentPos.getY());

            moveDraught(board, playerDraught, x2, y2, players[turn].getColour());
            removeDraught(board, players[opponentTurn], opponentDraught);
            players[turn].setStrikingDraught(playerDraught);
            players[turn].clearStrikes();
        }
    }

    private static void removeDraught(Board board, Player player, Draught draught){
        board.removeCircle(draught.getX(), draught.getY());
        player.deleteDraught(draught.getX(), draught.getY());
    }
}