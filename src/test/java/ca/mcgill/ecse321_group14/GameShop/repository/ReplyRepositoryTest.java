package ca.mcgill.ecse321_group14.GameShop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


import ca.mcgill.ecse321_group14.GameShop.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ReplyRepositoryTest {
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        replyRepository.deleteAll();
        reviewRepository.deleteAll();
        customerRepository.deleteAll();
        gameRepository.deleteAll();
        managerRepository.deleteAll();


    }

    @Test
    public void testCreateAndReplyReview(){
        // create customer
        Customer customer = new Customer("password", "email", "username", 123456, null, "address");
        customer = customerRepository.save(customer);

        //create game
        Game game = new Game("Mario", null, null, 0, 0, null, null);
        game = gameRepository.save(game);

        //create review on the game by customer
        Review review = new Review(Review.Ranking.OneStar,"love this game", customer, game);
        review = reviewRepository.save(review);

        //create manager
        Manager manager = new Manager("password", "easy@mcgill.ca", "testManager");
        managerRepository.save(manager);

        //create reply in db
        Reply reply = new Reply("Great to hear", review, manager);
        replyRepository.save(reply);

        int id = reply.getId();

        //act
        Reply readReply = replyRepository.findReplyById(id);

        assertNotNull(readReply);
        assertEquals(id, readReply.getId());
        assertEquals(reply.getManager().getId(), readReply.getManager().getId());
        assertEquals(reply.getReview().getId(), readReply.getReview().getId());
        assertEquals("Great to hear", readReply.getDescription());


    }
}
