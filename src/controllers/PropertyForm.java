package controllers;

import helpers.AlertBox;
import helpers.PropertyFactory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class PropertyForm extends Application {

    @FXML
    private TextField houseNumber;

    @FXML
    private TextField street;

    @FXML
    private TextField postCode;

    @FXML
    private TextField price;

    @FXML
    private ToggleGroup saleType;

    @FXML
    private ComboBox<String> propertyType;

    @FXML
    private TextField beds;

    @FXML
    private TextField baths;

    @FXML
    private ToggleGroup hasGarden;

    @FXML
    private Label FormErrorText;

    @FXML
    public void initialize() {
        // Fill property type drop down with data
        // Make first item on the list the default value
        propertyType.getItems().addAll("Bungalow","Detached", "Semi-detached", "Flat", "Terraced",
                "End of Terrace", "Cottage");
        propertyType.getSelectionModel().selectFirst();
    }

    /**
     * Create new property
     *
     * @param e button action event
     */
    @FXML
    void createProperty(ActionEvent e) {
        // Check if all fields have been completed
        if(checkAllFields()) {
            // Convert hasGarden string to boolean equivalent
            boolean hasGardenBool = ((RadioButton)hasGarden.getSelectedToggle()).getText().equalsIgnoreCase("yes");

            // Create new property and save to db
            PropertyFactory.createNewProperty(houseNumber.getText(), street.getText(), postCode.getText(),
                    propertyType.getValue(), Double.parseDouble(price.getText()), hasGardenBool,
                    Integer.parseInt(beds.getText()), Integer.parseInt(baths.getText()),
                    ((RadioButton)saleType.getSelectedToggle()).getText());

            // Display Success Alert box
            AlertBox.display("Success", "Property Successfully created");
            resetFormFields();
        } else {
            // Display error message telling user to fill all required fields
            // Required fields contains the "*" sign in front of label
            FormErrorText.setText("Please fill out all fields with '*' to continue");
        }
    }

    /**
     * Navigate to property list
     *
     * @param e button action event
     */
    @FXML
    void goToPropertyList(ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/Properties.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Check if a single field is empty
     *
     * @param fieldValue string to be checked
     * @return boolean representing if string is empty
     */
    boolean isFieldEmpty(String fieldValue){
        return fieldValue.isEmpty();
    }

    /**
     * Check if form fields have been filled out
     *
     * @return boolean the represents if all form fields is completed
     */
    boolean checkAllFields() {
        boolean completed;
        completed = !isFieldEmpty(houseNumber.getText()) && !isFieldEmpty(street.getText())
                && !isFieldEmpty(postCode.getText()) && !isFieldEmpty(price.getText()) &&
                !isFieldEmpty(propertyType.getValue()) && !isFieldEmpty(((RadioButton) saleType.getSelectedToggle()).getText())
                && !isFieldEmpty(beds.getText()) && !isFieldEmpty(baths.getText())
                && !isFieldEmpty(((RadioButton) hasGarden.getSelectedToggle()).getText());
        return completed;
    }

    /**
     * Reset form fields
     */
    void resetFormFields() {
        houseNumber.setText("");
        postCode.setText("");
        street.setText("");
        price.setText("");
        propertyType.getSelectionModel().selectFirst();
        saleType.selectToggle(null);
        hasGarden.selectToggle(null);
        beds.setText("");
        baths.setText("");
        FormErrorText.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        Parent source = FXMLLoader.load(getClass().getResource("/views/propertyForm.fxml"));
        Scene scene = new Scene(source);
        window.setTitle("Create New Property");
        window.setScene(scene);
        window.show();
    }
}
