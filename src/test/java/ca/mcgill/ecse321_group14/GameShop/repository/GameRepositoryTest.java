package ca.mcgill.ecse321_group14.GameShop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ca.mcgill.ecse321_group14.GameShop.model.Game;

@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        gameRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadGame() {
        // Arrange
        Game game = new Game("Mario", null, null, 0, 0, Game.Rating.R,null); // create a new game
        game = gameRepository.save(game); // save the game to the database
        int id = game.getId(); // get the id of the game

        // Act
        Game readGame = gameRepository.findGameById(id); // read the game from the database

        // Assert
        assertNotNull(readGame);
        assertEquals(game.getId(), readGame.getId());
        assertEquals(game.getName(), readGame.getName());
        assertEquals(game.getDescription(), readGame.getDescription());
        assertEquals(game.getPrice(), readGame.getPrice());
    }
}
