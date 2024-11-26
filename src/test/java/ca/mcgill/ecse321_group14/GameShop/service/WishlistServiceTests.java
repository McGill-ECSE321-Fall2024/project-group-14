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
import java.util.Collections;
import java.util.List;

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
    public void testAlreadyCreatedWishlist() {
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
    public void testClearWishlist() {
        // arrange
        Game game = new Game("game", "description", "category", 10, 10, Game.Rating.R, "picture");
        Customer customer = new Customer("password", "email", "username", 123456789, Date.valueOf("2021-01-01"), "address");
        Wishlist wishlist = new Wishlist(new Wishlist.Key(game, customer));

        when(wishlistRepository.findWishlistByKey(any())).thenReturn(wishlist);

        // act
        wishlistService.clearWishlist(customer, game);

        // assert
        verify(wishlistRepository, times(1)).delete(any());
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
    public void testFindWishlistsByCustomerId() {
        // Arrange
        int customerId = 1;
        Customer customer = new Customer("password", "customer@gmail.com", "username", 123456789, Date.valueOf("2015-12-07"), "address");
        Game game = new Game("aName", "aDescription", "aCategory", 10, 10, Game.Rating.G, "aPicture");
        Wishlist wishlist = new Wishlist(new Wishlist.Key(game, customer));

        when(customerRepository.findById(customerId)).thenReturn(java.util.Optional.of(customer));
        when(wishlistRepository.findByKey_Buyer(customer)).thenReturn(Collections.singletonList(wishlist));

        // Act
        List<Wishlist> wishlists = wishlistService.findWishlistsByCustomerId(customerId);

        // Assert
        assertNotNull(wishlists);
        assertEquals(1, wishlists.size());
        assertEquals(wishlist, wishlists.get(0));
    }








}
