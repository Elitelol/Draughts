package TestUtilities;

import DataStructures.*;
import GameLogic.GameRules;
import junit.framework.TestCase;

public class MovementTester extends TestCase {
    private Player [] players;
    private GameRules gameRules;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        gameRules = GameRules.getInstance();
        players = new Player[]{new WhitePlayer(), new BlackPlayer()};
    }

    public void testMovementNotAllowed(){
        //arrange
        players[0].addDraught(new Draught(4, 3));
        players[1].addDraught(new Draught(3, 4));
        Draught draught = players[0].getDraught(4, 3);

        //act
        boolean movementResult = gameRules.checkIfLegalMove(players, draught, new Position(3,4));

        //assert
        assertFalse(movementResult);
    }

    public void testMovementAllowed(){
        //arrange
        players[0].addDraught(new Draught(8, 1));
        Draught draught = players[0].getDraught(8, 1);

        //act
        boolean movementResult = gameRules.checkIfLegalMove(players, draught, new Position(7,2));

        //assert
        assertTrue(movementResult);
    }

    public void testMovementOutOfBoard(){
        //arrange
        players[0].addDraught(new Draught(1, 8));
        Draught draught = players[0].getDraught(1, 8);

        //act
        boolean movementResult = gameRules.checkIfLegalMove(players, draught, new Position(0, 9));

        //assert
        assertFalse(movementResult);
    }

    public void testStraightMovement(){
        //arrange
        players[0].addDraught(new Draught(1, 8));
        Draught draught = players[0].getDraught(1, 8);

        //act
        boolean movementResult = gameRules.checkIfLegalMove(players, draught, new Position(2, 8));

        //assert
        assertFalse(movementResult);
    }

    public void testDameMovement(){
        //arrange
        players[0].addDraught(new Draught(4, 5));
        Draught draught = players[0].getDraught(4, 5);
        draught.setDame();

        //act
        boolean movementResult = gameRules.checkIfLegalMove(players, draught, new Position(7, 2));

        //assert
        assertTrue(movementResult);
    }
}
