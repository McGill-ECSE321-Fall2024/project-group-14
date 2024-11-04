package ca.mcgill.ecse321_group14.GameShop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;

@SpringBootTest
public class CustomerRepositoryTest {
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadCustomer() {
        // Arrange
        Customer customer = new Customer("password", "email", "username", 123456, Date.valueOf("2015-12-07"), "address"); // create a new customer
        customer = customerRepository.save(customer); // save the customer to the database
        int id = customer.getId(); // get the id of the customer

        // Act
        Customer readCustomer = customerRepository.findCustomerById(id); // read the customer from the database

        // Assert
        assertNotNull(readCustomer);
        assertEquals(customer.getId(), readCustomer.getId());
        assertEquals(customer.getPassword(), readCustomer.getPassword());
        assertEquals(customer.getEmail(), readCustomer.getEmail());
        assertEquals(customer.getUsername(), readCustomer.getUsername());
        assertEquals(customer.getAddress(), readCustomer.getAddress());
        assertEquals(customer.getCardNumber(), readCustomer.getCardNumber());
        assertEquals(customer.getCardExpiryDate(), readCustomer.getCardExpiryDate());
        
    }
}
