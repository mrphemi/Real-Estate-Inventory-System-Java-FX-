package helpers;

import models.Property;

import java.io.File;
import java.util.ArrayList;

public class PropertyFactory {

    private static final String propertyDb = "properties.txt";
    private static ArrayList<Property> properties = new ArrayList(getProperties());

    /**
     * Gets properties from the database
     *
     * @return An array list with various properties for sell or for rent
     */
    public static ArrayList<?> getProperties() {
        // check if properties text file exists
        File db =  new File(propertyDb);
        return db.exists() ?  ReadWriteOperations.readFromFileDb(propertyDb): new ArrayList<>();
    };

    //

    /**
     * Create new property and save to database
     *
     * @param houseNo Property Number
     * @param street  Street name
     * @param postCode Property Post code
     * @param propertyType Type of property e.g Bungalow, Flat, Terraced e.t.c
     * @param price Rent or Sale price of the property
     * @param hasGarden Does the property have a garden
     * @param beds Number of bedrooms
     * @param baths Number of bathrooms or showers
     * @param saleType Property sale type (For Rent or For Sale)
     */
    public static void createNewProperty(String houseNo, String street, String postCode,
                                         String propertyType, double price, boolean hasGarden,
                                         int beds, int baths, String saleType){
        Property newProperty = new Property(houseNo, street,  postCode, propertyType, price, hasGarden
                , beds, baths, saleType);
        properties.add(newProperty);
        persist(properties);
    };


    /**
     * Deletes property from database and persists db if property is deleted
     *
     * @param propertyId id of the property you want to delete
     */
    public static void deleteProperty(int propertyId){
        boolean deleted;
        deleted = properties.removeIf(property -> property.getPropertyId() == propertyId);
        if (deleted){
            persist(properties);
        }
    };


    /**
     * Set property availability
     *
     * @param propertyId id of the property you want to change its status
     * @param value boolean value representing property's status
     */
    public static void setPropertyAvailability(int propertyId, boolean value){
        for (Property property : properties) {
            // Search property database by property id
            if (property.getPropertyId() == propertyId) {
                property.setAvailable(value);
            }
        }
        persist(properties);
    }

    // filter by postCode
    static boolean filterByPostCode(Property property, String postcode){
        return property.getAddress().getPostCode().toLowerCase().contains(postcode.toLowerCase());
    }

    // filter by property type
    static boolean filterByPropertyType(Property property, String propertyType){
        return property.getPropertyType().toLowerCase().startsWith(propertyType.toLowerCase());
    }

    // filter by sale type
    static boolean filterBySaleType(Property property, String saleType){
        return property.getSaleType().toLowerCase().startsWith(saleType.toLowerCase());
    }

    // filter by No of rooms
    static boolean filterByNoOfBedrooms(Property property, int noOfBedrooms){
        return property.getBedrooms() >= noOfBedrooms;
    }

    // filter by No of baths
    static boolean filterByNoOfBathrooms(Property property, int noOfBathrooms){
        return property.getBathrooms() >= noOfBathrooms;
    }

    // filter by minimum price
    static boolean filterByMinPrice(Property property, int minPrice){
        return (int) property.getPrice() >= minPrice;
    }

    // filter by minimum price
    static boolean filterByMaxPrice(Property property, int maxPrice){
        return (int) property.getPrice() <= maxPrice;
    }

    // filter by availability
    static boolean filterByAvailability(Property property, boolean availability){
        return property.getAvailable() == availability;
    }

    /**
     * Filter properties using different criteria and return an array list
     * of the filtered results
     *
     * @param postcode property post code
     * @param propertyType property type
     * @param saleType  property sale type
     * @param noOfBedrooms number of bedrooms in property
     * @param noOfBathrooms number of bathrooms in property
     * @param minPrice minimum price of property
     * @param maxPrice maximum price of property
     *
     * @return array list of properties that meet search criteria
     */
    public static ArrayList<Property> filterProperties(String postcode, String propertyType,
                                                       String saleType,
                                                       int noOfBedrooms, int noOfBathrooms,
                                                       int minPrice, int maxPrice){
        ArrayList<Property> filtered = new ArrayList<>();
        for (Property property: properties) {
            boolean conditions = filterByPostCode(property, postcode) && filterByPropertyType(property
                    , propertyType) && filterBySaleType(property, saleType) && filterByNoOfBedrooms
             (property, noOfBedrooms) && filterByNoOfBathrooms(property, noOfBathrooms) &&
             filterByMinPrice(property, minPrice) && filterByMaxPrice(property, maxPrice);
            if(conditions) {
                filtered.add(property);
            }
        }
        return filtered;
    }

    /**
     * Saves updated properties array list to db after any operation
     *
     * @param properties array list of properties
     */
    public static void persist(ArrayList<?> properties) {
        ReadWriteOperations.saveToFileDb(propertyDb,properties);
    }
}
