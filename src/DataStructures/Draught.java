package DataStructures;

import java.util.ArrayList;

public class Draught {
    private final Position position;
    private boolean isDame;
    private final ArrayList<StrikeableDraught> strikeableDraughts;

    public Draught(int x, int y){
        this.position = new Position(x, y);
        this.isDame = false;
        this.strikeableDraughts = new ArrayList<>();
    }

    public Position getPosition() {
        return position;
    }

    public int getX(){
        return position.getX();
    }

    public int getY(){
        return position.getY();
    }

    public void setX(int x){
        position.setX(x);
    }

    public void setY(int y){
        position.setY(y);
    }

    public boolean getIsDame(){
        return isDame;
    }

    public void setDame(){
        isDame = true;
    }

    private int getXDistance(Draught draught){
        return Math.abs(this.getX() - draught.getX());
    }

    private int getYDistance(Draught draught){
        return Math.abs(this.getY() - draught.getY());
    }

    public boolean isDraughtNear(Draught draught){
        return getXDistance(draught) == 1 && getYDistance(draught) == 1;
    }

    public void addStrikingDraughts(StrikeableDraught strikeableDraught){
        strikeableDraughts.add(strikeableDraught);
    }

    public int getStrikingSize(){
        return strikeableDraughts.size();
    }

    public StrikeableDraught getStrikingDraught(int x, int y){
        for(StrikeableDraught strikeableDraught : strikeableDraughts){
            if(strikeableDraught.getJumpingPosition().getX() == x && strikeableDraught.getJumpingPosition().getY() == y){
                return strikeableDraught;
            }
        }

        return null;
    }

    public void clearStrikes(){
        strikeableDraughts.clear();
    }
}
