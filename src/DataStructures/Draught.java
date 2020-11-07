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

    public boolean getIsDame(){
        return isDame;
    }

    public void setDame(){
        isDame = true;
    }
}
