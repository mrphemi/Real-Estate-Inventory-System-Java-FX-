package controllers;

import helpers.AlertBox;
import helpers.CustomerFactory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class CustomerForm extends Application {

    @FXML
    private TextField email;

    @FXML
    private TextField fullName;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField houseNumber;

    @FXML
    private TextField street;

    @FXML
    private TextField postCode;

    @FXML
    private Label FormErrorText;

    /**
     * Add new customer and save to database
     *
     * @param e button action event
     */
    @FXML
    void addCustomer(ActionEvent e) {
        if(checkAllFields()) {
            // Create new customer and save to db
            CustomerFactory.createNewCustomer(email.getText(), fullName.getText(),
                    phoneNumber.getText(), houseNumber.getText(), street.getText(), postCode.getText());
            // Display Success Alert box
            AlertBox.display("Success", "Customer Added Successfully");
            resetFormFields();
        } else {
            // Display error message telling user to fill all required fields
            // Required fields contains the "*" sign in front of label
            FormErrorText.setText("Please fill out all fields with '*' to continue");
        }
    }

    /**
     * Navigates to the customers list page
     *
     * @param e button action event
     * @throws Exception
     */
    @FXML
    void goToCustomerList(ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/customers.fxml"));
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
                && !isFieldEmpty(postCode.getText()) && !isFieldEmpty(email.getText())
                && !isFieldEmpty(phoneNumber.getText()) && !isFieldEmpty(fullName.getText());
        return completed;
    }
    
    /**
     * Reset form fields
     */
    void resetFormFields() {
        email.setText("");
        phoneNumber.setText("");
        fullName.setText("");
        houseNumber.setText("");
        postCode.setText("");
        street.setText("");
        FormErrorText.setText("");
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        Parent source = FXMLLoader.load(getClass().getResource("/views/customerForm.fxml"));
        Scene scene = new Scene(source);
        window.setTitle("Add New Customer");
        window.setScene(scene);
        window.show();
    }
}
