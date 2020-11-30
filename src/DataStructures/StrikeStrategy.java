package DataStructures;

import GameLogic.GameMechanics;

public class StrikeStrategy implements ExecutionStrategy {
    @Override
    public void execute(Board board, Player[] players, Draught playerDraught, int x2, int y2) {
        GameMechanics.captureDraught(board, players, playerDraught, x2, y2);
    }
}
