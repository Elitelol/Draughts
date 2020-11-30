package DataStructures;

import GameLogic.GameMechanics;
import GameUtilities.AlertMessenger;
import GameUtilities.TurnState;

public class ContinuousStrikeStrategy implements ExecutionStrategy {
    @Override
    public void execute(Board board, Player[] players, Draught playerDraught, int x2, int y2) {
        if(players[TurnState.getTurn()].isStrikingDraught(playerDraught)){
            GameMechanics.captureDraught(board, players, playerDraught, x2, y2);
        }
        else{
            AlertMessenger.showAlert("Invalid continuous striking draught selected.");
        }
    }
}
