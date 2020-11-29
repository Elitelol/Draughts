package DataStructures;

import java.util.ArrayList;

public abstract class Player {
    private final String colour;
    private final ArrayList<Draught> draughtList;
    private final int movingDirection;
    private Draught strikingDraught;

    public Player(String colour, int movingDirection){
        this.colour = colour;
        draughtList = new ArrayList<>();
        this.movingDirection = movingDirection;
        this.strikingDraught = null;
    }

    public abstract void generateDraughts();

    public ArrayList<Draught> getDraughts(){
        return draughtList;
    }

    public void addDraught(Draught draught){
        draughtList.add(draught);
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

    public int getMovingDirection() {
        return movingDirection;
    }

    public String getColour() {
        return colour;
    }

    public Draught getStrikingDraught() {
        return strikingDraught;
    }

    public boolean isAbleToStrike(){
        for(Draught draught : draughtList){
            if(draught.getStrikingSize() > 0){
                return true;
            }
        }

        return false;
    }

    public void clearStrikes(){
        for(Draught draught : draughtList){
            draught.clearStrikes();
        }
    }

    public void setStrikingDraught(Draught strikingDraught) {
        this.strikingDraught = strikingDraught;
    }

    public boolean isContinuousStrike(){
        Draught draught = getDraught(strikingDraught.getX(), strikingDraught.getY());

        return draught.getStrikingSize() > 0;
    }

    public boolean isStrikingDraught(Draught draught){
        if(strikingDraught != null){
            return strikingDraught.getX() == draught.getX() && strikingDraught.getY() == draught.getY();
        }

        return false;
    }
}
