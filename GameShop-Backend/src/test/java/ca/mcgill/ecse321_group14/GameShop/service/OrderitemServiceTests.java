package ca.mcgill.ecse321_group14.GameShop.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderitemRepository;

@ExtendWith(MockitoExtension.class)
public class OrderitemServiceTests {

    @Mock
    private OrderitemRepository orderitemRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderitemService orderitemService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateOrderitem() {
        // Arrange
        Customer person = new Customer("password", "customer@gmail.com", "username", 123456789, Date.valueOf("2015-12-07"), "address");
        Game game = new Game("aName", "aDescription", "aCategory", 10, 10, Game.Rating.G, "aPicture");
        Order order = new Order(LocalDate.now(), person,false);
        Orderitem.Key orderitemKey = new Orderitem.Key(game, order);
        
        when(orderitemRepository.findOrderitemByKey(orderitemKey)).thenReturn(null);
        when(orderitemRepository.save(any(Orderitem.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        // Act
        Orderitem createdOrderitem = orderitemService.createOrderitem(game, order);

        // Assert
        assertNotNull(createdOrderitem);
        assertEquals(game, createdOrderitem.getKey().getGame());
        assertEquals(order, createdOrderitem.getKey().getOrder());
        verify(orderitemRepository, times(1)).save(createdOrderitem);
    }

    @Test
    public void testCreateOrderitemAlreadyExists() {
        // Arrange
        Customer person = new Customer("password", "customer@gmail.com", "username", 123456789, Date.valueOf("2015-12-07"), "address");
        Game game = new Game("aName", "aDescription", "aCategory", 10, 10, Game.Rating.G, "aPicture");
        Order order = new Order(LocalDate.now(), person,false);
        Orderitem existingOrderitem = new Orderitem(new Orderitem.Key(game, order));
        
        when(orderitemRepository.findOrderitemByKey(new Orderitem.Key(game, order))).thenReturn(existingOrderitem);

        // Act
        Orderitem orderitem = orderitemService.createOrderitem(game, order);

        // Assert
        assertNotNull(orderitem);
        assertEquals(existingOrderitem, orderitem);
        verify(orderitemRepository, times(0)).save(orderitem); // Ensure save is not called
    }

    @Test
    public void testDeleteOrderitem() {
        // Arrange
        Customer person = new Customer("password", "customer@gmail.com", "username", 123456789, Date.valueOf("2015-12-07"), "address");
        Game game = new Game("aName", "aDescription", "aCategory", 10, 10, Game.Rating.G, "aPicture");
        Order order = new Order(LocalDate.now(), person,false);
        Orderitem orderitem = new Orderitem(new Orderitem.Key(game, order));
        
        when(orderitemRepository.findOrderitemByKey(new Orderitem.Key(game, order))).thenReturn(orderitem);

        // Act
        orderitemService.deleteOrderitem(game, order);

        // Assert
        verify(orderitemRepository, times(1)).delete(orderitem);
    }

    @Test
    public void testGetOrderitem() {
        // Arrange
        Customer person = new Customer("password", "customer@gmail.com", "username", 123456789, Date.valueOf("2015-12-07"), "address");
        Game game = new Game("aName", "aDescription", "aCategory", 10, 10, Game.Rating.G, "aPicture");
        Order order = new Order(LocalDate.now(), person,false);
        Orderitem orderitem = new Orderitem(new Orderitem.Key(game, order));
        
        when(orderitemRepository.findOrderitemByKey(new Orderitem.Key(game, order))).thenReturn(orderitem);

        // Act
        Orderitem retrievedOrderitem = orderitemService.getOrderitem(game, order);

        // Assert
        assertNotNull(retrievedOrderitem);
        assertEquals(orderitem, retrievedOrderitem);
    }

    @Test
    public void testFindOrderItemsByOrderId() {
        // Arrange
        int orderId = 1;
        Customer person = new Customer("password", "customer@gmail.com", "username", 123456789, Date.valueOf("2015-12-07"), "address");
        Order order = new Order(LocalDate.now(), person,false);
        Game game = new Game("aName", "aDescription", "aCategory", 10, 10, Game.Rating.G, "aPicture");
        Orderitem orderitem = new Orderitem(new Orderitem.Key(game, order));
        
        when(orderRepository.findOrderById(orderId)).thenReturn(order);
        when(orderitemRepository.findByKey_Order(order)).thenReturn(Collections.singletonList(orderitem));

        // Act
        List<Orderitem> orderitems = orderitemService.findOrderItemsByOrderId(orderId);

        // Assert
        assertNotNull(orderitems);
        assertEquals(1, orderitems.size());
        assertEquals(orderitem, orderitems.get(0));
    }
}
