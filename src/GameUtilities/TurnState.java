package GameUtilities;

public class TurnState {
    private static int turn = 0;

    public static void changeTurn(){
        turn = turn == 0 ? 1 : 0;
    }

    public static int getTurn() {
        return turn;
    }

    public static int getOpponentTurn(){
        return turn == 0 ? 1 : 0;
    }
}
