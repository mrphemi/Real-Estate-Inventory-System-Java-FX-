package test;

import models.Customer;
import models.Invoice;
import models.Property;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class InvoiceTest {
    @Test
    @DisplayName("Should return property sale/rent agent fee")
    public void getAgentFee() {
        Customer customer = new Customer("alex@mail.com", "Alex Cross", "0799883875",
                "69", "Crow Lane", "NN3 3EU");
        Property property = new Property("29", "Ashley Way", "NN3 3ER", "Bungalow",
                75600.00, false, 3, 2, "Sale");
        Invoice invoice = new Invoice(customer, property);

        assertEquals(1134.0, invoice.getAgentFee());
    }


    @Test
    @DisplayName("Should return property sale/rent total payment fee")
    public void getTotalPay() {
        Customer customer = new Customer("alex@mail.com", "Alex Cross", "0799883875",
                "69", "Crow Lane", "NN3 3EU");
        Property property = new Property("29", "Ashley Way", "NN3 3ER", "Bungalow",
                75600.00, false, 3, 2, "Sale");
        Invoice invoice = new Invoice(customer, property);

        assertEquals(76700.0, invoice.getTotalPay());
    }
}

