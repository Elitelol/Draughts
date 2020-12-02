package DataStructures;

import GameLogic.GameMechanics;

public class StrikeStrategy implements MovementStrategy {
    @Override
    public void execute(Board board, Player[] players, Draught playerDraught, int x2, int y2) {
        GameMechanics gameMechanics = GameMechanics.getInstance();

        gameMechanics.captureDraught(board, players, playerDraught, x2, y2);
    }
}
