import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.Scanner;

public class GameMechanics {

    public GameMechanics(){

    }

    public void checkForCaptures(Player[] players, int turn){
        int opponentTurn = getOpponentTurnNumber(turn);
        ArrayList<Draught> playerDraughts = players[turn].getDraughts();
        ArrayList<Draught> opponentDraughts = players[opponentTurn].getDraughts();

        for(Draught playerDraught : playerDraughts){
            for(Draught opponentDraught : opponentDraughts){
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
        return (x >= 0 && x < 8) && (y >= 0 && y < 8);
    }

    public void moveDraught(ConsoleBoard board, Draught draught, int newX, int newY){
        board.setBoard(newX, newY, draught.getMark());
        board.setBoard(draught.getPosition().getX(), draught.getPosition().getY(), " ");
        draught.getPosition().setX(newX);
        draught.getPosition().setY(newY);
    }

    public void captureDraught(ConsoleBoard board, Player[] players, int turn){
        Scanner scanner = new Scanner(System.in);
        int opponentTurn = getOpponentTurnNumber(turn);

        while(true){
            System.out.println("White: " + players[0].getDraughtSize() + " " + "Black: " + players[1].getDraughtSize());
            System.out.println(players[turn].getColour() + " turn.");
            System.out.println("Type coordinates to choose a striking draught e.g 5;0");
            String[] data = scanner.next().split(";");
            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt((data[1]));
            StrikingDraught strikingDraught = players[turn].getStrikingDraught(x, y);

            if(strikingDraught != null){
                Position playerPos = strikingDraught.getPlayerDraught();
                Position opponentPos = strikingDraught.getOpponentDraught();
                Position jumpingPos = strikingDraught.getJumpingPosition();
                Draught playerDraught = players[turn].getDraught(playerPos.getX(), playerPos.getY());
                Draught opponentDraught = players[opponentTurn].getDraught(opponentPos.getX(), opponentPos.getY());

                String[] newData = scanner.next().split(";");
                int captureX = Integer.parseInt(newData[0]);
                int captureY = Integer.parseInt(newData[1]);

                if(captureX == jumpingPos.getX() && captureY == jumpingPos.getY()){
                    moveDraught(board, playerDraught, captureX, captureY);
                    removeDraught(board, players[opponentTurn], opponentDraught);
                    break;
                }

            }
            else{
                System.out.println("You must choose a striking draught.");
            }
        }
        players[turn].clearStrikes();
    }
    public void removeDraught(ConsoleBoard board, Player player, Draught draught){
        board.setBoard(draught.getPosition().getX(), draught.getPosition().getY(), " ");
        player.deleteDraught(draught.getPosition().getX(), draught.getPosition().getY());
    }

    public int getOpponentTurnNumber(int turn){
        return turn == 0 ? 1 : 0;
    }
}
