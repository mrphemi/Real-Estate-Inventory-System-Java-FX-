package helpers;

import models.Customer;

import java.io.File;
import java.util.ArrayList;

public class CustomerFactory {

    private static final String customerDb = "customers.txt";
    private static ArrayList<Customer> customers = new ArrayList(getCustomers());

    /**
     * Returns an arraylist of customers retrieved from db
     *
     * @return customers from the database
     */
    public static ArrayList<?> getCustomers() {
       // check if customers text file exists
       File db =  new File(customerDb);
       return db.exists() ?  ReadWriteOperations.readFromFileDb(customerDb): new ArrayList<>();
    };

    /**
     * Creates an new customer and saves details to the db
     *
     * @param email customer email
     * @param fullName customer full name
     * @param phoneNumber customer phone number
     * @param houseNo customer current house number
     * @param street customer current street name
     * @param postCode customer current post code
     */
    public static void createNewCustomer(String email, String fullName, String  phoneNumber,
                                         String houseNo, String street, String postCode){
        Customer newCustomer = new Customer(email, fullName, phoneNumber, houseNo, street, postCode);
        customers.add(newCustomer);
        persist(customers);
    };


    /**
     * Deletes a customer from database
     *
     * @param customerId id of customer to be deleted
     */
    public static void deleteCustomer(int customerId){
        boolean deleted;
        deleted = customers.removeIf(customer -> customer.getCustomerId() == customerId);
        if (deleted){
            persist(customers);
        }
    };

    /**
     * Saves updated customers array list to db after any operation
     *
     * @param customers array list of customers
     */
    public static void persist(ArrayList<?> customers) {
        ReadWriteOperations.saveToFileDb(customerDb, customers);
    };

}
