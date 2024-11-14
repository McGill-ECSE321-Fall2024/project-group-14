package ca.mcgill.ecse321_group14.GameShop.integration;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

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

import ca.mcgill.ecse321_group14.GameShop.dto.OrderitemListDto;
import ca.mcgill.ecse321_group14.GameShop.dto.OrderitemResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderitemRepository;



@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class OrderitemIntegrationTest {
    
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired 
    private OrderitemRepository orderitemRepository;


    private int orderId;
    private int gameId;

    @BeforeAll
    public void setup(){
        Customer person = new Customer("password","email","username",123123,Date.valueOf("2015-12-07"), "address");
        person = customerRepository.save(person);
        int customerId = person.getId();
        assertTrue(customerRepository.existsById(customerId));
        assertTrue(customerId>0,"Customer id is not valid");

        Order order = new Order(LocalDate.now(), person);
        order = orderRepository.save(order);
        orderId = order.getId();
        assertTrue(orderRepository.existsById(orderId));
        assertTrue(orderId>0,"Order id is not valid");

        Game game = new Game("Test Game", "Test Description", "Category", 50, 10, Game.Rating.G, "Picture");
        game = gameRepository.save(game);
        gameId = game.getId();
        assertTrue(gameRepository.existsById(gameId));
        assertTrue(gameId>0,"Game id is not valid");
    }


    @AfterAll
    public void clearDatabase() {
        orderitemRepository.deleteAll();
        orderRepository.deleteAll();
        customerRepository.deleteAll();
        gameRepository.deleteAll();
    }



    @Test
    @org.junit.jupiter.api.Order(1)
    public void testCreateOrderitem(){
        //Act
        ResponseEntity<OrderitemResponseDto> response = client.exchange(
            "/orderitem/" + orderId + "/" + gameId,
            org.springframework.http.HttpMethod.PUT,
            null,
            OrderitemResponseDto.class
        );

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        OrderitemResponseDto createdOrderitem = response.getBody();
        assertNotNull(createdOrderitem);
        assertEquals(orderId, createdOrderitem.getOrderId());
        assertEquals(gameId, createdOrderitem.getOrderitemId());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    public void testGetOrderitem(){
        // Act
        ResponseEntity<OrderitemResponseDto> response = client.getForEntity("/orderitem/" + orderId + "/" + gameId, OrderitemResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        OrderitemResponseDto orderitem = response.getBody();
        assertNotNull(orderitem);
        assertEquals(orderId, orderitem.getOrderId());
        assertEquals(gameId, orderitem.getOrderitemId());

    }

    @Test
    @org.junit.jupiter.api.Order(3)
    public void testGetOrderitemsByOrderId(){
        //Act
        ResponseEntity<OrderitemListDto> resonse = client.getForEntity("/orderitems/" + orderId, OrderitemListDto.class);

        //Assert
        assertNotNull(resonse);
        assertEquals(HttpStatus.OK, resonse.getStatusCode());
        OrderitemListDto orderitems = resonse.getBody();
        assertNotNull(orderitems);
        
        List<OrderitemResponseDto> orderitemList = orderitems.getOrderitems();
        assertNotNull(orderitemList);
        assertTrue(orderitemList.size() > 0);
        assertEquals(orderId, orderitemList.get(0).getOrderId());
    }
}
