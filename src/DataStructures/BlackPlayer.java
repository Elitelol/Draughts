package DataStructures;

public class BlackPlayer extends Player {
    public BlackPlayer() {
        super("Black", 1);
    }

    @Override
    public void generateDraughts() {
        for(int i = 1; i <= 3; i++){
            int puttingPosition = i % 2 == 0 ? 1 : 2;

            for(int j = puttingPosition; j <= 8; j += 2){
                addDraught(new Draught(i, j));
            }
        }
    }
}
