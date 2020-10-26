public class StrikingDraught {
    private Position playerDraught;
    private Position opponentDraught;
    private Position jumpingPosition;

    public StrikingDraught(Position playerDraught, Position opponentDraught, Position jumpingPosition){
        this.playerDraught = playerDraught;
        this.opponentDraught = opponentDraught;
        this.jumpingPosition = jumpingPosition;
    }

    public Position getPlayerDraught(){
        return playerDraught;
    }

    public Position getOpponentDraught(){
        return  opponentDraught;
    }

    public Position getJumpingPosition(){
        return jumpingPosition;
    }
}
