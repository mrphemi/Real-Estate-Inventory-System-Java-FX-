package helpers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class AlertBox {
    public static void display(String title, String message){
        Stage window = new Stage();
        window.setTitle(title);
        window.setMinWidth(400);
        window.initModality(Modality.APPLICATION_MODAL);

        Button button = new Button("OK");
        button.setOnAction(e -> window.close());
        Label label = new Label(message);

        VBox layout = new VBox();
        layout.getChildren().addAll(label, button);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 120, 100);
        window.setScene(scene);
        window.showAndWait();
    }
}
