package TestUtilities;

import DataStructures.*;
import GameLogic.GameMechanics;
import GameLogic.GameRules;
import junit.framework.TestCase;

public class StrikingTester extends TestCase {
    private Player[] players;
    private GameMechanics gameMechanics;
    private GameRules gameRules;
    private Board board;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gameMechanics = GameMechanics.getInstance();
        gameRules = GameRules.getInstance();
        board = new Board();
        players = new Player[]{new WhitePlayer(), new BlackPlayer()};
    }

    public void testDraughtStriking(){
        //arrange
        players[0].addDraught(new Draught(6, 3));
        players[1].addDraught(new Draught(5, 2));
        Draught draught = players[0].getDraught(6, 3);

        //act
        gameRules.checkForStrikes(players);
        gameMechanics.captureDraught(board, players, draught, new Position(4, 1));

        //assert
        assertNull(players[1].getDraught(5, 2));
    }

    public void testDameStriking(){
        //arrange
        players[0].addDraught(new Draught(2, 5));
        players[1].addDraught(new Draught(4, 7));
        Draught draught = players[0].getDraught(2, 5);
        draught.setDame();

        //act
        gameRules.checkForStrikes(players);
        gameMechanics.captureDraught(board, players, draught, new Position(5, 8));

        //assert
        assertNull(players[1].getDraught(4, 7));
    }
}
