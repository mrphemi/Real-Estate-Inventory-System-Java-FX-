package models;

import java.io.Serializable;

public class Address implements Serializable {
    private String houseNumber;
    private String street;
    private String postCode;

    /**
     * Constructor
     *
     * @param houseNumber House Number
     * @param street   Street Name
     * @param postCode Area Post code
     */
    public Address(String houseNumber, String street, String postCode){
        this.houseNumber = houseNumber;
        this.street = street;
        this.postCode = postCode;
    }

    /**
     *
     * @return House Number
     */
    public String getHouseNumber() {
        return houseNumber;
    }

    /**
     * Updates house number
     * @param houseNumber New House Number
     */
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    /**
     *
     * @return Street Name
     */
    public String getStreet() {
        return street;
    }

    /**
     * Updates Street name
     * @param street New street name
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     *
     * @return Address Post code
     */
    public String getPostCode() {
        return postCode;
    }

    /**
     * Updates address post code
     * @param postCode New Post code
     */
    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    /**
     * Returns property's full address
     *
     * @return Full address (House no, street name and post code)
     */
    public String getFullAddress(){
        return houseNumber + ", " + street + ". " + postCode + ".";
    }

    @Override
    public String toString() {
        return getFullAddress();
    }
}
