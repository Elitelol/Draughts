package DataStructures;

public interface MovementStrategy {
     void execute(Board board, Player [] players, Draught playerDraught, Position moveToPos);
}
