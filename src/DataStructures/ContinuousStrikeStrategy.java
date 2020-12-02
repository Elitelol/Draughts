package DataStructures;

import GameLogic.GameMechanics;
import GameUtilities.AlertMessenger;
import GameUtilities.TurnState;

public class ContinuousStrikeStrategy implements MovementStrategy {
    @Override
    public void execute(Board board, Player[] players, Draught playerDraught, int x2, int y2) {
        GameMechanics gameMechanics = GameMechanics.getInstance();

        if(players[TurnState.getTurn()].isStrikingDraught(playerDraught)){
            gameMechanics.captureDraught(board, players, playerDraught, x2, y2);
        }
        else{
            AlertMessenger.showAlert("Invalid continuous striking draught selected.");
        }
    }
}
