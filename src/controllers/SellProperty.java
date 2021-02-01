package controllers;

import helpers.AlertBox;
import helpers.CustomerFactory;
import helpers.InvoiceFactory;
import helpers.PropertyFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import models.Customer;
import models.Invoice;
import models.Property;

import java.util.ArrayList;


public class SellProperty extends Application {

    @FXML
    private TextArea invoiceView;

    @FXML
    private Button completeSaleOrLease;

    @FXML
    private Label propertyId;

    @FXML
    private Label propertyAddress;

    @FXML
    private Label propertyPrice;

    @FXML
    private ComboBox<Customer> selectCustomer;

    private Invoice invoice;

    @FXML
    public void initialize() {
        ObservableList<Customer> customers =
                FXCollections.observableList((ArrayList<Customer>)CustomerFactory.getCustomers());
        selectCustomer.setItems(customers);
    }

    /**
     * Generate sale or rent payment invoice
     *
     * @param e
     */
    @FXML
    void generateInvoice(ActionEvent e) {

        String generatedText = "";

        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        // Get property to be sold(passed from the property list page)
        Property property = (Property) window.getUserData();
        // Get selected customer
        Customer customer = selectCustomer.getSelectionModel().getSelectedItem();

        if (customer == null) {
            AlertBox.display("Action Required", "Please select a customer");
        } else {
            invoice = InvoiceFactory.createInvoice(customer, property);

            String deposit = invoice.getDeposit() > 0.0 ? String.valueOf(invoice.getDeposit()) : "None";
            generatedText =
                    "Invoice Id:  " + invoice.getInvoiceId() + "\r\n" +
                    "\r\n" +
                    "Property Price:  " + invoice.getProperty().getPrice() + "\r\n" +
                    "Sale Type:  " + invoice.getProperty().getSaleType() + "\r\n" +
                    "Agent Fee:  " + invoice.getAgentFee() + "\r\n" +
                    "Deposit:  " + deposit + "\r\n" +
                    "Total Price to be Paid:  " + invoice.getTotalPay() + "\r\n" +
                    "Payment Date:  " + invoice.getPaymentDate() + "\r\n" +
                    "Next Payment Date:  " +  invoice.getNextPaymentDate() + "\r\n" +
                    "\r\n"  +
                    "Property Address:  " + invoice.getProperty().getFullAddress() + "\r\n" +
                    "Property Type:  " + invoice.getProperty().getPropertyType() + "\r\n" +
                    "No of Rooms:  " + invoice.getProperty().getBedrooms() + "\r\n" +
                    "No of Baths:  " + invoice.getProperty().getBathrooms() + "\r\n" +
                    "Garden:  " + invoice.getProperty().getHasGardenString() + "\r\n" +
                    "\r\n" +
                    "Customer Name:  " + invoice.getCustomer().getFullName() + "\r\n" +
                    "Customer Email:  " + invoice.getCustomer().getEmail() + "\r\n" +
                    "Customer Phone Number:  " + invoice.getCustomer().getPhoneNumber() + "\r\n";
        }

        invoiceView.setText(generatedText);
    }

    /**
     * Complete sale or rent of a property
     *
     * @param e button action event
     * @throws Exception
     */

    @FXML
    void completeSaleOrRent(ActionEvent e) throws Exception {
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        // Get property to be sold(passed from the property list page)
        Property property = (Property) stage.getUserData();
        if (invoice != null) {
            // Set property availability to false or no
            PropertyFactory.setPropertyAvailability(property.getPropertyId(), false);
            // Save invoice to database
            InvoiceFactory.recordPayment(invoice);
            // Show Successful sale popup
            AlertBox.display("Success", "Property Sold Successfully");
            // Go back to property listings
            goToPropertyListings(e);
        } else {
            // Alert user to generate invoice before proceeding with sale completion
            AlertBox.display("Action Required", "Please Generate Invoice to Continue");
        }
    }

    /**
     * Navigate to property listings when you complete a sale
     *
     * @param e button action event
     * @throws Exception
     */
    void goToPropertyListings(ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/Properties.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }


    /**
     * Populate property preview grid pane\
     * @param property the property that it's details are to be populated
     */
    public void populatePropertyPreview(Property property) {
        propertyId.setText(Integer.toString(property.getPropertyId()));
        propertyAddress.setText(property.getFullAddress());
        propertyPrice.setText(String.valueOf(property.getPrice()));
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception{
        Parent source = FXMLLoader.load(getClass().getResource("/views/sellProperty.fxml"));
        Scene scene = new Scene(source);
        stage.setTitle("Sell/Lease Property");
        stage.setScene(scene);
        stage.show();
    }
}
