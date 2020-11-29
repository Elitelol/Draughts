package DataStructures;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DraughtsApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Board board = new Board();
        board.generateBoard();
        board.populateWithCircles();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("GameController.fxml"));
        Parent root = loader.load();

        GameController gameController = loader.getController();
        gameController.setBoard(board);
        gameController.turnLabel.setText("Turn: White");
        gameController.stackPane.getChildren().addAll(board.getBoard(), board.getCircles());

        primaryStage.setTitle("Draughts");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
