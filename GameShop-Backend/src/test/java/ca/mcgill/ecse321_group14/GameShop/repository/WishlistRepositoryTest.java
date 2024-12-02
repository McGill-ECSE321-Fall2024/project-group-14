package ca.mcgill.ecse321_group14.GameShop.repository;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.sql.Date;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Wishlist;
import ca.mcgill.ecse321_group14.GameShop.model.Game;

@SpringBootTest
public class WishlistRepositoryTest {
    @Autowired
    private WishlistRepository wishlistRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        wishlistRepository.deleteAll();
        customerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadWishlist() {
        // Arrange
        Customer customer = new Customer("password", "email", "username", 123456, Date.valueOf("2015-12-07"), "address"); // create a new customer
        customer = customerRepository.save(customer);

        Game game = new Game("Mario", null, null, 0, 0, null, null); // create a new game
        game = gameRepository.save(game);

        Wishlist.Key key = new Wishlist.Key(game,customer); // create a new key
        Wishlist wishlist = new Wishlist(key); // create a new wishlist

        wishlist = wishlistRepository.save(wishlist); 

        // Act
        Wishlist readWishlist = wishlistRepository.findWishlistByKey(key); // read the wishlist

        // Assert
        assertNotNull(readWishlist);
        assertNotNull(readWishlist.getKey());
        assertNotNull(readWishlist.getKey().getBuyer());
        assertNotNull(readWishlist.getKey().getWish());
        assertTrue(readWishlist.getKey().getBuyer().equals(customer));
        assertTrue(readWishlist.getKey().getWish().equals(game));

    }
}
