package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Wishlist;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.WishlistRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class WishlistServiceTests {
    @Mock
    WishlistRepository wishlistRepository;

    @Mock
    CustomerRepository customerRepository;

    @Mock
    GameRepository gameRepository;

    @InjectMocks
    WishlistService wishlistService;

    @SuppressWarnings("null")
    @Test
    public void testCreateWishlist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");

        when(wishlistRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        // act
        Wishlist createdWishlist = wishlistService.createWishlist(game, customer);

        // assert
        assertNotNull(createdWishlist);
        assertEquals(createdWishlist.getKey().getWish(), game);
        assertEquals(createdWishlist.getKey().getBuyer(), customer);
        verify(wishlistRepository, times(1)).save(any());
    }

    @Test
    public void testAreadyCreatedWishlist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");
        Wishlist wishlist = new Wishlist(new Wishlist.Key(game, customer));

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(wishlist);

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistService.createWishlist(game, customer);
        });
        assertEquals("Wishlist already exists!", exception.getMessage());
        verify(wishlistRepository, times(1)).findWishlistByKey(any());
        verify(wishlistRepository, times(0)).save(any());
    }

    @Test
    public void testAddGameToWishlist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");
        Wishlist wishlist = new Wishlist(new Wishlist.Key(game, customer));

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(wishlist);
        when(wishlistRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));
        when(customerRepository.existsById(any())).thenReturn(true);
        when(gameRepository.existsById(any())).thenReturn(true);

        // act
        Wishlist updatedWishlist = wishlistService.addGameToWishlist(game, customer);

        // assert
        assertNotNull(updatedWishlist);
        assertEquals(updatedWishlist.getKey().getWish(), game);
        assertEquals(updatedWishlist.getKey().getBuyer(), customer);
        verify(wishlistRepository, times(1)).findWishlistByKey(any());
        verify(wishlistRepository, times(1)).save(any());
    }

    @Test
    public void testAddGameToWishlistCustomerDoesNotExist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");

        when(customerRepository.existsById(any())).thenReturn(false);

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistService.addGameToWishlist(game, customer);
        });
        assertEquals("Customer does not exist!", exception.getMessage());
        verify(wishlistRepository, times(0)).save(any());
    }

    @Test
    public void testAddGameToWishlistWishlistDoesNotExist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(null);
        when(customerRepository.existsById(any())).thenReturn(true);
        when(gameRepository.existsById(any())).thenReturn(true);
        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistService.addGameToWishlist(game, customer);
        });
        assertEquals("Wishlist does not exist!", exception.getMessage());
        verify(wishlistRepository, times(0)).save(any());
    }

    @Test
    public void testAddGameToWishlistGameDoesNotExist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");
        Wishlist wishlist = new Wishlist(new Wishlist.Key(game, customer));

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(wishlist);
        when(customerRepository.existsById(any())).thenReturn(true);
        when(gameRepository.existsById(any())).thenReturn(false);

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistService.addGameToWishlist(game, customer);
        });
        assertEquals("Game does not exist!", exception.getMessage());
        verify(wishlistRepository, times(0)).save(any());
    }

    @Test
    public void testDeleteGameFromWishlist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");
        Wishlist wishlist = new Wishlist(new Wishlist.Key(game, customer));

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(wishlist);

        // act
        Wishlist deletedWishlist = wishlistService.deleteGameFromWishlist(game, customer);

        // assert
        assertNotNull(deletedWishlist);
        assertEquals(deletedWishlist.getKey().getWish(), game);
        assertEquals(deletedWishlist.getKey().getBuyer(), customer);
        verify(wishlistRepository, times(1)).findWishlistByKey(any());
        verify(wishlistRepository, times(1)).deleteById(any());
    }

    @Test
    public void testDeleteGameFromWishlistWishlistDoesNotExist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(null);

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistService.deleteGameFromWishlist(game, customer);
        });
        assertEquals("Wishlist does not exist!", exception.getMessage());
        verify(wishlistRepository, times(0)).deleteById(any());

    }
    @Test
    public void testClearWishlist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");
        Wishlist wishlist = new Wishlist(new Wishlist.Key(game, customer));

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(wishlist);

        // act
        wishlistService.clearWishlist(customer, game);

        // assert
        verify(wishlistRepository, times(1)).deleteAll();
    }
    @Test
    public void testClearWishlistWishlistDoesNotExist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(null);

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistService.clearWishlist(customer, game);
        });
        assertEquals("Wishlist does not exist!", exception.getMessage());
        verify(wishlistRepository, times(0)).deleteAll();
    }
    @Test
    public void testGetWishlist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");
        Wishlist wishlist = new Wishlist(new Wishlist.Key(game, customer));

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(wishlist);

        // act
        Wishlist foundWishlist = wishlistService.getWishlist(game, customer);

        // assert
        assertNotNull(foundWishlist);
        assertEquals(foundWishlist.getKey().getWish(), game);
        assertEquals(foundWishlist.getKey().getBuyer(), customer);
        verify(wishlistRepository, times(1)).findWishlistByKey(any());
    }
    @Test
    public void testGetWishlistWishlistDoesNotExist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(null);

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistService.getWishlist(game, customer);
        });
        assertEquals("Wishlist does not exist!", exception.getMessage());

    }
    @Test
    public void testDeleteGameFromWishlistGameDoesNotExist(){
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Game game2 = new Game("game2", "description2", "category2", 20, 20, Game.Rating.G, "picture2");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");
        Wishlist wishlist = new Wishlist(new Wishlist.Key(game, customer));

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(wishlist);

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistService.deleteGameFromWishlist(game2, customer);
        });
        assertEquals("Game is not in the wishlist!", exception.getMessage());
        verify(wishlistRepository, times(0)).deleteById(any());
    }
    @Test
    public void testAddNullGameToWishlist(){
        // arrange
        Game game = null;
        Game game2 = new Game("game2", "description2", "category2", 20, 20, Game.Rating.G, "picture2");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistService.addGameToWishlist(game, customer);
        });
        assertEquals("Game does not exist!", exception.getMessage());
        verify(wishlistRepository, times(0)).save(any());
    }
    @Test
    public void testAddNullCustomerToWishlist(){
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Game game2 = new Game("game2", "description2", "category2", 20, 20, Game.Rating.G, "picture2");
        Customer customer = null;

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistService.addGameToWishlist(game, customer);
        });
        assertEquals("Customer does not exist!", exception.getMessage());
        verify(wishlistRepository, times(0)).save(any());
    }
    @Test
    public void testDeleteGameFromWishlistCustomerNotOwner(){
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");
        Customer customer2 = new Customer("password2", "email2", "username2", 987654321, Date.valueOf("2021-01-01"), "address2");
        Wishlist wishlist = new Wishlist(new Wishlist.Key(game, customer));

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(wishlist);

        // act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            wishlistService.deleteGameFromWishlist(game, customer2);
        });
        assertEquals("Customer does not own the wishlist!", exception.getMessage());
        verify(wishlistRepository, times(0)).deleteById(any());
    }





}
