public class Draught {
    private Position position;
    private String mark;

    public Draught(int x, int y, String mark){
        this.position = new Position(x, y);
        this.mark = mark;
    }

    public Position getPosition() {
        return position;
    }

    public String getMark() {
        return mark;
    }
}
