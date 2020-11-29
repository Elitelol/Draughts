package DataStructures;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Board {
    private final Group circles = new Group();
    private final Group squares = new Group();

    public void generateBoard(){
        squares.getChildren().clear();

        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                Rectangle rectangle = new Rectangle(j * 100, i * 100, 100, 100);

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

    public Group populateWithCircles(){
        for(int i = 1; i <= 8; i++){
            for(int j = 1; j <= 8; j++){
                if((i + j) % 2 != 0){
                    if(i <= 3){
                        Circle circle = new Circle((j*100)-50, (i*100)-50, 35);

                        circle.setFill(Color.BLACK);
                        circles.getChildren().add(circle);
                    }
                    if(i >= 6){
                        Circle circle = new Circle((j*100)-50, (i*100)-50, 35);
                        circle.setFill(Color.WHITE);
                        circles.getChildren().add(circle);
                    }
                }
            }
        }

        return circles;
    }

    public Group getCircles(){
        return circles;
    }

    public void removeCircle(int x, int y){
        x = (x * 100) - 50;
        y = (y * 100) - 50;

        for(Node circle : circles.getChildren()){
            Circle temp = (Circle) circle;

            if(temp.getCenterX() == y && temp.getCenterY() == x){
                circles.getChildren().remove(circle);
                break;
            }
        }
    }

    public void addCircle(int x, int y, String playerColour, boolean isDame){
        x = (x * 100) - 50;
        y = (y * 100) - 50;
        Color circleColour;

        if(playerColour.equals("White")){
            circleColour = Color.WHITE;
        }
        else{
            circleColour = Color.BLACK;
        }

        Circle circle = new Circle(y, x, 30, circleColour);

        if(isDame){
            circle.setStrokeWidth(10);
            circle.setStroke(Color.DARKRED);
        }

        circles.getChildren().add(circle);
    }
}
