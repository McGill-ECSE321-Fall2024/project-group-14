package ca.mcgill.ecse321_group14.GameShop.integration;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import ca.mcgill.ecse321_group14.GameShop.dto.GameListDtos;
import ca.mcgill.ecse321_group14.GameShop.dto.GameRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.GameResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private GameRepository gameRepository;

    private static final String name = "Mario";
    private static final String description = "A game about a plumber";
    private static final String category = "Platformer";
    private static final int price = 60;
    private static final int quantity = 100;
    private static final Game.Rating rating = Game.Rating.R;
    private static final String picture = "https://www.google.com";
    private int gameId;


    @AfterAll
    public void clearDatabase() {
        gameRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidGame() {
        // Arrange
        ResponseEntity<Game> response = client.postForEntity("/game", new Game(name, description, category, price, quantity, rating, picture), Game.class);

        // Act
        Game createdGame = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(createdGame);
        assertEquals(name, createdGame.getName());
        assertEquals(description, createdGame.getDescription());
        assertEquals(category, createdGame.getCategory());
        assertEquals(price, createdGame.getPrice());
        assertEquals(quantity, createdGame.getQuantity());
        assertEquals(rating, createdGame.getRating());
        assertEquals(picture, createdGame.getPicture());
        this.gameId = response.getBody().getId();
    }

    @Test
    @Order(2)
    public void testCreateSecondGame() {
        // Arrange
        String secondName = "Zelda";
        String secondDescription = "A game about a hero";
        String secondCategory = "Adventure";
        int secondPrice = 70;
        int secondQuantity = 150;
        Game.Rating secondRating = Game.Rating.PG;
        String secondPicture = "https://example.com/zelda.jpg";

        ResponseEntity<Game> response = client.postForEntity("/game", new Game(secondName, secondDescription, secondCategory, secondPrice, secondQuantity, secondRating, secondPicture), Game.class);

        // Act
        Game createdGame = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(createdGame);
        assertEquals(secondName, createdGame.getName());
        assertEquals(secondDescription, createdGame.getDescription());
        assertEquals(secondCategory, createdGame.getCategory());
        assertEquals(secondPrice, createdGame.getPrice());
        assertEquals(secondQuantity, createdGame.getQuantity());
        assertEquals(secondRating, createdGame.getRating());
        assertEquals(secondPicture, createdGame.getPicture());
    }

    @Test
    @Order(3)
    public void testGetValidGameById(){
        //arrange
        String url = String.format("/game/%d", this.gameId);

        //act
        ResponseEntity<GameResponseDto> response = client.getForEntity(url, GameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.gameId, response.getBody().getId());
        assertEquals(name, response.getBody().getName());
        assertEquals(description, response.getBody().getDescription());
        assertEquals(category, response.getBody().getCategory());
        assertEquals(price, response.getBody().getPrice());
        assertEquals(quantity, response.getBody().getQuantity());
        assertEquals(rating, response.getBody().getRating());
        assertEquals(picture, response.getBody().getPicture());



    }


    @Test
    @Order(4)
    public void testGetAllGames() {
        // Act
        ResponseEntity<GameListDtos> response = client.getForEntity("/games", GameListDtos.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        GameListDtos gameListDtos = response.getBody();
        assertNotNull(gameListDtos);
        assertTrue(gameListDtos.getGames().size() >= 2, "Expected at least two games in the list");

        // Verify details of the first game in the list
        GameResponseDto firstGame = gameListDtos.getGames().get(0);
        assertNotNull(firstGame);
        assertEquals(name, firstGame.getName());
        assertEquals(description, firstGame.getDescription());
        assertEquals(category, firstGame.getCategory());
        assertEquals(price, firstGame.getPrice());
        assertEquals(quantity, firstGame.getQuantity());
        assertEquals(rating, firstGame.getRating());
        assertEquals(picture, firstGame.getPicture());

        // Verify details of the second game in the list
        GameResponseDto secondGame = gameListDtos.getGames().get(1);
        assertNotNull(secondGame);
        assertEquals("Zelda", secondGame.getName());
        assertEquals("A game about a hero", secondGame.getDescription());
        assertEquals("Adventure", secondGame.getCategory());
        assertEquals(70, secondGame.getPrice());
        assertEquals(150, secondGame.getQuantity());
        assertEquals(Game.Rating.PG, secondGame.getRating());
        assertEquals("https://example.com/zelda.jpg", secondGame.getPicture());
    }
    @Test
    @Order(5)
    public void testUpdateValidGameById() {
        //arrange
        String updatedName = "Luigi";
        String updatedDescription = "A game about a plumber's brother";
        String updatedCategory = "Platformer";
        int updatedPrice = 70;
        int updatedQuantity = 50;
        Game.Rating updatedRating = Game.Rating.R;
        String updatedPicture = "https://www.google.com";

        //act
        ResponseEntity<GameResponseDto> response = client.postForEntity("/game/updatebyid", new GameRequestDto(this.gameId, updatedName, updatedDescription, updatedCategory, updatedPrice, updatedQuantity, updatedRating, updatedPicture), GameResponseDto.class);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(this.gameId, response.getBody().getId());
        assertEquals(updatedName, response.getBody().getName());
        assertEquals(updatedDescription, response.getBody().getDescription());
        assertEquals(updatedCategory, response.getBody().getCategory());
        assertEquals(updatedPrice, response.getBody().getPrice());
        assertEquals(updatedQuantity, response.getBody().getQuantity());
        assertEquals(updatedRating, response.getBody().getRating());
        assertEquals(updatedPicture, response.getBody().getPicture());

    }

//    @Test
//    @Order(6)
//    public void testDeleteGame() {
//        //arrange
//        Manager person = new Manager("pasword", "email", "Username");
//        //act
//        ResponseEntity<void> response = client.delete("/game", new GameRequestDto("Luigi", person));
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }

}
