package DataStructures;

import javafx.scene.control.Alert;

import java.util.ArrayList;
import java.util.Scanner;

public class GameMechanics {

    public GameMechanics(){

    }

    public boolean checkIfLegalMove(Player [] players, Draught draught, int oldX, int oldY, int newX, int newY, int turn){
        int oppositeTurn = getOpponentTurnNumber(turn);
        int xDiff = newX - oldX;
        int yDiff = Math.abs(oldY - newY);

        if(checkBounds(newX, newY)){
            if(draught.getIsDame()){
                if((xDiff != 0 && yDiff == 0) || (xDiff == 0 && yDiff != 0)){
                    return false;
                }
                else {
                    return checkDameLegalMoves(players, oldX, oldY, newX, newY, turn);
                }
            }
            else{
                if(xDiff != players[turn].getMovingSign() || yDiff != 1){
                    return false;
                }
                else{
                    return players[turn].getDraught(newX, newY) == null && players[oppositeTurn].getDraught(newX, newY) == null;
                }
            }
        }

        return false;
    }

    public boolean checkDameLegalMoves(Player [] players, int oldX, int oldY, int newX, int newY, int turn){
        int xMovingSign;
        int yMovingSign;
        int oppositeTurn = getOpponentTurnNumber(turn);

        if(oldX < newX){
            xMovingSign = 1;
        }
        else{
            xMovingSign = -1;
        }
        if(oldY < newY){
            yMovingSign = 1;
        }
        else {
            yMovingSign = -1;
        }

        oldX += xMovingSign;
        oldY += yMovingSign;

        while(oldY != newY + yMovingSign){
            if(!checkBounds(oldX, oldY)){
                break;
            }
            if(players[turn].getDraught(oldX, oldY) != null || players[oppositeTurn].getDraught(oldX, oldY) != null){
                return false;
            }

            oldX += xMovingSign;
            oldY += yMovingSign;
        }

        return true;
    }

    public void checkForCaptures(Player[] players, int turn){
        int opponentTurn = getOpponentTurnNumber(turn);
        ArrayList<Draught> playerDraughts = players[turn].getDraughts();
        ArrayList<Draught> opponentDraughts = players[opponentTurn].getDraughts();

        for(Draught playerDraught : playerDraughts){
            for(Draught opponentDraught : opponentDraughts){
                if(playerDraught.getIsDame()){
                    checkForCapturesAsDame(players, playerDraught, turn);
                    break;
                }

                int playerX = playerDraught.getPosition().getX();
                int playerY = playerDraught.getPosition().getY();
                int opponentX = opponentDraught.getPosition().getX();
                int opponentY = opponentDraught.getPosition().getY();
                int xDiff = Math.abs(playerX - opponentX);
                int yDiff = Math.abs(playerY - opponentY);

                if(xDiff == 1 && yDiff == 1){
                    Position position = possibleForJump(playerDraught,opponentDraught, players, turn);

                    if(position != null){
                        players[turn].addPossibleStrike(new StrikingDraught(new Position(playerX, playerY), new Position(opponentX, opponentY), position));
                    }

                }
            }
        }
    }

    public void checkForCapturesAsDame(Player [] players, Draught draught, int turn){
        int opponentTurn = getOpponentTurnNumber(turn);
        int x1 = draught.getPosition().getX();
        int y1 = draught.getPosition().getY();
        int x2 = x1;
        int y2 = y1;

        //goes up and right
        for(int i = y2+1; i <= 7; i++){
            x2--;
            if(!checkBounds(x2, i) || !checkBounds(x2 - 1, i+ 1)){
                break;
            }
            if(players[opponentTurn].getDraught(x2, i) != null){
                if(players[turn].getDraught(x2 - 1, i + 1) == null && players[opponentTurn].getDraught(x2 - 1, i + 1) == null){
                    players[turn].addPossibleStrike(new StrikingDraught (new Position(x1, y1), new Position(x2, i), new Position( x2-1, i+1)));
                }
                break;
            }
        }
        x2 = x1;

        // goes down and right
        for(int i = y2+1; i <= 7; i++){
            x2++;
            if(!checkBounds(x2, i) || !checkBounds(x2 + 1, i + 1)){
                break;
            }
            if(players[opponentTurn].getDraught(x2, i) != null){
                if(players[turn].getDraught(x2 + 1, i + 1) == null && players[opponentTurn].getDraught(x2 + 1, i + 1) == null){
                    players[turn].addPossibleStrike(new StrikingDraught (new Position(x1, y1), new Position(x2, i), new Position( x2+1, i+1)));
                }
                break;
            }
        }
        x2 = x1;

        // goes up and left
        for(int i = y2-1; i >= 2; i--){
            x2--;
            if(!checkBounds(x2, i) || !checkBounds(x2 - 1, i - 1)){
                break;
            }
            if(players[opponentTurn].getDraught(x2, i) != null){
                if(players[turn].getDraught(x2 - 1, i - 1) == null && players[opponentTurn].getDraught(x2 - 1, i - 1) == null){
                    players[turn].addPossibleStrike(new StrikingDraught (new Position(x1, y1), new Position(x2, i), new Position( x2-1, i-1)));
                }
                break;
            }
        }
        x2 = x1;

        // goes down and left
        for(int i = y2-1; i >= 2; i--){
            x2++;
            if(!checkBounds(x2, i) || !checkBounds(x2 + 1, i- 1)){
                break;
            }
            if(players[opponentTurn].getDraught(x2, i) != null){
                if(players[turn].getDraught(x2 + 1, i - 1) == null && players[opponentTurn].getDraught(x2 + 1, i - 1) == null){
                    players[turn].addPossibleStrike(new StrikingDraught (new Position(x1, y1), new Position(x2, i), new Position( x2+1, i-1)));
                }
                break;
            }
        }
    }

