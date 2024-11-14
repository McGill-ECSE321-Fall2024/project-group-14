package ca.mcgill.ecse321_group14.GameShop.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;



@SpringBootTest
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class GameServiceTests {
    @Mock
    private GameRepository gameRepository;
    @Mock
    private PersonRepository personRepository;
    @InjectMocks
    private GameService gameService;

    @SuppressWarnings("null")
    @Test
    public void testCreateValidGame() {
        //arrange
        String name = "Mario";
        String description = "A game about a plumber";
        String category = "Platformer";
        int price = 60;
        int quantity = 100;
        Game.Rating rating = Game.Rating.R;
        String picture = "https://www.google.com";
        when(gameRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));


        //act
        Game createdGame = gameService.createGame(name, description, category, price, quantity, rating, picture);

        //assert
        assertNotNull(createdGame);
        assertEquals(name, createdGame.getName());
        assertEquals(description, createdGame.getDescription());
        assertEquals(category, createdGame.getCategory());
        assertEquals(price, createdGame.getPrice());
        assertEquals(quantity, createdGame.getQuantity());
        assertEquals(rating, createdGame.getRating());
        assertEquals(picture, createdGame.getPicture());
        verify(gameRepository, times(1)).save(any());


    }

    @Test
    public void testCreateGameNullName() {
        //arrange
        String name = null;
        String description = "A game about a plumber";
        String category = "Platformer";
        int price = 60;
        int quantity = 100;
        Game.Rating rating = Game.Rating.R;
        String picture = "https://www.google.com";

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.createGame(name, description, category, price, quantity, rating, picture);
        });

        //assert
        assertEquals("All fields must be filled!", exception.getMessage());
        verify(gameRepository, times(0)).save(any());
    }

    @Test
    public void testCreateGameNullDescription() {
        //arrange
        String name = "Mario";
        String description = null;
        String category = "Platformer";
        int price = 60;
        int quantity = 100;
        Game.Rating rating = Game.Rating.R;
        String picture = "https://www.google.com";

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.createGame(name, description, category, price, quantity, rating, picture);
        });

        //assert
        assertEquals("All fields must be filled!", exception.getMessage());
        verify(gameRepository, times(0)).save(any());
    }

    @Test
    public void testCreateGameNullCategory() {
        //arrange
        String name = "Mario";
        String description = "A game about a plumber";
        String category = null;
        int price = 60;
        int quantity = 100;
        Game.Rating rating = Game.Rating.R;
        String picture = "https://www.google.com";

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.createGame(name, description, category, price, quantity, rating, picture);
        });

        //assert
        assertEquals("All fields must be filled!", exception.getMessage());
        verify(gameRepository, times(0)).save(any());
    }

    @Test
    public void testCreateGameNullPrice() {
        //arrange
        String name = "Mario";
        String description = "A game about a plumber";
        String category = "Platformer";
        int price = -1;
        int quantity = 100;
        Game.Rating rating = Game.Rating.R;
        String picture = "https://www.google.com";

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.createGame(name, description, category, price, quantity, rating, picture);
        });

        //assert
        assertEquals("All fields must be filled!", exception.getMessage());
        verify(gameRepository, times(0)).save(any());
    }

    @Test
    public void testCreateGameNullQuantity() {
        //arrange
        String name = "Mario";
        String description = "A game about a plumber";
        String category = "Platformer";
        int price = 60;
        int quantity = -1;
        Game.Rating rating = Game.Rating.R;
        String picture = "https://www.google.com";

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.createGame(name, description, category, price, quantity, rating, picture);
        });

        //assert
        assertEquals("All fields must be filled!", exception.getMessage());
        verify(gameRepository, times(0)).save(any());
    }

    @Test
    public void testCreateGameNullRating() {
        //arrange
        String name = "Mario";
        String description = "A game about a plumber";
        String category = "Platformer";
        int price = 60;
        int quantity = 100;
        Game.Rating rating = null;
        String picture = "https://www.google.com";

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.createGame(name, description, category, price, quantity, rating, picture);
        });

        //assert
        assertEquals("All fields must be filled!", exception.getMessage());
        verify(gameRepository, times(0)).save(any());
    }

    @Test
    public void testCreateGameNullPicture() {
        //arrange
        String name = "Mario";
        String description = "A game about a plumber";
        String category = "Platformer";
        int price = 60;
        int quantity = 100;
        Game.Rating rating = Game.Rating.R;
        String picture = null;

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.createGame(name, description, category, price, quantity, rating, picture);
        });

        //assert
        assertEquals("All fields must be filled!", exception.getMessage());
        verify(gameRepository, times(0)).save(any());
    }

    @Test
    public void testUpdateGameByValidId(){
        //arrange
        int id = 42;
        String name = "Mario";
        String description = "A game about a plumber";
        String category = "Platformer";
        int price = 60;
        int quantity = 100;
        Game.Rating rating = Game.Rating.R;
        String picture = "https://www.google.com";
        Game game = new Game(name, description, category, price, quantity, rating, picture);
        when(gameRepository.findGameById(id)).thenReturn(game);
        when(gameRepository.save(any())).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        String newName = "Luigi";
        String newDescription = "A game about a plumber's brother";
        String newCategory = "Platformer";
        int newPrice = 70;
        int newQuantity = 50;
        Game.Rating newRating = Game.Rating.R;
        String newPicture = "https://www.google.com";
        //act
        Game updatedGame = gameService.updateGameById(id, newName, newDescription, newCategory, newPrice, newQuantity, newRating, newPicture);

        //assert
        assertNotNull(updatedGame);
        assertEquals(newName, updatedGame.getName());
        assertEquals(newDescription, updatedGame.getDescription());
        assertEquals(newCategory, updatedGame.getCategory());
        assertEquals(newPrice, updatedGame.getPrice());
        assertEquals(newQuantity, updatedGame.getQuantity());
        assertEquals(newRating, updatedGame.getRating());
        assertEquals(newPicture, updatedGame.getPicture());
        verify(gameRepository, times(1)).findGameById(id);
        verify(gameRepository, times(1)).save(any());
    }

    @Test
    public void testUpdateGameByInvalidNegativeId() {
        // Arrange
        int id = -1;
        String name = "Mario";
        String description = "A game about a plumber";
        String category = "Platformer";
        int price = 60;
        int quantity = 100;
        Game.Rating rating = Game.Rating.R;
        String picture = "https://www.google.com";

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.updateGameById(id, name, description, category, price, quantity, rating, picture);
        });

        // Assert
        assertEquals("ID cannot be negative!", exception.getMessage());
        verify(gameRepository, times(0)).findGameById(id); // Ensure repository is not called
    }

    @Test
    public void testUpdateGameByInvalidId(){
        //arrange
        int id = 42;
        String name = "Mario";
        String description = "A game about a plumber";
        String category = "Platformer";
        int price = 60;
        int quantity = 100;
        Game.Rating rating = Game.Rating.R;
        String picture = "https://www.google.com";
        when(gameRepository.findGameById(id)).thenReturn(null);

        String newName = "Luigi";
        String newDescription = "A game about a plumber's brother";
        String newCategory = "Platformer";
        int newPrice = 70;
        int newQuantity = 50;
        Game.Rating newRating = Game.Rating.R;
        String newPicture = "https://www.google.com";
        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.updateGameById(id, newName, newDescription, newCategory, newPrice, newQuantity, newRating, newPicture);
        });

        //assert
        assertEquals("Game does not exist!", exception.getMessage());
        verify(gameRepository, times(1)).findGameById(id);
        verify(gameRepository, times(0)).save(any());
    }

    @Test
    public void testFindGameByValidId(){
        //arrange
        int id = 42;
        Game game = new Game("Mario", "A game about a plumber", "Platformer", 60, 100, Game.Rating.R, "https://www.google.com");
        when(gameRepository.findGameById(id)).thenReturn(game);

        //act
        Game validGame = gameService.getGameById(id);

        assertNotNull(validGame);
        assertEquals(game, validGame);
        assertEquals(game.getId(), validGame.getId());
        assertEquals(game.getName(), validGame.getName());
        assertEquals(game.getDescription(), validGame.getDescription());
        assertEquals(game.getCategory(), validGame.getCategory());
        assertEquals(game.getPrice(), validGame.getPrice());
        assertEquals(game.getQuantity(), validGame.getQuantity());
        assertEquals(game.getRating(), validGame.getRating());
        assertEquals(game.getPicture(), validGame.getPicture());
        verify(gameRepository, times(1)).findGameById(id);

    }

    @Test
    public void testFindGameByNonExistentId() {
        // Arrange
        int id = 42;
        when(gameRepository.findGameById(id)).thenReturn(null);

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.getGameById(id);
        });

        // Assert
        assertEquals("Game does not exist!", exception.getMessage());
        verify(gameRepository, times(1)).findGameById(id);
    }
    @Test
    public void testFindGameByInvalidNegativeId() {
        // Arrange
        int id = -1;

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.getGameById(id);
        });

        // Assert
        assertEquals("ID cannot be null!", exception.getMessage());
        verify(gameRepository, times(0)).findGameById(id); // Ensure repository is not called
    }

    @Test
    public void testDeleteGameInvalidPerson() {
        // Arrange
        String gameName = "Mario";
        Customer person = new Customer("password", "customer@gmail.com", "username", 123456789, Date.valueOf("2015-12-07"), "address");

        // Mock saving the person without explicitly setting an ID
        when(personRepository.save(person)).thenReturn(person);

        personRepository.save(person);  // Save the person without setting an ID

        Integer personId = person.getId();

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.deleteGame(gameName, personId);
        });

        // Assert
        assertEquals("Only Staff can delete games!", exception.getMessage());
        verify(gameRepository, times(0)).deleteGameByName(gameName); // Ensure deletion doesn't happen
    }

    @Test
    public void testDeleteGameNullName() {
        // Arrange
        String gameName = null;
        Manager person = new Manager("password", "email@gmail.com", "username");

        // Mock saving the person and retrieving the generated ID
        when(personRepository.save(person)).thenReturn(person);
        personRepository.save(person);
        Integer personId = person.getId();

        when(personRepository.findPersonById(personId)).thenReturn(person);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.deleteGame(gameName, person.getId());
        });

        assertEquals("Name cannot be null!", exception.getMessage());


    }







    @Test
    public void testDeleteGame() {
        // Arrange
        String gameName = "Mario";
        Manager person = new Manager("password", "email@gmail.com", "username");

        // Save the person and retrieve the generated ID
        when(personRepository.save(person)).thenReturn(person);
        personRepository.save(person);
        Integer personId = person.getId();  // The ID is generated here

        Game game = new Game(gameName, "A game about a plumber", "Platformer", 60, 100, Game.Rating.R, "https://www.google.com");

        // Mock the behavior of findGameByName and findPersonById to return the game and person
        when(gameRepository.findGameByName(gameName)).thenReturn(game);
        when(personRepository.findPersonById(personId)).thenReturn(person);

        // Act
        gameService.deleteGame(gameName, personId);

        // Assert
        verify(gameRepository, times(1)).findGameByName(gameName);
        verify(gameRepository, times(1)).deleteGameByName(gameName);
    }



}