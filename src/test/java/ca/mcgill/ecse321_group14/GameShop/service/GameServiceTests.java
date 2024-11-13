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

        // Act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameService.deleteGame(gameName, person);
        });

        // Assert
        assertEquals("Only Staff can delete games!", exception.getMessage());
        verify(gameRepository, times(0)).deleteGameByName(gameName); // Ensure deletion doesn't happen
    }



    @Test
    public void testDeleteGame() {
        // Arrange
        String gameName = "Mario";
        Manager person = new Manager("password", "email@gmail.com", "username");

        Game game = new Game(gameName, "A game about a plumber", "Platformer", 60, 100, Game.Rating.R, "https://www.google.com");

        // Mock the behavior of findGameByName to return a game
        when(gameRepository.findGameByName(gameName)).thenReturn(game);

        // Act
        gameService.deleteGame(gameName, person);

        // Assert
        verify(gameRepository, times(1)).findGameByName(gameName);
        verify(gameRepository, times(1)).deleteGameByName(gameName);
    }


}