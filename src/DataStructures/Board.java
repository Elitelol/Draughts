package DataStructures;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Board {
    private final Group circles = new Group();
    private final Group squares = new Group();
    private final int width = 8;
    private final int height = 8;
    private final int TILE_SIZE = 100;
    private final int CIRCLE_RADIUS = 35;

    public void generateBoard(){
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Rectangle rectangle = new Rectangle(j * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                if((i + j) % 2 == 0){
                    rectangle.setFill(Color.GREY);
                }
                else{
                    rectangle.setFill(Color.DARKBLUE);
                }
                squares.getChildren().add(rectangle);
            }
        }
    }

    public Group getBoard(){
        return squares;
    }

    public void populateWithCircles(){

        for(int i = 1; i <= height; i++){
            for(int j = 1; j <= width; j++){
                if((i + j) % 2 != 0){
                    if(i <= 3){
                        Circle circle = new Circle((j*TILE_SIZE)-TILE_SIZE/2, (i*TILE_SIZE)-TILE_SIZE/2, CIRCLE_RADIUS);

                        circle.setFill(Color.BLACK);
                        circles.getChildren().add(circle);
                    }
                    if(i >= 6){
                        Circle circle = new Circle((j*TILE_SIZE)-TILE_SIZE/2, (i*TILE_SIZE)-TILE_SIZE/2, CIRCLE_RADIUS);

                        circle.setFill(Color.WHITE);
                        circles.getChildren().add(circle);
                    }
                }
            }
        }
    }

    public Group getCircles(){
        return circles;
    }

    public void removeCircle(int x, int y){
        x = (x * TILE_SIZE) - TILE_SIZE/2;
        y = (y * TILE_SIZE) - TILE_SIZE/2;

        for(Node circle : circles.getChildren()){
            Circle temp = (Circle) circle;

            if(temp.getCenterX() == y && temp.getCenterY() == x){
                circles.getChildren().remove(circle);
                break;
            }
        }
    }

    public void addCircle(int x, int y, String playerColour, boolean isDame){
        x = (x * TILE_SIZE) - TILE_SIZE/2;
        y = (y * TILE_SIZE) - TILE_SIZE/2;
        Color circleColour;

        if(playerColour.equals("White")){
            circleColour = Color.WHITE;
        }
        else{
            circleColour = Color.BLACK;
        }
        Circle circle = new Circle(y, x, CIRCLE_RADIUS, circleColour);

        if(isDame){
            circle.setStrokeWidth(10);
            circle.setStroke(Color.DARKRED);
        }
        circles.getChildren().add(circle);
    }
}
