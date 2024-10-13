package ca.mcgill.ecse321_group14.GameShop.repository;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        reviewRepository.deleteAll();
        customerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadReview() {
        // create customer
        Customer customer = new Customer("password", "email", "username", 123456, null, "address");
        customer = customerRepository.save(customer);

        //create game
        Game game = new Game("Mario", null, null, 0, 0, null, null);
        game = gameRepository.save(game);

        //create review on the game by customer
        Review review = new Review(Review.Ranking.OneStar,"love this game", customer, game);
        review = reviewRepository.save(review);

        int id = review.getId();

        //Act
        Review readReview = reviewRepository.findReviewById(id);

        assertNotNull(review);
        assertEquals(review.getId(), readReview.getId());
        assertEquals(review.getCustomer().getId(), readReview.getCustomer().getId());
        assertEquals(review.getGame().getId(), readReview.getGame().getId());
        assertEquals(review.getRanking(), readReview.getRanking());
        assertEquals(review.getDescription(), readReview.getDescription());
        
    }
}