    public Position possibleForJump(Draught playerDraught, Draught opponentDraught, Player[] players, int turn){
        int sign = players[turn].getMovingSign();
        int x = playerDraught.getPosition().getX() + sign;
        int y = playerDraught.getPosition().getY();
        int opponentTurn = getOpponentTurnNumber(turn);

        if(playerDraught.getPosition().getY() > opponentDraught.getPosition().getY()){
            if(!checkBounds(x + sign, y - 2)){
                return null;
            }
            if(players[opponentTurn].getDraught(x, y-1) != null && players[opponentTurn].getDraught(x+sign, y-2) == null && players[turn].getDraught(x+sign, y-2) == null ){
                return new Position(x + sign, y - 2);
            }
        }
        else{
            if(!checkBounds(x + sign, y +2)){
                return null;
            }
            if(players[opponentTurn].getDraught(x, y+1) != null && players[opponentTurn].getDraught(x+sign, y+2) == null && players[turn].getDraught(x+sign, y+2) == null){
                return new Position(x + sign, y + 2);
            }
        }

        return null;
    }

    public boolean checkBounds(int x, int y){
        return (x >= 1 && x <= 8) && (y >= 1 && y <= 8);
    }

    public void moveDraught(Board board, Draught draught, int newX, int newY, String colour){
        board.removeCircle(draught.getPosition().getX(), draught.getPosition().getY());
        draught.getPosition().setX(newX);
        draught.getPosition().setY(newY);

        if(newX == 1 && colour.equals("White") || newX == 8 && colour.equals("Black") && !draught.getIsDame()){
            draught.setDame();
        }

        board.addCircle(newX, newY, colour, draught.getIsDame());
    }

    public boolean captureDraught(Board board, Player[] players, int turn, int x1, int y1, int x2, int y2){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(null);
        int opponentTurn = getOpponentTurnNumber(turn);
        StrikingDraught strikingDraught = players[turn].getStrikingDraught(x1, y1);

        if(strikingDraught != null){
            Position playerPos = strikingDraught.getPlayerDraught();
            Position opponentPos = strikingDraught.getOpponentDraught();
            Position jumpingPos = strikingDraught.getJumpingPosition();
            Draught playerDraught = players[turn].getDraught(playerPos.getX(), playerPos.getY());
            Draught opponentDraught = players[opponentTurn].getDraught(opponentPos.getX(), opponentPos.getY());

            if(x2 == jumpingPos.getX() && y2 == jumpingPos.getY()){
                moveDraught(board, playerDraught, x2, y2, players[turn].getColour());
                removeDraught(board, players[opponentTurn], opponentDraught);
                players[turn].clearStrikes();
                return true;
            }
            else{
                alert.setHeaderText("Incorrect jumping position.");
                alert.showAndWait();
            }
        }
        else{
            alert.setHeaderText("You must choose a striking draught.");
            alert.showAndWait();
        }
        players[turn].clearStrikes();
        return false;
    }
    public void removeDraught(Board board, Player player, Draught draught){
        board.removeCircle(draught.getPosition().getX(), draught.getPosition().getY());
        player.deleteDraught(draught.getPosition().getX(), draught.getPosition().getY());
    }

    public int getOpponentTurnNumber(int turn){
        return turn == 0 ? 1 : 0;
    }
}