package helpers;

import models.Customer;
import models.Invoice;
import models.Property;

import java.io.File;
import java.util.ArrayList;


public class InvoiceFactory {

    private static final String paymentsDb = "invoices.txt";
    private static ArrayList<Invoice> invoices = new ArrayList(getPayments());

    /**
     * Returns an arraylist of invoices retrieved from db
     *
     * @return invoices from the database
     */
    public static ArrayList<?> getPayments() {
        // check if invoices text file exists
        File db =  new File(paymentsDb);
        return db.exists() ?  ReadWriteOperations.readFromFileDb(paymentsDb): new ArrayList<>();
    };

    /**
     * Creates a payment invoice
     *
     * @param customer customer that owns the invoice
     * @param property property to be sold
     * @return payment invoice
     */
    public static Invoice createInvoice(Customer customer, Property property) {
        return new Invoice(customer, property);
    }


    /**
     * Save payment invoice to database
     *
     * @param invoice payment invoice to be saved to db
     */
    public static void recordPayment(Invoice invoice){
        invoices.add(invoice);
        persist(invoices);
    };


    /**
     * Delete invoice from the database
     *
     * @param invoiceId id of invoice to be deleted
     */
    public static void deletePayment(int invoiceId){
        boolean deleted;
        deleted = invoices.removeIf(invoice -> invoice.getInvoiceId() == invoiceId);
        if (deleted){
            persist(invoices);
        }
    };

    /**
     * Saves updated invoices array list to db after any operation
     *
     * @param invoices array list of invoices
     */
    public static void persist(ArrayList<?> invoices) {
        ReadWriteOperations.saveToFileDb(paymentsDb, invoices);
    }

}
