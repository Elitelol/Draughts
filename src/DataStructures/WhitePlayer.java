package DataStructures;

public class WhitePlayer extends Player {
    public WhitePlayer() {
        super("White", -1);
    }

    @Override
    public void generateDraughts() {
        for(int i = 6; i <= 8; i++){
            int puttingPosition = i % 2 == 0 ? 1 : 2;

            for(int j = puttingPosition; j <= 8; j += 2){
                addDraught(new Draught(i, j));
            }
        }
    }
}
