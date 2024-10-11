package ca.mcgill.ecse321_group14.GameShop.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321_group14.GameShop.model.OrderItems;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;

@SpringBootTest
public class OrderItemsRepositoryTest {
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        orderItemsRepository.deleteAll();
        gameRepository.deleteAll();
        orderRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadOrderItems() {
        // Arrange
        Game game = new Game("Mario", null, null, 0, 0, null, null);
        game = gameRepository.save(game);

        Customer customer = new Customer("password", "email", "username", 123456, null, "address");
        customer = customerRepository.save(customer);

        Order order = new Order(null, customer);
        order = orderRepository.save(order);

        

        OrderItems.Key key = new OrderItems.Key(game, order);
        OrderItems orderItems = new OrderItems(key);

        orderItems = orderItemsRepository.save(orderItems);

        // Act
        OrderItems readOrderItems = orderItemsRepository.findOrderItemsByKey(key);

        // Assert
        assertNotNull(readOrderItems);
        assertNotNull(readOrderItems.getKey());
        assertNotNull(readOrderItems.getKey().getGame());
        assertNotNull(readOrderItems.getKey().getOrder());
    }
}
