package ca.mcgill.ecse321_group14.GameShop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Promotion;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.PromotionRepository;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceTests {

    @Mock
    private PromotionRepository promotionRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private PromotionService promotionService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreatePromotion() {
        // Arrange
        Game game = new Game("Game1", "Description", "Category", 100, 10, Game.Rating.G, "Picture");
        
        when(gameRepository.findGameById(game.getId())).thenReturn(game);
        when(promotionRepository.save(any(Promotion.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        // Act
        Promotion createdPromotion = promotionService.createPromotion("Promo Description", 20, game);

        // Assert
        assertNotNull(createdPromotion);
        assertEquals("Promo Description", createdPromotion.getDescription());
        assertEquals(20, createdPromotion.getDiscount());
        assertEquals(game, createdPromotion.getGame());
        verify(promotionRepository, times(1)).save(createdPromotion);
    }

    @Test
    public void testCreatePromotionGameDoesNotExist() {
        // Arrange
        Game game = new Game();
        
        when(gameRepository.findGameById(game.getId())).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            promotionService.createPromotion("Promo Description", 20, game)
        );
        assertEquals("Game does not exist!", exception.getMessage());
    }

    @Test
    public void testGetPromotion() {
        // Arrange
        Promotion promotion = new Promotion("Promo Description", 20, new Game());

        when(promotionRepository.findPromotionById(promotion.getId())).thenReturn(promotion);

        // Act
        Promotion retrievedPromotion = promotionService.getPromotion(promotion.getId());

        // Assert
        assertNotNull(retrievedPromotion);
        assertEquals(promotion, retrievedPromotion);
    }

    @Test
    public void testGetPromotionNotFound() {
        // Arrange
        when(promotionRepository.findPromotionById(999)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            promotionService.getPromotion(999)
        );
        assertEquals("Promotion does not exist!", exception.getMessage());
    }

    @Test
    public void testUpdatePromotion() {
        // Arrange
        Game game = new Game("Game1", "Description", "Category", 100, 10, Game.Rating.G, "Picture");
        Promotion promotion = new Promotion("Promo Description", 20, game);

        when(promotionRepository.findPromotionById(promotion.getId())).thenReturn(promotion);
        when(promotionRepository.save(any(Promotion.class))).thenAnswer((InvocationOnMock invocation) -> invocation.getArgument(0));

        // Act
        Promotion updatedPromotion = promotionService.updatePromotion(promotion.getId(), "Updated Description", 30, game);

        // Assert
        assertNotNull(updatedPromotion);
        assertEquals("Updated Description", updatedPromotion.getDescription());
        assertEquals(30, updatedPromotion.getDiscount());
        assertEquals(game, updatedPromotion.getGame());
        verify(promotionRepository, times(1)).save(updatedPromotion);
    }

    @Test
    public void testUpdatePromotionNotFound() {
        // Arrange
        when(promotionRepository.findPromotionById(9999)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            promotionService.updatePromotion(9999, "Updated Description", 30, new Game())
        );
        assertEquals("Promotion does not exist!", exception.getMessage());
    }

    @Test
    public void testDeletePromotion() {
        // Arrange
        Promotion promotion = new Promotion("Promo Description", 20, new Game());

        when(promotionRepository.findPromotionById(promotion.getId())).thenReturn(promotion);

        // Act
        promotionService.deletePromotion(promotion.getId());

        // Assert
        verify(promotionRepository, times(1)).delete(promotion);
    }

    @Test
    public void testDeletePromotionNotFound() {
        // Arrange
        when(promotionRepository.findPromotionById(1)).thenReturn(null);

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> 
            promotionService.deletePromotion(1)
        );
        assertEquals("Promotion does not exist!", exception.getMessage());
    }
}
