package models;

import java.io.Serializable;
import java.util.Random;

public class Property implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int propertyId;
    private Boolean available;
    private double price;
    private String saleType;
    private Address address;
    private String propertyType;
    private Boolean hasGarden;
    private int bedrooms;
    private int bathrooms;

    /**
     * Constructor
     *
     * @param houseNo Property Number
     * @param street  Street name
     * @param postCode Property Post code
     * @param propertyType Type of property e.g Bungalow, Flat, Terraced e.t.c
     * @param price Rent or Sale price of the property
     * @param hasGarden Does the property have a garden
     * @param bedrooms Number of bedrooms
     * @param bathrooms Number of bathrooms or showers
     * @param saleType Property sale type (For Rent or For Sale)
     */
    public Property(String houseNo, String street, String postCode, String propertyType, double price,
                    Boolean hasGarden, int bedrooms, int bathrooms, String saleType){
        propertyId = new Random().nextInt(10000);
        available = true;
        this.address = new Address(houseNo, street, postCode);
        this.propertyType = propertyType;
        this.hasGarden = hasGarden;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.price = price;
        this.saleType = saleType;
    }

    /**
     * Returns property Id
     *
     * @return property Id
     */
    public int getPropertyId() {
        return propertyId;
    }

    /**
     * Returns boolean value representing property's availability
     *
     * @return property availability
     */
    public Boolean getAvailable() {
        return available;
    }

    /**
     * Returns property sale type. A property can either be for sale or rent
     *
     * @return property's sale type
     */
    public String getSaleType() {
        return saleType;
    }

    /**
     * Updates a property's availability
     *
     * @param available new availability status
     */
    public void setAvailable(Boolean available) {
        this.available = available;
    }

    /**
     * Returns the price of the property
     *
     * @return price of property
     */
    public double getPrice() {
        return price;
    }

    /**
     * Updates the price of a property
     *
     * @param price new property price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Get property's address
     *
     * @return property's address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Returns type of property(Bungalow, flat etc.)
     *
     * @return property type
     */
    public String getPropertyType() {
        return propertyType;
    }

    /**
     * Updates the type of property
     *
     * @param type new property type
     */
    public void setPropertyType(String type) {
        this.propertyType = type;
    }

    /**
     * Returns boolean value specifying if the property has a garden
     *
     * @return if property has a garden
     */
    public Boolean getHasGarden() {
        return hasGarden;
    }

    /**
     * Updates if a property has a garden
     *
     * @param hasGarden new has garden status
     */
    public void setHasGarden(Boolean hasGarden) {
        this.hasGarden = hasGarden;
    }

    /**
     * Returns the number of bathrooms the property has
     *
     * @return number of bathrooms
     */
    public int getBathrooms() {
        return bathrooms;
    }

    /**
     * Updates the number bathrooms in a property
     *
     * @param bathrooms new number of bathrooms
     */
    public void setBathrooms(int bathrooms) {
        this.bathrooms = bathrooms;
    }

    /**
     * Returns the number of bedrooms the property has
     *
     * @return number of bedrooms
     */
    public int getBedrooms() {
        return bedrooms;
    }

    /**
     * Updates the number bedrooms in a property
     *
     * @param bedrooms new number of bedrooms
     */
    public void setBedrooms(int bedrooms) {
        this.bedrooms = bedrooms;
    }

    /**
     * Returns a string value representing property's availability.
     * Useful for table view
     *
     * @return string for property availability
     */
    public String getAvailableString(){
        if (this.getSaleType().equalsIgnoreCase("Sale")) {
            return available ? "Yes" : "No (Sold)";
        } else {
            return available ? "Yes" : "No";
        }
    }

    /**
     * Returns a string value representing if a property has a garden
     * Useful for table view
     *
     * @return string showing if property has a garden
     */
    public String getHasGardenString(){
        return this.hasGarden ? "Yes" : "No";
    }

    /**
     * Returns property's full address
     *
     * @return Full address (House no, street name and post code)
     */
    public String getFullAddress(){
        return this.address.getFullAddress();
    }

    @Override
    public String toString() {
        return Integer.toString(propertyId);
    }
}
