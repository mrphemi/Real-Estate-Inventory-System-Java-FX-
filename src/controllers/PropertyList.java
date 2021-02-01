package controllers;

import helpers.AlertBox;
import helpers.PropertyFactory;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import java.util.ArrayList;

import models.Property;


public class PropertyList extends Application {

    @FXML private TableView<Property> propertyTable;

    @FXML private TableColumn<Property, String> propertyId;

    @FXML private TableColumn<Property, String> address;

    @FXML private TableColumn<Property, Double> price;

    @FXML private TableColumn<Property, String> available;

    @FXML private TableColumn<Property, String> propertyType;

    @FXML private TableColumn<Property, String> saleType;

    @FXML private TableColumn<Property, Integer> rooms;

    @FXML private TableColumn<Property, Integer> baths;

    @FXML private TableColumn<Property, Boolean> garden;

    @FXML private TextField filterByPostCode;

    @FXML private ComboBox<String> filterByPropertyType;

    @FXML private TextField filterByRooms;

    @FXML private TextField filterByBaths;

    @FXML
    private TextField filterByMinPrice;

    @FXML
    private TextField filterByMaxPrice;

    @FXML private ComboBox<String> filterBySaleType;

    @FXML
    public void initialize() {

        // Populate the property type and for sale filter combo boxes and with values
        filterByPropertyType.getItems().addAll("All","Bungalow","Detached", "Semi-detached", "Flat",
                "Terraced",
                "End of Terrace", "Cottage");
        filterBySaleType.getItems().addAll("All", "Rent", "Sale");

        // Populate table view with properties from database
        propertyId.setCellValueFactory(new PropertyValueFactory<>("propertyId"));
        address.setCellValueFactory(new PropertyValueFactory<>("fullAddress"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        available.setCellValueFactory(new PropertyValueFactory<>("availableString"));
        propertyType.setCellValueFactory(new PropertyValueFactory<>("propertyType"));
        saleType.setCellValueFactory(new PropertyValueFactory<>("saleType"));
        rooms.setCellValueFactory(new PropertyValueFactory<>("bedrooms"));
        baths.setCellValueFactory(new PropertyValueFactory<>("bathrooms"));
        garden.setCellValueFactory(new PropertyValueFactory<>("hasGardenString"));

        ArrayList<Property> properties = new ArrayList<Property>((ArrayList<Property>)PropertyFactory.getProperties());

        ObservableList<Property> list = FXCollections.observableList(properties);

        propertyTable.setItems(list);

        // Allow single cell selection on click. Disables multiple multiple cell selection.
        propertyTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        // Sets label text as placeholder if the table is empty
        propertyTable.setPlaceholder(new Label("There are No Properties left"));
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
     * Navigates to the add property page where user can add new property
     *
     * @param e button action event
     * @throws Exception
     */
    @FXML
    public void addProperty(ActionEvent e) throws Exception {
        Parent parent = FXMLLoader.load(getClass().getResource("/views/propertyForm.fxml"));
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    /**
     * Deletes a property from the properties list
     * and from the database
     */
    @FXML
    public void deleteProperty()
    {
        ObservableList<Property> properties;
        properties = propertyTable.getItems();
        //provides the selected property
        Property selectedProperty = propertyTable.getSelectionModel().getSelectedItem();

        // Delete only if a property is selected
        if(selectedProperty == null){
            // Alert user if no property is selected for deletion
            AlertBox.display("Selection Required",
                    "No property selected. Please Select a Property to delete");
        } else {
            // deletes property from database
            PropertyFactory.deleteProperty(selectedProperty.getPropertyId());
            // deletes property from table view
            properties.remove(selectedProperty);
        }
    }

    /**
     * Filters the property list based on parameters like post code, property type
     * or price range
     *
     */
    @FXML
    void filterList() {
        final int highestPrice = 1000000;
        // Get values from filter fields
        int noOfBedrooms = filterByRooms.getText().equals("") ? 0 : Integer.parseInt(filterByRooms.getText());
        int noOfBathrooms = filterByBaths.getText().equals("") ? 0 : Integer.parseInt(filterByBaths.getText());
        String propertyType =
                filterByPropertyType.getValue() == null || filterByPropertyType.getValue()
                .equalsIgnoreCase("All") ? "" : filterByPropertyType.getValue();
        String saleType =
                filterBySaleType.getValue() == null || filterBySaleType.getValue()
                        .equalsIgnoreCase("All") ? "" : filterBySaleType.getValue();

        int minPrice = filterByMinPrice.getText().equals("") ? 0 : Integer.parseInt(filterByMinPrice.getText());
        int maxPrice = filterByMaxPrice.getText().equals("") ? highestPrice :
                Integer.parseInt(filterByMaxPrice.getText());

        // Get array list based on filtered fields
        ArrayList <Property> filtered = PropertyFactory.filterProperties(filterByPostCode.getText(),
                propertyType, saleType,
                noOfBedrooms, noOfBathrooms, minPrice, maxPrice);
        ObservableList<Property> filteredList =
                FXCollections.observableList(filtered);

        //Display filtered results to the table view
        propertyTable.setItems(filteredList);
    }

    /**
     * Clear filter fields when the reset filters button is pressed
     * and returns all properties to the table
     */
    @FXML
    void clearFields(){
        filterByRooms.setText("");
        filterByBaths.setText("");
        filterByMaxPrice.setText("");
        filterByMinPrice.setText("");
        filterByPostCode.setText("");
        filterBySaleType.getSelectionModel().selectFirst();
        filterByPropertyType.getSelectionModel().selectFirst();
        filterByPropertyType.setPlaceholder(new Label("Property Type"));
        filterBySaleType.setPlaceholder(new Label("Sale Type"));
        filterList();
    }


    /**
     * Generates Payment Invoice and completes sale or rent of property
     *
     * @param e button action event
     * @throws Exception
     */
    @FXML
    void sellOrRentProperty(ActionEvent e) throws Exception {
        Property selectedProperty = propertyTable.getSelectionModel().getSelectedItem();

        // Do nothing if there is no property selected
        if (selectedProperty == null) {
            AlertBox.display("Selection Required", "Please Select a Property to sell");
            return;
        }

        // Display an error popup if selected property is not available for sell/rent
        if (!selectedProperty.getAvailable()) {
            AlertBox.display("Not Available", "Property is not available for sale/rent at the moment");
        } else {
            // Go to sell/rent property view if a property is selected
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/sellProperty.fxml"));
            Parent parent = loader.load();

            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

            // Access the sell/rent property view's controller
            SellProperty controller = loader.getController();
            // Populate the property preview grid pane with selected property details summary
            controller.populatePropertyPreview(selectedProperty);
            //Allow access to the selected property from the sell/rent view's controller
            stage.setUserData(selectedProperty);

            stage.setScene(scene);
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception  {
        Parent source = FXMLLoader.load(getClass().getResource("/views/Properties.fxml"));
        Scene scene = new Scene(source);
        stage.setTitle("Property List");
        stage.setScene(scene);
        stage.show();
    }
}

