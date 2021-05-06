package DataStructures;

import GameLogic.GameMechanics;

public class StrikeStrategy implements MovementStrategy {
    @Override
    public void execute(Board board, Player[] players, Draught playerDraught, Position moveToPos) {
        GameMechanics gameMechanics = GameMechanics.getInstance();

        gameMechanics.captureDraught(board, players, playerDraught, moveToPos);
    }
}
