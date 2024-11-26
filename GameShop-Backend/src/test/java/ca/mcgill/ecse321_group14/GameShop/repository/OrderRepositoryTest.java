package ca.mcgill.ecse321_group14.GameShop.repository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;

import java.sql.Date;


@SpringBootTest
public class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        orderRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadOrder() {
        // Arrange
        Customer customer = new Customer("password", "email", "username", 123456, null, "address"); // create a new customer
        customer = customerRepository.save(customer); 
        Order order = new Order(Date.valueOf("2024-10-12"), customer); // create a new order
        order = orderRepository.save(order); // save the order
        int id = order.getId(); // get the id of the order

        // Act
        Order readOrder = orderRepository.findOrderById(id); // read the order

        // Assert
        assertNotNull(readOrder);
        assertEquals(order.getId(), readOrder.getId());
        assertEquals(order.getCustomer(), readOrder.getCustomer());

    }
}
