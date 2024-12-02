package ca.mcgill.ecse321_group14.GameShop.integration;

import ca.mcgill.ecse321_group14.GameShop.dto.ReplyRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.ReplyResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ReplyIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private ReplyRepository replyRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ManagerRepository managerRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private GameRepository gameRepository;

    private int replyId;
    private String description = "Test Description";
    private Review.Ranking ranking = Review.Ranking.FiveStar;

    Game game = new Game("Test Game", "Test Description", "Category", 50, 10, Game.Rating.G, "Picture");
    Customer customer = new Customer("password", "email", "username", 123123, Date.valueOf("2015-12-07"), "address");
    Manager manager = new Manager("password", "email", "username");

    @AfterAll
    public void clearDatabase() {
        replyRepository.deleteAll();
        managerRepository.deleteAll();

    }

    @Test
    @Order(1)
    public void testCreateManager() {
        manager = managerRepository.save(manager);
        assertTrue(managerRepository.existsById(manager.getId()));
        assertTrue(manager.getId() > 0, "Manager id is not valid");
    }

    @Test
    @Order(2)
    public void testCreateCustomer() {
        customer = customerRepository.save(customer);
        assertTrue(customerRepository.existsById(customer.getId()));
        assertTrue(customer.getId() > 0, "Customer id is not valid");
    }

    @Test
    @Order(3)
    public void testCreateGame() {
        game = gameRepository.save(game);
        assertTrue(gameRepository.existsById(game.getId()));
        assertTrue(game.getId() > 0, "Game id is not valid");
    }

    @Test
    @Order(4)
    public void testCreateReview() {
        Review review = new Review(ranking, description, customer, game);
        review = reviewRepository.save(review);
        assertTrue(reviewRepository.existsById(review.getId()));
        assertTrue(review.getId() > 0, "Review id is not valid");
    }

    @Test
    @Order(5)
    public void testCreateReply() {
        Review review = new Review(ranking, description, customer, game);
        review = reviewRepository.save(review);
        ReplyRequestDto replyRequestDto = new ReplyRequestDto(review.getId(), description, manager.getId());

        ResponseEntity<ReplyResponseDto> response = client.postForEntity("/reply", replyRequestDto,
                ReplyResponseDto.class);

        ReplyResponseDto createdReply = response.getBody();

        assertNotNull(createdReply);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(description, createdReply.getMessage());
        replyId = createdReply.getId();
    }

    @Test
    @Order(6)
    public void testGetReply() {
        ResponseEntity<ReplyResponseDto> response = client.getForEntity("/reply/" + replyId, ReplyResponseDto.class);
        ReplyResponseDto reply = response.getBody();

        assertNotNull(reply);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(description, reply.getMessage());
    }

    @Test
    @Order(7)
    public void testDeleteReply() {
        ResponseEntity<Void> response = client.exchange("/reply/delete/" + replyId, HttpMethod.GET, HttpEntity.EMPTY,
                Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}