import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    private ConsoleBoard board;
    private GameMechanics gameMechanics;
    private Player[] players;

    public Game(){
        board = new ConsoleBoard();
        players = new Player[2];
        gameMechanics = new GameMechanics();
        players[0] = new Player("White");
        players[1] = new Player("Black");
    }

    public void prepareBoard(Player player){
        ArrayList<Draught> playerDraughts = player.getDraughts();

        for(Draught draught : playerDraughts){
            int x = draught.getPosition().getX();
            int y = draught.getPosition().getY();
            String mark = draught.getMark();

            board.setBoard(x, y, mark);
        }
    }

    public void start(){
        Scanner scanner = new Scanner(System.in);
        prepareBoard(players[0]);
        prepareBoard(players[1]);
        int turn = 0;
        boolean continuousStrike = false;

        while(true){
            if(players[0].getDraughtSize() == 0){
                System.out.println(players[0].getColour() + " lost. GG");
                break;
            }
            if(players[1].getDraughtSize() == 0){
                System.out.println(players[1].getColour() + " lost. GG");
                break;
            }

            gameMechanics.checkForCaptures(players, turn);

            if(players[turn].getPossibleStrikeSize() > 0){
                System.out.println("Striking is available");
                board.setXMarks(players[turn].getPossibleStrikes());
                board.printBoard();
                gameMechanics.captureDraught(board, players, turn);
                board.clearXMarks();
                continuousStrike = true;
                continue;
            }
            if(players[turn].getPossibleStrikeSize() == 0 && continuousStrike){
                turn = changeTurn(turn);
                continuousStrike = false;
                continue;
            }
            System.out.println("White: " + players[0].getDraughtSize() + " " + "Black: " + players[1].getDraughtSize());
            System.out.println(players[turn].getColour() + " turn. Type coordinates to choose a draught e.g 5;0");
            System.out.println("To surrender type g;g");
            board.printBoard();
            String[] data = scanner.next().split(";");

            if(data[0].equals("g") && data[1].equals("g")){
                System.out.println(players[turn].getColour() + " resigned. GG");
                break;
            }

            int x = Integer.parseInt(data[0]);
            int y = Integer.parseInt(data[1]);
            Draught draught = players[turn].getDraught(x, y);

            if(draught != null){
                System.out.println("Type coordinates to move the draught.");
                String[] newData = scanner.next().split(";");
                int newX = Integer.parseInt(newData[0]);
                int newY = Integer.parseInt(newData[1]);
                gameMechanics.moveDraught(board, draught, newX, newY);
            }
            else{
                System.out.println("Draught doesn't exist");
                continue;
            }

            turn = changeTurn(turn);
        }
    }

    public int changeTurn(int turn){
        return turn == 0 ? 1 : 0;
    }
}
