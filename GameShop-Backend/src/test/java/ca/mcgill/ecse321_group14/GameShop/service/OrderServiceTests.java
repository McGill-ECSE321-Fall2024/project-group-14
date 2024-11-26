package ca.mcgill.ecse321_group14.GameShop.service;

import java.sql.Date;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.invocation.InvocationOnMock;
import org.springframework.boot.test.context.SpringBootTest;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderRepository;



@SpringBootTest
public class OrderServiceTests {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private OrderService orderService;


    @Test
    public void testCreateOrder() {
        // Arrange
        Customer person = new Customer("password", "customer@gmail.com", "username", 123456789, Date.valueOf("2015-12-07"), "address");
        
        // Mock the CustomerRepository to return the person when searching by ID
        when(customerRepository.findCustomerById(person.getId())).thenReturn(person);
        
        // Stub save to return the same order that was passed in
        when(orderRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        
        // Act
        Order order = orderService.createOrder(person.getId());
        
        // Assert
        assertNotNull(order);
        assertEquals(person.getId(), order.getCustomer().getId());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    public void testDeleteAllOrders() {
        // Act
        orderService.deleteAllOrders();
        
        // Assert
        verify(orderRepository, times(1)).deleteAll();
    }

    @Test
    public void testCreateOrderNonExistentCustomer() {
        //Arrange
        Customer person = new Customer("password", "customer@gmail.com", "username", 123456789, Date.valueOf("2015-12-07"), "address");

        //Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.createOrder(person.getId()));

        //Assert
        assertEquals("Customer does not exist!", exception.getMessage());
        verify(orderRepository, times(0)).save(any());  // Ensure no attempt to save an order was made
    }
    
    @Test
    public void testGetOrder() {
        // Arrange
        Customer person = new Customer("password", "customer@gmail.com", "username", 123456789, Date.valueOf("2015-12-07"), "address");
        Order order = new Order(LocalDate.now(), person,false);
    
        // Stub the repository to return the order when queried with the order's ID
        when(orderRepository.findOrderById(order.getId())).thenReturn(order);
    
        // Act
        Order returnedOrder = orderService.getOrder(order.getId());
    
        // Assert
        assertNotNull(returnedOrder);
        assertEquals(order, returnedOrder);
        
        // Verify the repository interaction
        verify(orderRepository, times(1)).findOrderById(order.getId());
    }

    @Test
    public void testGetOrderNonExistentOrder() {
        // Arrange
        int nonExistentOrderId = 999; 
        when(orderRepository.findOrderById(nonExistentOrderId)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> orderService.getOrder(nonExistentOrderId));
        assertEquals("Order does not exist!", exception.getMessage());

        // Verify the repository interaction
        verify(orderRepository, times(1)).findOrderById(nonExistentOrderId);
    }
}
