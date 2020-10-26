import java.util.ArrayList;

public class ConsoleBoard {
    private String[][] board;

    public ConsoleBoard(){
        board = new String[][]
                {{"#", " ", "#", " ", "#", " ", "#", " "},
                {" ", "#", " ", "#", " ", "#", " ", "#"},
                {"#", " ", "#", " ", "#", " ", "#", " "},
                {" ", "#", " ", "#", " ", "#", " ", "#"},
                {"#", " ", "#", " ", "#", " ", "#", " "},
                {" ", "#", " ", "#", " ", "#", " ", "#"},
                {"#", " ", "#", " ", "#", " ", "#", " "},
                {" ", "#", " ", "#", " ", "#", " ", "#"}};
    }

    public void printBoard(){
        for(int i = 0; i < board.length; i++){
            System.out.print(i + "\t");
            for(int j = 0; j < board[0].length; j++){
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }

        System.out.println("    0   1   2   3   4   5   6   7");
    }

    public void setBoard(int x, int y, String mark){
        board[x][y] = mark;
    }

    public void setXMarks(ArrayList<StrikingDraught> strikingDraughts){
        for(StrikingDraught strikingDraught : strikingDraughts){
            Position jumpingPos = strikingDraught.getJumpingPosition();
            setBoard(jumpingPos.getX(), jumpingPos.getY(), "X");
        }
    }

    public void clearXMarks(){
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board[0].length; j++){
                if(board[i][j].equals("X")){
                    setBoard(i, j, " ");
                }
            }
        }
    }
}
