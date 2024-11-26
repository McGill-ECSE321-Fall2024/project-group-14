package ca.mcgill.ecse321_group14.GameShop.repository;
import java.sql.Date;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Order;

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
        Customer customer = new Customer("password", "email", "username", 123456, Date.valueOf("2015-12-07"), "address"); // create a new customer
        customer = customerRepository.save(customer); 
        Order order = new Order(LocalDate.now(), customer,false); // create a new order
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
