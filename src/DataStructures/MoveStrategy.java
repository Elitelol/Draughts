package DataStructures;

import GameLogic.GameMechanics;
import GameLogic.GameRules;
import GameUtilities.AlertMessenger;
import GameUtilities.TurnState;

public class MoveStrategy implements ExecutionStrategy {
    @Override
    public void execute(Board board, Player[] players, Draught playerDraught, int x2, int y2) {
        if(GameRules.checkIfLegalMove(players, playerDraught, playerDraught.getX(), playerDraught.getY(), x2, y2)){
            GameMechanics.moveDraught(board, playerDraught, x2, y2, players[TurnState.getTurn()].getColour());
            TurnState.changeTurn();
        }
        else{
            AlertMessenger.showAlert("You cannot move to that position.");
        }
    }
}
