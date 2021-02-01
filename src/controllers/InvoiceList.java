package controllers;

import helpers.AlertBox;
import helpers.InvoiceFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Invoice;

import java.util.ArrayList;

public class InvoiceList extends Application {

    @FXML
    private TableView<Invoice> invoiceTable;

    @FXML
    private TableColumn<Invoice, String> invoiceId;

    @FXML
    private TableColumn<Invoice, Integer> propertyId;

    @FXML
    private TableColumn<Invoice, String> saleType;

    @FXML
    private TableColumn<Invoice, Double> price;

    @FXML
    private TableColumn<Invoice, Double> agentFee;

    @FXML
    private TableColumn<Invoice, Double> deposit;

    @FXML
    private TableColumn<Invoice, Double> totalPay;

    @FXML
    private TableColumn<Invoice, String> paymentDate;

    @FXML
    private TableColumn<Invoice, String> nextPayment;

    @FXML
    private TableColumn<Invoice, String> customerName;

    @FXML
    private TableColumn<Invoice, String> customerEmail;

    @FXML
    public void initialize() {
        invoiceId.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        propertyId.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        saleType.setCellValueFactory(new PropertyValueFactory<>("saleType"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        agentFee.setCellValueFactory(new PropertyValueFactory<>("agentFee"));
        deposit.setCellValueFactory(new PropertyValueFactory<>("deposit"));
        totalPay.setCellValueFactory(new PropertyValueFactory<>("totalPay"));
        paymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));
        nextPayment.setCellValueFactory(new PropertyValueFactory<>("nextPaymentDate"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerEmail.setCellValueFactory(new PropertyValueFactory<>("customerEmail"));

        // Populates table with invoices from database
        ArrayList<Invoice> invoices =
                new ArrayList<Invoice>((ArrayList<Invoice>) InvoiceFactory.getPayments());
        ObservableList<Invoice> list = FXCollections.observableList(invoices);
        invoiceTable.setItems(list);

        // Sets label text as placeholder if the table is empty
        invoiceTable.setPlaceholder(new Label("There are No Invoices left"));
    }

    /**
     * Navigates the user back to the home page
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
     * Deletes an payment information from the list
     * @param e button action event
     * @throws Exception
     */
    @FXML
    void deleteInvoice(ActionEvent e) throws Exception {
        ObservableList<Invoice> invoices;
        invoices = invoiceTable.getItems();
        //provides the selected invoice
        Invoice selectedInvoice = invoiceTable.getSelectionModel().getSelectedItem();

        // Delete only if an invoice is selected
        if(selectedInvoice == null) {
            // Alert user if no invoice is selected for deletion
            AlertBox.display("Selection Required",
                    "No invoice selected. Please Select an invoice to delete");
        }
        else {
            // deletes invoice from database
            InvoiceFactory.deletePayment(selectedInvoice.getInvoiceId());
            // deletes invoice from table view
            invoices.remove(selectedInvoice);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent source = FXMLLoader.load(getClass().getResource("/views/invoices.fxml"));
        Scene scene = new Scene(source);

        stage.setTitle("Invoice List");
        stage.setScene(scene);
        stage.show();
    }
}
