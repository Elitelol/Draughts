package GameUtilities;
import javafx.scene.control.Alert;

public class AlertMessenger {
    public static void showAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setContentText(null);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
