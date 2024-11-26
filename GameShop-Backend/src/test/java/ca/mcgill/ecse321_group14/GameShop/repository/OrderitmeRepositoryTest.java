package ca.mcgill.ecse321_group14.GameShop.repository;

<<<<<<< HEAD:GameShop-Backend/src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
import static org.junit.jupiter.api.Assertions.assertNotNull;

=======
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;

>>>>>>> main:src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
<<<<<<< HEAD:GameShop-Backend/src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
import ca.mcgill.ecse321_group14.GameShop.model.OrderItems;
=======
import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;
>>>>>>> main:src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;

@SpringBootTest
<<<<<<< HEAD:GameShop-Backend/src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
public class OrderItemsRepositoryTest {
    @Autowired
    private OrderItemsRepository orderItemsRepository;
=======
public class OrderitmeRepositoryTest {
    @Autowired
    private OrderitemRepository OrderitemRepository;
>>>>>>> main:src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
<<<<<<< HEAD:GameShop-Backend/src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
        orderItemsRepository.deleteAll();
=======
        OrderitemRepository.deleteAll();
>>>>>>> main:src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
        gameRepository.deleteAll();
        orderRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
<<<<<<< HEAD:GameShop-Backend/src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
    public void testCreateAndReadOrderItems() {
=======
    public void testCreateAndReadOrderItem() {
>>>>>>> main:src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
        // Arrange
        Game game = new Game("Mario", null, null, 0, 0, null, null); // create a new game
        game = gameRepository.save(game);

<<<<<<< HEAD:GameShop-Backend/src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
        Customer customer = new Customer("password", "email", "username", 123456, null, "address"); // create a new customer
=======
        Customer customer = new Customer("password", "email", "username", 123456, Date.valueOf("2015-12-07"), "address"); // create a new customer
>>>>>>> main:src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
        customer = customerRepository.save(customer);

        Order order = new Order(null, customer); // create a new order
        order = orderRepository.save(order);

        

<<<<<<< HEAD:GameShop-Backend/src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
        OrderItems.Key key = new OrderItems.Key(game, order); // create a new key
        OrderItems orderItems = new OrderItems(key); // create a new orderItems

        orderItems = orderItemsRepository.save(orderItems); // save the orderItems

        // Act
        OrderItems readOrderItems = orderItemsRepository.findOrderItemsByKey(key); // read the orderItems

        // Assert
        assertNotNull(readOrderItems);
        assertNotNull(readOrderItems.getKey());
        assertNotNull(readOrderItems.getKey().getGame());
        assertNotNull(readOrderItems.getKey().getOrder());
=======
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
        
>>>>>>> main:src/test/java/ca/mcgill/ecse321_group14/GameShop/repository/OrderitmeRepositoryTest.java
    }
}
