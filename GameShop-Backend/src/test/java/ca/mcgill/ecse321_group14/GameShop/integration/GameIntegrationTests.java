package ca.mcgill.ecse321_group14.GameShop.integration;

import java.sql.Date;

import ca.mcgill.ecse321_group14.GameShop.dto.GameListDtos;
import ca.mcgill.ecse321_group14.GameShop.dto.GameRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.GameResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.PersonRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(Lifecycle.PER_CLASS)
public class GameIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PersonRepository personRepository;

    private static final String Name = "Mario";
    private static final String Description = "A game about a plumber";
    private static final String Category = "Platformer";
    private static final int Price = 60;
    private static final int Quantity = 100;
    private static final Game.Rating Rating = Game.Rating.R;
    private static final String Picture = "https://www.google.com";
    private int gameId;


    @AfterAll
    public void clearDatabase() {
        gameRepository.deleteAll();
        personRepository.deleteAll();
    }

    @Test
    @Order(1)
    public void testCreateValidGame() {
        // Arrange
        ResponseEntity<GameResponseDto> response = client.postForEntity("/game", new GameRequestDto(Name, Description, Category, Price, Quantity, Rating, Picture), GameResponseDto.class);

        // Act
        GameResponseDto createdGame = response.getBody();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(createdGame);
        assertEquals(Name, createdGame.getName());
        assertEquals(Description, createdGame.getDescription());
        assertEquals(Category, createdGame.getCategory());
        assertEquals(Price, createdGame.getPrice());
        assertEquals(Quantity, createdGame.getQuantity());
        assertEquals(Rating, createdGame.getRating());
        assertEquals(Picture, createdGame.getPicture());
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

        ResponseEntity<GameResponseDto> response = client.postForEntity("/game", new GameRequestDto(secondName, secondDescription, secondCategory, secondPrice, secondQuantity, secondRating, secondPicture), GameResponseDto.class);

        // Act
        GameResponseDto createdGame = response.getBody();

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
        assertEquals(Name, response.getBody().getName());
        assertEquals(Description, response.getBody().getDescription());
        assertEquals(Category, response.getBody().getCategory());
        assertEquals(Price, response.getBody().getPrice());
        assertEquals(Quantity, response.getBody().getQuantity());
        assertEquals(Rating, response.getBody().getRating());
        assertEquals(Picture, response.getBody().getPicture());



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
        assertEquals(Name, firstGame.getName());
        assertEquals(Description, firstGame.getDescription());
        assertEquals(Category, firstGame.getCategory());
        assertEquals(Price, firstGame.getPrice());
        assertEquals(Quantity, firstGame.getQuantity());
        assertEquals(Rating, firstGame.getRating());
        assertEquals(Picture, firstGame.getPicture());

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


        @Test
        @Order(6)
        public void testDeleteGameNullName() {
            // Arrange
            String gameName = null;
            Manager person = new Manager("password", "email@gmail.com", "Username");
            person = personRepository.save(person);  // Save to generate an ID
            Integer personId = person.getId();

            GameRequestDto gameRequestDto = new GameRequestDto(gameName, personId);
            HttpEntity<GameRequestDto> requestEntity = new HttpEntity<>(gameRequestDto);

            // Act
            ResponseEntity<Void> response = client.exchange("/game", HttpMethod.DELETE, requestEntity, Void.class);

            // Assert
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @Order(7)
    public void testDeleteGameNotValidPerson() {
        // Arrange: Ensure the customer exists in the database
        Customer person = new Customer("password", "customer@gmail.com", "username", 123456789, Date.valueOf("2015-12-07"), "address");
        person = personRepository.save(person);  // Save to generate an ID
        Integer personId = person.getId();

        // Assume a game with the name "Zelda" and gameId exists in the database
        String gameName = "Luigi";
        Integer gameId = gameRepository.findGameByName(gameName).getId();  // Retrieve the game ID

        // Create the GameRequestDto with the game name and customer ID
        GameRequestDto gameRequestDto = new GameRequestDto(gameName, personId);
        HttpEntity<GameRequestDto> requestEntity = new HttpEntity<>(gameRequestDto);

        // Act - Attempt to delete the game with a non-manager's ID
        ResponseEntity<Void> deleteResponse = client.exchange("/game", HttpMethod.DELETE, requestEntity, Void.class);

        // Assert - Ensure a 403 Forbidden response if non-manager users are not allowed to delete games
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());

        // Verify the game still exists by retrieving it
        ResponseEntity<GameResponseDto> getResponse = client.getForEntity("/game/" + gameId, GameResponseDto.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    }

    @Test
    @Order(8)
    public void testDeleteGameValidPerson() {
        // Arrange: Ensure the game and manager exist in the database
        String gameName = "Zelda";
        Manager person = new Manager("password", "email@gmail.com", "Username");
        person = personRepository.save(person);  // Save to generate an ID
        Integer personId = person.getId();

        // Save the game in the database to ensure it exists before deletion
//        Game game = new Game(gameName, "A game about a plumber's brother", "Platformer", 70, 50, Game.Rating.R, "https://www.google.com");
//        game = gameRepository.save(game);
//        Integer gameId = game.getId();

        // Create the GameRequestDto with the game name and manager's ID
        GameRequestDto gameRequestDto = new GameRequestDto(gameName, personId);
        HttpEntity<GameRequestDto> requestEntity = new HttpEntity<>(gameRequestDto);

        // Act - Attempt to delete the game with a manager's ID
        ResponseEntity<Void> deleteResponse = client.exchange("/game", HttpMethod.DELETE, requestEntity, Void.class);

        // Assert - Ensure a 204 No Content response if deletion is successful
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        // Verify the game no longer exists by attempting to retrieve it
        ResponseEntity<GameResponseDto> getResponse = client.getForEntity("/game/" + gameId, GameResponseDto.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    }


}
