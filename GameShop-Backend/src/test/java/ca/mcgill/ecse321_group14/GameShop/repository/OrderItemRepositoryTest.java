package ca.mcgill.ecse321_group14.GameShop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;

@SpringBootTest
public class OrderItemRepositoryTest {
    @Autowired
    private OrderitemRepository OrderitemRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        OrderitemRepository.deleteAll();
        gameRepository.deleteAll();
        orderRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadOrderItem() {
        // Arrange
        Game game = new Game("Mario", null, null, 0, 0, null, null); // create a new game
        game = gameRepository.save(game);

        Customer customer = new Customer("password", "email", "username", 123456, Date.valueOf("2015-12-07"), "address"); // create a new customer
        customer = customerRepository.save(customer);

        Order order = new Order(null, customer); // create a new order
        order = orderRepository.save(order);

        

        Orderitem.Key key = new Orderitem.Key(game, order); // create a new key
        Orderitem Orderitem = new Orderitem(key); // create a new Orderitem

        Orderitem = OrderitemRepository.save(Orderitem); // save the Orderitem

        // Act
        Orderitem readOrderitem = OrderitemRepository.findOrderitemByKey(key); // read the Orderitem

        // Assert
        assertNotNull(readOrderitem);
        assertNotNull(readOrderitem.getKey());
        assertNotNull(readOrderitem.getKey().getGame());
        assertNotNull(readOrderitem.getKey().getOrder());
        assertEquals(Orderitem.getKey().getGame().getId(), readOrderitem.getKey().getGame().getId());
        assertEquals(Orderitem.getKey().getOrder().getId(), readOrderitem.getKey().getOrder().getId());
        
    }
}
