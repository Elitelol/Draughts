package DataStructures;

import GameLogic.GameMechanics;
import GameLogic.GameRules;
import GameUtilities.AlertMessenger;
import GameUtilities.TurnState;

public class SimpleMoveStrategy implements MovementStrategy {
    @Override
    public void execute(Board board, Player[] players, Draught playerDraught, Position moveToPos) {
        GameRules gameRules = GameRules.getInstance();
        GameMechanics gameMechanics = GameMechanics.getInstance();

        if(gameRules.checkIfLegalMove(players, playerDraught, moveToPos)){
            gameMechanics.moveDraught(board, playerDraught, moveToPos, players[TurnState.getTurn()].getColour());
            TurnState.changeTurn();
        }
        else{
            AlertMessenger.showAlert("You cannot move to that position.");
        }
    }
}
