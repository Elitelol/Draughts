package DataStructures;

public class StrikeableDraught {
    private final Position opponentDraught;
    private final Position jumpingPosition;

    public StrikeableDraught(Position opponentDraught, Position jumpingPosition){
        this.opponentDraught = opponentDraught;
        this.jumpingPosition = jumpingPosition;
    }

    public Position getOpponentDraught(){
        return  opponentDraught;
    }

    public Position getJumpingPosition(){
        return jumpingPosition;
    }
}