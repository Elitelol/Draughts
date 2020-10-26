import java.util.ArrayList;

public class Player {
    private final String colour;
    private ArrayList<Draught> draughtList;
    private ArrayList<StrikingDraught> possibleStrikes;
    private final int movingSign;

    public Player(String colour){
        this.colour = colour;
        draughtList = generateDraughts();
        possibleStrikes = new ArrayList<>();

        if(colour.equals("White")){
            movingSign = -1;
        }
        else{
            movingSign = 1;
        }
    }

    public ArrayList<Draught> generateDraughts(){
        ArrayList<Draught> tempList = new ArrayList<>();
        int startingPos;
        int endingPos;
        String mark;

        if(colour.equals("White")){
            startingPos = 5;
            endingPos = 8;
            mark = "O";
        }
        else{
            startingPos = 0;
            endingPos = 3;
            mark = "Q";
        }

        for(int i = startingPos; i < endingPos; i++){
            int puttingPosition = i % 2 == 0 ? 1 : 0;

            for(int j = puttingPosition; j < 8; j += 2){
                tempList.add(new Draught(i, j, mark));
            }
        }

        return tempList;
    }

    public ArrayList<Draught> getDraughts(){
        return draughtList;
    }

    public int getDraughtSize(){
        return draughtList.size();
    }

    public Draught getDraught(int x, int y){
        for(Draught draught : draughtList){
            if(draught.getPosition().getX() == x && draught.getPosition().getY() == y){
                return draught;
            }
        }

        return null;
    }

    public void deleteDraught(int x, int y){
        for(Draught draught : draughtList){
            if(draught.getPosition().getX() == x && draught.getPosition().getY() == y){
                draughtList.remove(draught);
                break;
            }
        }
    }

    public int getMovingSign() {
        return movingSign;
    }

    public String getColour() {
        return colour;
    }

    public void addPossibleStrike(StrikingDraught strikingDraught){
        possibleStrikes.add(strikingDraught);
    }

    public int getPossibleStrikeSize(){
        return possibleStrikes.size();
    }

    public StrikingDraught getStrikingDraught(int x, int y){

        for(StrikingDraught strikingDraught : possibleStrikes){
            Position playerDraught = strikingDraught.getPlayerDraught();

            if(playerDraught.getX() == x && playerDraught.getY() == y){
                return strikingDraught;
            }
        }

        return null;
    }

    public ArrayList<StrikingDraught> getPossibleStrikes(){
        return  possibleStrikes;
    }

    public void clearStrikes(){
        possibleStrikes.clear();
    }

}
