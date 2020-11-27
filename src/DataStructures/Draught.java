package DataStructures;

public class Draught {
    private Position position;
    private boolean isDame;

    public Draught(int x, int y){
        this.position = new Position(x, y);
        this.isDame = false;
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
}
