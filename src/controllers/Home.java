package controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class Home extends Application {

    /**
     * Navigates to the customers list page
     *
     * @param e button action event
     * @throws Exception
     */
    @FXML
    public void goToCustomersPage(ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/customers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates to the properties list page
     *
     * @param e button action event
     * @throws Exception
     */
    @FXML
    public void goToPropertiesList(ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/Properties.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Navigates to the invoice list page
     *
     * @param e button action event
     * @throws Exception
     */
    @FXML
    void goToInvoiceList(ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/invoices.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent source = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
        Scene scene = new Scene(source);

        stage.setTitle("Northants Realtors Property Management System");
        stage.setScene(scene);
        stage.show();
    }
}
