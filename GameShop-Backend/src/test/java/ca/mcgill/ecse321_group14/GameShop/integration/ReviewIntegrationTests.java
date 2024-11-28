package ca.mcgill.ecse321_group14.GameShop.integration;


import ca.mcgill.ecse321_group14.GameShop.dto.ReplyResponseDto;
import ca.mcgill.ecse321_group14.GameShop.dto.ReviewRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.ReviewResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.ReplyRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.ReviewRepository;
import jakarta.transaction.Transactional;
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
public class ReviewIntegrationTests {

    @Autowired
    private TestRestTemplate client;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private GameRepository gameRepository;


    private int reviewId;
    private Review.Ranking ranking = Review.Ranking.FiveStar;
    private String description = "Test Description";

    Customer customer = new Customer("password","email","username",123123, Date.valueOf("2015-12-07"), "address");
    Game game = new Game("Test Game", "Test Description", "Category", 50, 10, Game.Rating.G, "Picture");



    @Test
    @Order(1)
    public void testCreateCustomer(){
        //create a customer to be used with the review
        customer = customerRepository.save(customer);
        assertTrue(customerRepository.existsById(customer.getId()));
        assertTrue(customer.getId()>0,"Customer id is not valid");
    }

    @Test
    @Order(2)
    public void testCreateGame(){
        //create a game to be used with the review
        game = gameRepository.save(game);
        assertTrue(gameRepository.existsById(game.getId()));
        assertTrue(game.getId()>0,"Game id is not valid");
    }

    @Test
    @Order(3)
    public void testCreateReview(){
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto(ranking, description, customer.getId(), game.getId());

        ResponseEntity<ReviewResponseDto> response = client.postForEntity("/review", reviewRequestDto, ReviewResponseDto.class);

        ReviewResponseDto createdReview = response.getBody();

        assertNotNull(createdReview);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ranking, createdReview.getRanking());
        assertEquals(description, createdReview.getDescription());
        assertEquals(customer.getId(), createdReview.getCustomerId());
        assertEquals(game.getId(), createdReview.getGameId());

        reviewId = createdReview.getId();
    }

    @Test
    @Order(4)
    public void testGetReview(){
        String url = "/review/" + reviewId;

        ResponseEntity<ReviewResponseDto> response = client.getForEntity(url, ReviewResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ReviewResponseDto foundReview = response.getBody();
        assertNotNull(foundReview);
        assertEquals(ranking, foundReview.getRanking());
        assertEquals(description, foundReview.getDescription());
        assertEquals(customer.getId(), foundReview.getCustomerId());
        assertEquals(game.getId(), foundReview.getGameId());
    }




}