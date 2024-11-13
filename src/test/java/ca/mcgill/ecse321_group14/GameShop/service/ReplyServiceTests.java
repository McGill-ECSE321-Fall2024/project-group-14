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

import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.model.Reply;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.repository.ManagerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.ReplyRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.ReviewRepository;

@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ReplyServiceTests {
    @Mock
    private ReplyRepository replyRepository;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ManagerRepository managerRepository;
    @InjectMocks
    private ReplyService replyService;

    private static final String VALID_DESCRIPTION = "This is a reply";
    private static final int VALID_REVIEW_ID = 1;
    private static final int VALID_MANAGER_ID = 1;

    @Test
    public void testCreateValidReply() {
        Review review = new Review();
        Manager manager = new Manager();
        when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(review);
        when(managerRepository.findManagerById(VALID_MANAGER_ID)).thenReturn(manager);
        when(replyRepository.save(any(Reply.class))).thenAnswer((InvocationOnMock iom) -> iom.getArgument(0));

        Reply createdReply = replyService.createReply(VALID_DESCRIPTION, VALID_REVIEW_ID, VALID_MANAGER_ID);

        assertNotNull(createdReply);
        assertEquals(VALID_DESCRIPTION, createdReply.getDescription());
        assertEquals(review, createdReply.getReview());
        assertEquals(manager, createdReply.getManager());
        verify(replyRepository, times(1)).save(createdReply);
    }

    @Test
    public void testCreateReplyWithNullDescription() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            replyService.createReply(null, VALID_REVIEW_ID, VALID_MANAGER_ID);
        });
        assertEquals("Description cannot be empty!", ex.getMessage());
    }

    @Test
    public void testCreateReplyWithNonExistentReview() {
        when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            replyService.createReply(VALID_DESCRIPTION, VALID_REVIEW_ID, VALID_MANAGER_ID);
        });
        assertEquals("Review does not exist!", ex.getMessage());
    }

    @Test
    public void testCreateReplyWithNonExistentManager() {
        Review review = new Review();
        when(reviewRepository.findReviewById(VALID_REVIEW_ID)).thenReturn(review);
        when(managerRepository.findManagerById(VALID_MANAGER_ID)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            replyService.createReply(VALID_DESCRIPTION, VALID_REVIEW_ID, VALID_MANAGER_ID);
        });
        assertEquals("Manager does not exist!", ex.getMessage());
    }

    @Test
    public void testGetReplyByValidId() {
        Reply reply = new Reply(VALID_DESCRIPTION, new Review(), new Manager());
        when(replyRepository.findReplyById(1)).thenReturn(reply);

        Reply foundReply = replyService.getReply(1);

        assertNotNull(foundReply);
        assertEquals(reply, foundReply);
    }

    @Test
    public void testGetReplyByInvalidId() {
        when(replyRepository.findReplyById(1)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            replyService.getReply(1);
        });
        assertEquals("Reply does not exist!", ex.getMessage());
    }

    @Test
    public void testDeleteReplyByValidId() {
        Reply reply = new Reply(VALID_DESCRIPTION, new Review(), new Manager());
        when(replyRepository.findReplyById(1)).thenReturn(reply);

        Reply deletedReply = replyService.deleteReply(1);

        assertNotNull(deletedReply);
        assertEquals(reply, deletedReply);
        verify(replyRepository, times(1)).delete(reply);
    }

    @Test
    public void testDeleteReplyByInvalidId() {
        when(replyRepository.findReplyById(1)).thenReturn(null);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            replyService.deleteReply(1);
        });
        assertEquals("Reply does not exist!", ex.getMessage());
    }
}
