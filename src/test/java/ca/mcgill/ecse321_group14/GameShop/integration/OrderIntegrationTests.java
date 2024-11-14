package ca.mcgill.ecse321_group14.GameShop.integration;

import java.sql.Date;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import ca.mcgill.ecse321_group14.GameShop.dto.OrderRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.OrderResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderRepository;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class OrderIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;

    private int orderId;
    private int customerId;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;
    

    @BeforeAll
    public void setup(){
        Customer person = new Customer("password","email","username",123123,Date.valueOf("2015-12-07"), "address");
        person = customerRepository.save(person);
        customerId = person.getId();
        assertTrue(customerRepository.existsById(customerId));
        assertTrue(customerId>0,"Customer id is not valid");

        OrderRequestDto orderRequestDto = new OrderRequestDto(customerId);
        ResponseEntity<OrderResponseDto> response = client.postForEntity("/order", orderRequestDto, OrderResponseDto.class);
        OrderResponseDto orderResponseDto = response.getBody();
        orderId = orderResponseDto.getOrderId();

        assertTrue(orderRepository.existsById(orderId));
        assertTrue(orderId>0,"Order id is not valid");
    }

    @AfterAll
    public void clearDatabase() {
        orderRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    public void testCreateOrder() {
        // Arrange: Create the OrderRequestDto with a valid customerId
        OrderRequestDto orderRequestDto = new OrderRequestDto(customerId);
    
        // Act: Send POST request with the OrderRequestDto as JSON
        ResponseEntity<OrderResponseDto> response = client.postForEntity("/order", orderRequestDto, OrderResponseDto.class);
    
        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        OrderResponseDto orderResponseDto = response.getBody();
        assertNotNull(orderResponseDto);
        orderId = orderResponseDto.getOrderId();
        assertTrue(orderId > 0, "Order id should be positive");
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testGetOrderById(){
        // Act
        ResponseEntity<OrderResponseDto> response = client.getForEntity("/order/" + orderId, OrderResponseDto.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        OrderResponseDto orderResponseDto = response.getBody();
        assertNotNull(orderResponseDto);
        assertEquals(orderId, orderResponseDto.getOrderId());
        assertEquals(customerId, orderResponseDto.getCustomerId());

    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testDeleteOrder(){
        // Check that order exists before deletion
        ResponseEntity<OrderResponseDto> preDeleteResponse = client.getForEntity("/order/" + orderId, OrderResponseDto.class);
        assertEquals(HttpStatus.OK, preDeleteResponse.getStatusCode());

        // Act: Perform delete operation
        ResponseEntity<Void> response = client.exchange("/order/delete/" + orderId, org.springframework.http.HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify deletion
        ResponseEntity<OrderResponseDto> postDeleteResponse = client.getForEntity("/order/" + orderId, OrderResponseDto.class);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, postDeleteResponse.getStatusCode());
    }
        
}
