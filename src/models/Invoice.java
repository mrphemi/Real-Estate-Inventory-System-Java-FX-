package models;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int invoiceId;
    private final Customer customer;
    private final Property property;
    private final LocalDateTime now;

    /**
     * Constructor
     *
     * @param customer customer that owns the invoice
     * @param property property for sale/rent
     */
    public Invoice(Customer customer, Property property){
        now = LocalDateTime.now();
        invoiceId = new Random().nextInt(10000);
        this.customer = customer;
        this.property = property;
    }

    /**
     * Returns invoice Id
     *
     * @return Invoice Id
     */
    public int getInvoiceId() {
        return invoiceId;
    }

    /**
     * Returns customer that owns the invoice
     *
     * @return Invoice customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Returns property that is to be sold to customer
     *
     * @return Invoice property
     */
    public Property getProperty() {
        return property;
    }

    /**
     * Returns payment date for sale/rent of property
     *
     * @return Invoice payment date
     */
    public String getPaymentDate() {
        return formatDate(now);
    }

    /**
     * Returns next payment date if the property is for rent
     * else it returns the string "None" indicating fee isn't applicable for sale
     *
     * @return Next payment date
     */
    public String getNextPaymentDate() {
        LocalDateTime date = this.property.getSaleType().equalsIgnoreCase("Rent") ? now.plusMonths(1):
                null;
        if (date == null) {
            return "None";
        }
        return formatDate(date);
    }

    /**
     * Returns agent fee for sale/rent of property
     *
     * @return Property agent fee
     */
    public double getAgentFee() {
        return this.property.getSaleType().equalsIgnoreCase("Rent") ?
                300 : this.property.getPrice() *  0.015;
    }

    /**
     * Returns deposit fee if the property is for rent
     * else returns 0 signifying that fee is not applicable for sale
     *
     * @return Property deposit fee
     */
    public double getDeposit() {
        return this.property.getSaleType().equalsIgnoreCase("Rent") ?
                this.property.getPrice() * 3 : 0;
    }

    /**
     * Returns total price a customer is required to pay for property.
     * This includes agent fees and deposit if applicable
     *
     *
     * @return Invoice total fee
     */
    public double getTotalPay() {
        return this.property.getPrice() + getAgentFee() + getDeposit();
    }

    public int getPropertyId() {
        return property.getPropertyId();
    }

    public double getPrice() {
        return property.getPrice();
    }

    public String getSaleType(){
        return property.getSaleType();
    }

    public String getCustomerName(){
        return customer.getFullName();
    }

    public String getCustomerEmail() {
        return customer.getEmail();
    }

    /**
     * Returns formatted date based on user provided format
     * like "dd/MM/yyyy" e.t.c
     *
     * @param date date that needs formatting
     * @return formatted date
     */
    String formatDate(LocalDateTime date){
        DateTimeFormatter format =  DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return date.format(format);
    }

    @Override
    public String toString() {
        return Integer.toString(getInvoiceId());
    }
}
