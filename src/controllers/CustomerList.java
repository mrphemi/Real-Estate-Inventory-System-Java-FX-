package controllers;

import helpers.AlertBox;
import helpers.CustomerFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import models.Customer;

import java.util.ArrayList;

public class CustomerList extends Application {

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, String> customerId;

    @FXML
    private TableColumn<Customer, String> currentAddress;

    @FXML
    private TableColumn<Customer, String> email;

    @FXML
    private TableColumn<Customer, String> fullName;

    @FXML
    private TableColumn<Customer, String> phoneNumber;

    @FXML
    public void initialize() {
        // Populate table view with customers from database
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        fullName.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        currentAddress.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));

        ArrayList<Customer> customers =
                new ArrayList<Customer>((ArrayList<Customer>) CustomerFactory.getCustomers());

        ObservableList<Customer> list = FXCollections.observableList(customers);

        customersTable.setItems(list);

        // Allow single cell selection on click. Disables multiple multiple cell selection.
        customersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // Sets label text as placeholder if the table is empty
        customersTable.setPlaceholder(new Label("There are No Customers left"));
    }

    /**
     * Navigate to add customer form
     *
     * @param e button action event
     * @throws Exception
     */
    @FXML
    void addCustomer(ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/customerForm.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Navigate to home page
     *
     * @param e button action event
     * @throws Exception
     */
    @FXML
    void backHome(ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/Home.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Delete customer
     *
     * @param e button action event
     * @throws Exception
     */
    @FXML
    void deleteCustomer(ActionEvent e) throws Exception {
        ObservableList<Customer> customers;
        customers = customersTable.getItems();
        //Provides the selected customer
        Customer selectedCustomer = customersTable.getSelectionModel().getSelectedItem();

        // Delete only if a customer is selected
        if(selectedCustomer == null){
            // Alert user if no customer is selected for deletion
            AlertBox.display("Selection Required",
                    "No customer selected. Please Select a customer to delete");
        } else {
            // Deletes customer from database
            CustomerFactory.deleteCustomer(selectedCustomer.getCustomerId());
            // Deletes customer from table view
            customers.remove(selectedCustomer);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        Parent source = FXMLLoader.load(getClass().getResource("/views/customers.fxml"));
        Scene scene = new Scene(source);
        window.setTitle("Customer List");
        window.setScene(scene);
        window.show();
    }
}
