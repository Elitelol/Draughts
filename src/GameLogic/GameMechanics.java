package GameLogic;
import DataStructures.*;
import GameUtilities.AlertMessenger;
import GameUtilities.TurnState;

public class GameMechanics {
    private static final GameMechanics instance = new GameMechanics();

    private GameMechanics(){}

    public static GameMechanics getInstance(){
        return instance;
    }

    public void moveDraught(Board board, Draught draught, int newX, int newY, String colour){
        board.removeCircle(draught.getPosition().getX(), draught.getPosition().getY());
        draught.setX(newX);
        draught.setY(newY);

        if(newX == 1 && colour.equals("White") || newX == 8 && colour.equals("Black") && !draught.getIsDame()){
            draught.setDame();
        }

        board.addCircle(newX, newY, colour, draught.getIsDame());
    }

    public void captureDraught(Board board, Player[] players, Draught playerDraught, int newX, int newY){
        StrikeableDraught strikeableDraught = playerDraught.getStrikingDraught(newX, newY);

        if(strikeableDraught == null){
            AlertMessenger.showAlert("You must use a striking draught properly.");
        }
        else {
            Position opponentPos = strikeableDraught.getOpponentDraught();
            Draught opponentDraught = players[TurnState.getOpponentTurn()].getDraught(opponentPos.getX(), opponentPos.getY());

            moveDraught(board, playerDraught, newX, newY, players[TurnState.getTurn()].getColour());
            removeDraught(board, players[TurnState.getOpponentTurn()], opponentDraught);
            players[TurnState.getTurn()].setStrikingDraught(playerDraught);
            players[TurnState.getTurn()].clearStrikes();
        }
    }

    private void removeDraught(Board board, Player player, Draught draught){
        board.removeCircle(draught.getX(), draught.getY());
        player.deleteDraught(draught.getX(), draught.getY());
    }
}