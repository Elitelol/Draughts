package GameLogic;
import DataStructures.*;
import GameUtilities.TurnState;
import java.util.ArrayList;

public class GameRules {
    private static final GameRules instance = new GameRules();

    private GameRules(){}

    public static GameRules getInstance(){
        return instance;
    }

    public boolean checkIfLegalMove(Player[] players, Draught draught, int oldX, int oldY, int newX, int newY){
        if(!checkBounds(newX, newY)){
            return false;
        }
        else if(draught.getIsDame() && (Math.abs(newY - oldY) == Math.abs(oldX - newX))){
            return checkDameLegalMove(players, oldX, oldY, newX, newY);
        }
        else{
            return ((Math.abs(newY - oldY) == 1 && (newX - oldX) == players[TurnState.getTurn()].getMovingDirection())
                    && isTileClear(newX, newY, players));
        }
    }

    private boolean checkDameLegalMove(Player [] players, int oldX, int oldY, int newX, int newY){
        int xMovingDirection = getMovingDirection(oldX, newX);
        int yMovingDirection = getMovingDirection(oldY, newY);
        oldX += xMovingDirection;
        oldY += yMovingDirection;

        while(oldY <= newY && checkBounds(oldX, oldY)){ // TODO: pataisyti i aiskesni while
            if(!isTileClear(oldX, oldY, players)){
                return false;
            }
            oldX += xMovingDirection;
            oldY += yMovingDirection;
        }

        return true;
    }

    public void checkForStrikes(Player[] players){
        ArrayList<Draught> playerDraughts = players[TurnState.getTurn()].getDraughts();
        ArrayList<Draught> opponentDraughts = players[TurnState.getOpponentTurn()].getDraughts();

        for(Draught playerDraught : playerDraughts){
            if(playerDraught.getIsDame()){
                checkForStrikesAsDame(players, playerDraught, 1, -1); // down left
                checkForStrikesAsDame(players, playerDraught, -1, -1); // up left
                checkForStrikesAsDame(players, playerDraught, 1, 1); // down right
                checkForStrikesAsDame(players, playerDraught, -1, 1); // up right
            }
            else{
                checkForStrikesAsDraught(playerDraught, opponentDraughts, players);
            }
        }
    }

    private void checkForStrikesAsDame(Player [] players, Draught draught, int xMovingDirection, int yMovingDirection){
        int x = draught.getX() + xMovingDirection;
        int y = draught.getY() + yMovingDirection;

        while(checkBounds(x + xMovingDirection, y + yMovingDirection) && players[TurnState.getTurn()].getDraught(x, y) == null){
            if(players[TurnState.getOpponentTurn()].getDraught(x, y) == null){
                x += xMovingDirection;
                y += yMovingDirection;
                continue;
            }
            if(isTileClear(x + xMovingDirection, y + yMovingDirection, players)){
                StrikeableDraught strikeableDraught = new StrikeableDraught(new Position(x, y), new Position(x + xMovingDirection, y + yMovingDirection));
                draught.addStrikingDraughts(strikeableDraught);
            }
            break;
        }
    }

    private void checkForStrikesAsDraught(Draught playerDraught, ArrayList<Draught> opponentDraughts, Player [] players){
        for(Draught opponentDraught : opponentDraughts){
            if(!playerDraught.isDraughtNear(opponentDraught)){
                continue;
            }
            Position jumpingPosition = possibleForJump(playerDraught, opponentDraught, players);

            if(jumpingPosition != null){
                StrikeableDraught strikeableDraught = new StrikeableDraught(opponentDraught.getPosition(), jumpingPosition);

                playerDraught.addStrikingDraughts(strikeableDraught);
            }
        }
    }

    private Position possibleForJump(Draught playerDraught, Draught opponentDraught, Player [] players){
        int x = playerDraught.getX() + 2*(opponentDraught.getX() - playerDraught.getX());
        int y = playerDraught.getY() + 2*(opponentDraught.getY() - playerDraught.getY());

        if(!checkBounds(x, y) || (playerDraught.getX() + 2*players[TurnState.getTurn()].getMovingDirection()) != x){
            return null;
        }
        else if(isTileClear(x, y, players)){
            return new Position(x, y);
        }
        else{
            return null;
        }
    }

    private boolean checkBounds(int x, int y){
        return (x >= 1 && x <= 8) && (y >= 1 && y <= 8);
    }

    private int getMovingDirection(int oldPos, int newPos){
        return oldPos < newPos ? 1 : -1;
    }

    private boolean isTileClear(int x, int y, Player [] players){
        return players[TurnState.getTurn()].getDraught(x, y) == null
                && players[TurnState.getOpponentTurn()].getDraught(x, y) == null;
    }
}
