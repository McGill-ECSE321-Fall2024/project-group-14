package ca.mcgill.ecse321_group14.GameShop.service;

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
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.ReviewRepository;




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
        assertEquals(customer, createdReview.getCustomer());
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
        Customer customer = new Customer();
        Game game = new Game();
        Review review = new Review();
        when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(review);
        when(customerRepository.findCustomerById(VALID_CUSTOMER_ID)).thenReturn(customer);
        when(gameRepository.findGameById(VALID_GAME_ID)).thenReturn(game);
        when(reviewRepository.save(any(Review.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        Review updatedReview = reviewService.updateReview(VALID_REVIEW_ID, VALID_RANKING, VALID_DESCRIPTION, VALID_CUSTOMER_ID, VALID_GAME_ID);

        assertNotNull(updatedReview);
        assertEquals(VALID_RANKING, updatedReview.getRanking());
        assertEquals(VALID_DESCRIPTION, updatedReview.getDescription());
        assertEquals(customer, updatedReview.getCustomer());
        assertEquals(game, updatedReview.getGame());
        verify(reviewRepository, times(1)).save(updatedReview);
    }
}