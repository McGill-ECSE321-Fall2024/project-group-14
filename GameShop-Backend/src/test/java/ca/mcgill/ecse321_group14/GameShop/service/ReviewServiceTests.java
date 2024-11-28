package ca.mcgill.ecse321_group14.GameShop.service;

import java.sql.Date;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321_group14.GameShop.service.ReviewService;
import ca.mcgill.ecse321_group14.GameShop.service.CustomerService;
import ca.mcgill.ecse321_group14.GameShop.service.GameService;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.ReviewRepository;
import ca.mcgill.ecse321_group14.GameShop.model.Review.Ranking;




@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ReviewServiceTests {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private ReviewService reviewService;

    @InjectMocks
    private CustomerService customerService;

    @InjectMocks
    private GameService gameService;

    private static final int VALID_CUSTOMER_ID = 1;
    private static final int VALID_GAME_ID = 1;
    private static final int VALID_REVIEW_ID = 1;
    private static final String VALID_DESCRIPTION = "Great game!";
    private static final Review.Ranking VALID_RANKING = Review.Ranking.FiveStar;

    @Test
    public void testCreateValidReview() {
        Customer customer = new Customer();
        Game game = new Game();
        when(customerRepository.findCustomerById(VALID_CUSTOMER_ID)).thenReturn(customer);
        when(gameRepository.findGameById(VALID_GAME_ID)).thenReturn(game);
        when(reviewRepository.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));
        

        Review createdReview = reviewService.createReview(VALID_RANKING, VALID_DESCRIPTION, VALID_CUSTOMER_ID, VALID_GAME_ID);
        
        
        assertNotNull(createdReview);
        assertEquals(VALID_RANKING, createdReview.getRanking());
        assertEquals(VALID_DESCRIPTION, createdReview.getDescription());
        assertEquals(game, createdReview.getGame());
        verify(reviewRepository, times(1)).save(createdReview);
    }

    @Test
    public void testGetReviewByValidId() {
        Review review = new Review();
        when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(review);

        Review foundReview = reviewService.getReview(VALID_REVIEW_ID);

        assertNotNull(foundReview);
        assertEquals(review, foundReview);
    }

    @Test
    public void testGetReviewByInvalidId() {
        when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> reviewService.getReview(VALID_REVIEW_ID));
        assertEquals("Review does not exist!", ex.getMessage());
    }

    @Test
    public void testDeleteReview() {
        when(reviewRepository.existsById(VALID_REVIEW_ID)).thenReturn(true);

        reviewService.deleteReview(VALID_REVIEW_ID);

        verify(reviewRepository, times(1)).deleteById(VALID_REVIEW_ID);
    }

    @Test
    public void testUpdateReview() {
    // Arrange
    Customer customer = new Customer();
    Game game = new Game();
    when(customerRepository.findCustomerById(VALID_CUSTOMER_ID)).thenReturn(customer);
    when(gameRepository.findGameById(VALID_GAME_ID)).thenReturn(game);

    // Create a new review
    Review createdReview = new Review(VALID_RANKING, VALID_DESCRIPTION, customer, game);
    int id = createdReview.getId(); // assuming the ID is auto-generated in this mock scenario

    // Stub repository to return the createdReview when findReviewById is called with its ID
    when(reviewRepository.findReviewById(id)).thenReturn(createdReview);
    when(reviewRepository.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

    // Set up updated values
    Ranking updateRanking = Ranking.FourStar;
    String updateDescription = "Updated review description";

    // Act
    Review updatedReview = reviewService.updateReview(id, updateRanking, updateDescription, VALID_CUSTOMER_ID, VALID_GAME_ID);

    // Assert
    assertNotNull(updatedReview);
    assertEquals(updateRanking, updatedReview.getRanking());
    assertEquals(updateDescription, updatedReview.getDescription());
    assertEquals(game, updatedReview.getGame());

    verify(reviewRepository, times(1)).save(updatedReview); }

    @Test
    public void testCreateReviewWithNullDescription() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        reviewService.createReview(VALID_RANKING, null, VALID_CUSTOMER_ID, VALID_GAME_ID);
    });
    assertEquals("Description cannot be empty!", exception.getMessage());
}

    @Test
    public void testCreateReviewWithEmptyDescription() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(VALID_RANKING, "", VALID_CUSTOMER_ID, VALID_GAME_ID);
        });
        assertEquals("Description cannot be empty!", exception.getMessage());
    }

    @Test
    public void testCreateReviewWithNullRanking() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(null, VALID_DESCRIPTION, VALID_CUSTOMER_ID, VALID_GAME_ID);
        });
        assertEquals("Ranking cannot be empty!", exception.getMessage());
    }

    @Test
    public void testCreateReviewWithNonexistentCustomer() {
        when(customerRepository.findCustomerById(VALID_CUSTOMER_ID)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(VALID_RANKING, VALID_DESCRIPTION, VALID_CUSTOMER_ID, VALID_GAME_ID);
        });
        assertEquals("Customer does not exist!", exception.getMessage());
    }

    @Test
    public void testCreateReviewWithNonexistentGame() {
        when(customerRepository.findCustomerById(VALID_CUSTOMER_ID)).thenReturn(new Customer());
        when(gameRepository.findGameById(VALID_GAME_ID)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.createReview(VALID_RANKING, VALID_DESCRIPTION, VALID_CUSTOMER_ID, VALID_GAME_ID);
        });
        assertEquals("Game does not exist!", exception.getMessage());
    }

    @Test
    public void testUpdateReviewWithNonexistentReview() {
    when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(null);

    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
        reviewService.updateReview(VALID_REVIEW_ID, VALID_RANKING, VALID_DESCRIPTION, VALID_CUSTOMER_ID, VALID_GAME_ID);
    });
    assertEquals("Review does not exist!", exception.getMessage());
    }

    @Test
    public void testUpdateReviewWithNullDescription() {
        when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(new Review());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReview(VALID_REVIEW_ID, VALID_RANKING, null, VALID_CUSTOMER_ID, VALID_GAME_ID);
        });
        assertEquals("Description cannot be empty!", exception.getMessage());
    }

    @Test
    public void testUpdateReviewWithEmptyDescription() {
        when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(new Review());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReview(VALID_REVIEW_ID, VALID_RANKING, "", VALID_CUSTOMER_ID, VALID_GAME_ID);
        });
        assertEquals("Description cannot be empty!", exception.getMessage());
    }

    @Test
    public void testUpdateReviewWithNullRanking() {
        when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(new Review());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReview(VALID_REVIEW_ID, null, VALID_DESCRIPTION, VALID_CUSTOMER_ID, VALID_GAME_ID);
        });
        assertEquals("Ranking cannot be empty!", exception.getMessage());
    }

    @Test
    public void testUpdateReviewWithNonexistentCustomer() {
        when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(new Review());
        when(customerRepository.findCustomerById(VALID_CUSTOMER_ID)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReview(VALID_REVIEW_ID, VALID_RANKING, VALID_DESCRIPTION, VALID_CUSTOMER_ID, VALID_GAME_ID);
        });
        assertEquals("Customer does not exist!", exception.getMessage());
    }

    @Test
    public void testUpdateReviewWithNonexistentGame() {
        when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(new Review());
        when(customerRepository.findCustomerById(VALID_CUSTOMER_ID)).thenReturn(new Customer());
        when(gameRepository.findGameById(VALID_GAME_ID)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            reviewService.updateReview(VALID_REVIEW_ID, VALID_RANKING, VALID_DESCRIPTION, VALID_CUSTOMER_ID, VALID_GAME_ID);
        });
        assertEquals("Game does not exist!", exception.getMessage());
    }



}