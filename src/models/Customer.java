package models;

import java.io.Serializable;
import java.util.Random;

public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int customerId;
    private String email;
    private String fullName;
    private String phoneNumber;
    private Address currentAddress;

    /**
     * Constructor
     *
     * @param email  customer's email
     * @param fullName  customer's full name
     * @param phoneNumber customer's phone number
     * @param houseNo  customer's current house number
     * @param street  customer's current street name
     * @param postCode customer's current post code
     */
    public Customer(String email, String fullName, String phoneNumber, String houseNo, String street, String postCode){
        this.customerId = new Random().nextInt(10000);
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.fullName = fullName;
        this.currentAddress = new Address(houseNo, street, postCode);
        String fullAddress = getFullAddress();
    }

    /**
     * Returns customer's id
     *
     * @return customer's id
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Returns customer's full name
     *
     * @return customer's full name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Updates customer's full name
     *
     * @param newFullName customer's new full name
     */
    public void setFullName(String newFullName) {
        this.fullName = newFullName;
    }

    /**
     * Returns customer's email
     *
     * @return customer's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Updates customer's email
     *
     * @param newEmail  customer's new email address
     */
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    /**
     * Returns customer's phone number
     *
     * @return customer's phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Updates customer's phone number
     *
     * @param newMobile new mobile number
     */
    public void setPhoneNumber(String newMobile) {
        this.phoneNumber = newMobile;
    }

    /**
     * Returns customer's current address
     *
     * @return customer's current address
     */
    public Address getCurrentAddress() {
        return currentAddress;
    }

    /**
     * Updates customer's current address
     *
     * @param address  customer's new address
     */
    public void setCurrentAddress(Address address) {
        currentAddress = address;
    }

    /**
     * Returns customer's full address
     * Full address a combination of house no, street and post code in a sentence.
     *
     * @return customer's full address
     */
    public String getFullAddress(){
        return this.currentAddress.getFullAddress();
    }

    @Override
    public String toString() {
        return email + "   " + fullName;
    }
}
