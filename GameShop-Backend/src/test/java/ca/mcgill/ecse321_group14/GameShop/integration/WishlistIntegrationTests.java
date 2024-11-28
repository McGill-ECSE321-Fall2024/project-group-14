package ca.mcgill.ecse321_group14.GameShop.integration;

import ca.mcgill.ecse321_group14.GameShop.dto.WishlistDto;
import ca.mcgill.ecse321_group14.GameShop.dto.WishlistListDto;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Wishlist;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.WishlistRepository;
import ca.mcgill.ecse321_group14.GameShop.service.WishlistService;
import org.apache.coyote.Response;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class WishlistIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private CustomerRepository customerRepository;

    //game stuff
    private String name = "Mario";
    private String description = "A game about a plumber";
    private String category = "Platformer";
    private int price = 60;
    private int quantity = 100;
    private Game.Rating rating = Game.Rating.R;
    private String picture = "https://www.google.com";
    private int gameId;

    //customer stuff
    private String password = "password";
    private String email = "email";
    private String username = "username";
    private int cardNumber = 123456789;
    private Date expiryDate = Date.valueOf("2022-12-12");
    private String address = "1234 address";
    private int customerId;
    @Autowired
    private WishlistService wishlistService;

    @AfterAll
    public void clearDatabase() {
        wishlistRepository.deleteAll();
        gameRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateWishlist(){
        Game game = new Game(name, description, category, price, quantity, rating, picture);
        gameRepository.save(game);
        gameId = game.getId();
        Assertions.assertNotNull(game);

        Customer customer = new Customer(password, email, username, cardNumber, expiryDate, address);
        customerRepository.save(customer);
        customerId = customer.getId();
        Assertions.assertNotNull(customer);

        ResponseEntity<WishlistDto> response = client.postForEntity("/wishlist/" + gameId + "/" + customerId, null, WishlistDto.class);
        WishlistDto createdWishlist = response.getBody();
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(gameId, createdWishlist.getGameId());
        assertEquals(customerId, createdWishlist.getCustomerId());
        assertNotNull(createdWishlist);
    }


    @Test
    @Order(2)
    public void testCreateWishlistWishlistAlreadyExists() {
        // Arrange
        Game game = new Game(name, description, category, price, quantity, rating, picture);
        gameRepository.save(game);
        int gameId2 = game.getId();
        Assertions.assertNotNull(game);

        Customer customer = new Customer(password, email, username, cardNumber, expiryDate, address);
        customerRepository.save(customer);
        int customerId2 = customer.getId();
        Assertions.assertNotNull(customer);

        // Create the wishlist first
        Wishlist wishlist = new Wishlist(new Wishlist.Key(game, customer));
        wishlistRepository.save(wishlist);

        // Act
        ResponseEntity<WishlistDto> response2 = client.postForEntity("/wishlist/" + gameId2 + "/" + customerId2, null, WishlistDto.class);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
        assertNotNull(response2.getBody());
        wishlistRepository.delete(wishlist);
    }


    @Test
    @Order(3)
    public void testGetWishlist(){


        ResponseEntity<WishlistDto> response2 = client.getForEntity("/wishlist/" + gameId + "/" + customerId, WishlistDto.class);
        WishlistDto createdWishlist2 = response2.getBody();
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(gameId, createdWishlist2.getGameId());
        assertEquals(customerId, createdWishlist2.getCustomerId());
        assertNotNull(createdWishlist2);
    }

    @Test
    @Order(4)
    public void testGetWishlistWishlistDoesNotExist(){
        Game game = new Game(name, description, category, price, quantity, rating, picture);
        gameRepository.save(game);
        int gameId2 = game.getId();
        Assertions.assertNotNull(game);

        Customer customer = new Customer(password, email, username, cardNumber, expiryDate, address);
        customerRepository.save(customer);
        int customerId2 = customer.getId();
        Assertions.assertNotNull(customer);

        ResponseEntity<WishlistDto> response = client.getForEntity("/wishlist/" + gameId2 + "/" + customerId2, WishlistDto.class);
        WishlistDto createdWishlist = response.getBody();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(createdWishlist);
    }


    @Test
    @Order(5)
    public void testGetWishlistsByCustomerId(){
        ResponseEntity<WishlistListDto> response = client.getForEntity("/wishlist/" + customerId, WishlistListDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        WishlistListDto wishlists = response.getBody();
        assertNotNull(wishlists);

        List<WishlistDto> wishlistDtos = wishlists.getWishlists();
        assertNotNull(wishlistDtos);
        assertEquals(1, wishlistDtos.size());
        assertTrue(wishlistDtos.size() > 0);
        assertEquals(gameId, wishlistDtos.get(0).getGameId());
    }


    @Test
    @Order(6)
    public void testClearWishlist(){
        ResponseEntity<Void> response = client.exchange("/wishlist/" + gameId + "/" + customerId, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    @Order(7)
    public void testClearWishlistWishlistDoesNotExist(){
        Game game = new Game(name, description, category, price, quantity, rating, picture);
        gameRepository.save(game);
        int gameId2 = game.getId();
        Assertions.assertNotNull(game);

        Customer customer = new Customer(password, email, username, cardNumber, expiryDate, address);
        customerRepository.save(customer);
        int customerId2 = customer.getId();
        Assertions.assertNotNull(customer);

        ResponseEntity<Void> response = client.exchange("/wishlist/" + gameId2 + "/" + customerId2, HttpMethod.DELETE, null, Void.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }






}