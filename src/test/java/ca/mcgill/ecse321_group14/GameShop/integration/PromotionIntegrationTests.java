package ca.mcgill.ecse321_group14.GameShop.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.AfterAll;
import ca.mcgill.ecse321_group14.GameShop.dto.PromotionRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.PromotionResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.PromotionRepository;
import org.springframework.http.HttpMethod;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class PromotionIntegrationTests {
    
    @Autowired
    private TestRestTemplate client;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private GameRepository gameRepository;

    private int promotionId;
    private int gameId;

    @AfterAll
    public void clearDatabase() {
        promotionRepository.deleteAll();
        gameRepository.deleteAll();
    }


    @Test
    @Order(1)
    public void testCreateGame(){
        //make a game to be used with the promotion
        Game game = new Game("Test Game", "Test Description", "Category", 50, 10, Game.Rating.G, "Picture");
        game = gameRepository.save(game);
        gameId = game.getId();
        assertTrue(gameRepository.existsById(gameId));
        assertTrue(gameId>0,"Game id is not valid");
    }

    @Test
    @Order(2)
    public void testCreatePromotion(){
        // Arrange
        PromotionRequestDto promotionRequestDto = new PromotionRequestDto("Promo Description", 20, gameId);
        
        //Act
        ResponseEntity<PromotionResponseDto> response = client.exchange(
            "/promotion/" + gameId,
            org.springframework.http.HttpMethod.PUT,
            new org.springframework.http.HttpEntity<>(promotionRequestDto),
            PromotionResponseDto.class
        );
        
        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        PromotionResponseDto createdPromotion = response.getBody();
        assertNotNull(createdPromotion);
        assertEquals("Promo Description", createdPromotion.getDescription());
        assertEquals(20, createdPromotion.getDiscount());
        assertEquals(gameId, createdPromotion.getGameId());

        promotionId = createdPromotion.getId();
    }

    @Test
    @Order(3)
    public void testGetPromotion(){
        String url = "/promotion/" + promotionId;

        //Act
        ResponseEntity<PromotionResponseDto> response = client.getForEntity(url, PromotionResponseDto.class);

        //Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PromotionResponseDto promotion = response.getBody();
        assertNotNull(promotion);
        assertEquals("Promo Description", promotion.getDescription());
        assertEquals(20, promotion.getDiscount());
        assertEquals(gameId, promotion.getGameId());
    }


    @Test
    @Order(4)
    public void testUpdatePromotion(){
        // Arrange
        PromotionRequestDto promotionRequestDto = new PromotionRequestDto("Updated Promo Description", 30, gameId);

        // Act
        ResponseEntity<PromotionResponseDto> updateResponse = client.exchange(
            "/promotion/update/" + promotionId,
            org.springframework.http.HttpMethod.PUT,
            new org.springframework.http.HttpEntity<PromotionRequestDto>(promotionRequestDto),
            PromotionResponseDto.class
    );

        // Assert
        ResponseEntity<PromotionResponseDto> response = client.getForEntity("/promotion/" + promotionId, PromotionResponseDto.class);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        PromotionResponseDto updatedPromotion = response.getBody();
        assertNotNull(updatedPromotion);
        assertEquals("Updated Promo Description", updatedPromotion.getDescription());
        assertEquals(30, updatedPromotion.getDiscount());
        assertEquals(gameId, updatedPromotion.getGameId());
    }

    @Test
    @Order(5)
    public void testDeletePromotion() {
        // Arrange
        String url = "/promotion/" + promotionId;

        // Act - Delete the promotion
        ResponseEntity<Void> deleteResponse = client.exchange(url, HttpMethod.DELETE, null, Void.class);

        // Assert - Verify deletion was successful
        assertNotNull(deleteResponse);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode(), "Delete operation did not return expected status.");

        // Act - Try to fetch the deleted promotion, expect 404 status
        ResponseEntity<String> getResponse = client.getForEntity(url, String.class);

        // Assert - Expect a 404 NOT_FOUND status for a non-existing promotion
        assertNotNull(getResponse);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode(), "Promotion should not exist after deletion.");
    }
    
}
