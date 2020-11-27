package DataStructures;

import java.util.ArrayList;

public class GameRules {
    public static boolean checkIfLegalMove(Player [] players, Draught draught, int oldX, int oldY, int newX, int newY, int turn){
        if(!checkBounds(newX, newY)){
            return false;
        }
        else if(draught.getIsDame() && (Math.abs(newY - oldY) == Math.abs(oldX - newX))){
            return checkDameLegalMoves(players, oldX, oldY, newX, newY, turn);
        }
        else{
            return ((Math.abs(newY - oldY) == 1 && (newX - oldX) == players[turn].getMovingSign())
                    && isTileClear(newX, newY, players, turn));
        }
    }

    private static boolean checkDameLegalMoves(Player [] players, int oldX, int oldY, int newX, int newY, int turn){
        int xMovingDirection = getMovingDirection(oldX, newX);
        int yMovingDirection = getMovingDirection(oldY, newY);
        oldX += xMovingDirection;
        oldY += yMovingDirection;

        while(oldY != newY + yMovingDirection){
            if(!checkBounds(oldX, oldY)){
                break;
            }
            if(!isTileClear(oldX, oldY, players, turn)){
                return false;
            }
            oldX += xMovingDirection;
            oldY += yMovingDirection;
        }

        return true;
    }

    public static void checkForCaptures(Player[] players, int turn){
        int opponentTurn = getOpponentTurnNumber(turn);
        ArrayList<Draught> playerDraughts = players[turn].getDraughts();
        ArrayList<Draught> opponentDraughts = players[opponentTurn].getDraughts();

        for(Draught playerDraught : playerDraughts){
            if(playerDraught.getIsDame()){
                checkForCapturesAsDame(players, playerDraught, turn, 1, -1); // up left
                checkForCapturesAsDame(players, playerDraught, turn, -1, -1); // down left
                checkForCapturesAsDame(players, playerDraught, turn, 1, 1); // up right
                checkForCapturesAsDame(players, playerDraught, turn, -1, 1); // down right
            }
            else{
                checkForCapturesAsDraught(playerDraught, opponentDraughts, players, turn);
            }
        }
    }

    private static void checkForCapturesAsDame(Player [] players, Draught draught, int turn, int xMovingDirection, int yMovingDirection){
        int opponentTurn = getOpponentTurnNumber(turn);
        int x = draught.getX() + xMovingDirection;
        int y = draught.getY() + yMovingDirection;

        while(checkStrikingBounds(x, y) && players[turn].getDraught(x, y) == null){
            if(players[opponentTurn].getDraught(x, y) == null){
                x += xMovingDirection;
                y += yMovingDirection;
                continue;
            }
            if(isTileClear(x + xMovingDirection, y + yMovingDirection, players, turn)){
                StrikingDraught strikingDraught = new StrikingDraught(new Position(draught.getX(), draught.getY()), new Position(x, y), new Position(x + xMovingDirection, y + yMovingDirection));
                players[turn].addPossibleStrike(strikingDraught);
            }
            break;
        }
    }

    private static void checkForCapturesAsDraught(Draught playerDraught, ArrayList<Draught> opponentDraughts, Player [] players, int turn){
        for(Draught opponentDraught : opponentDraughts){
            if(!playerDraught.isDraughtNear(opponentDraught)){
                continue;
            }
            Position position = possibleForJump(playerDraught, opponentDraught, players, turn);

            if(position != null){
                StrikingDraught strikingDraught = new StrikingDraught(playerDraught.getPosition(), opponentDraught.getPosition(), position);

                players[turn].addPossibleStrike(strikingDraught);
            }
        }
    }

    private static Position possibleForJump(Draught playerDraught, Draught opponentDraught, Player[] players, int turn){
        int x = playerDraught.getX() + 2*(opponentDraught.getX() - playerDraught.getX());
        int y = playerDraught.getY() + 2*(opponentDraught.getY() - playerDraught.getY());

        if(!checkBounds(x, y) || (playerDraught.getX() + 2*players[turn].getMovingSign()) != x){
            return null;
        }
        else if(isTileClear(x, y, players, turn)){
            return new Position(x, y);
        }
        else{
            return null;
        }
    }

    private static boolean checkBounds(int x, int y){
        return (x >= 1 && x <= 8) && (y >= 1 && y <= 8);
    }

    private static boolean checkStrikingBounds(int x, int y){
        return (x >= 2 && x <= 7) && (y >= 2 && y <= 7);
    }

    private static int getOpponentTurnNumber(int turn){
        return turn == 0 ? 1 : 0;
    }

    private static int getMovingDirection(int oldPos, int newPos){
        return oldPos < newPos ? 1 : -1;
    }

    private static boolean isTileClear(int x, int y, Player [] players, int turn){
        int opponentTurn = getOpponentTurnNumber(turn);

        return players[turn].getDraught(x, y) == null && players[opponentTurn].getDraught(x, y) == null;
    }
}
