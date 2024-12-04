package ca.mcgill.ecse321_group14.GameShop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Promotion;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.PromotionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class PromotionServiceTests {

    @Mock
    private PromotionRepository promotionRepository;

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private PromotionService promotionService;

    private Game game;
    private Promotion promotion;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        // Setup default Game and Promotion
        game = new Game("Zelda", "Adventure Game", "Action", 100, 50, Game.Rating.G, "zelda.png");
        

        promotion = new Promotion("Summer Sale", 20, game);
        
    }


    @Test
    public void testCreatePromotion_Valid() {
        // Arrange
        Game game = new Game("Game1", "Description", "Category", 100, 10, Game.Rating.G, "Picture");
        Promotion promotion = new Promotion("Promo Description", 20, game);

        when(promotionRepository.findPromotionById(promotion.getId())).thenReturn(promotion);
        when(promotionRepository.save(any(Promotion.class))).thenAnswer(invocation -> invocation.getArgument(0));
        when(gameRepository.save(any(Game.class))).thenAnswer(invocation -> invocation.getArgument(0)); // Ensure this is necessary

        // Act
        Promotion updatedPromotion = promotionService.updatePromotion(promotion.getId(), "Updated Description", 30, game);

        // Assert
        assertNotNull(updatedPromotion);
        assertEquals("Updated Description", updatedPromotion.getDescription());
        assertEquals(30, updatedPromotion.getDiscount());
        assertEquals(70, game.getDiscountedprice()); // Ensure the discounted price is updated
        verify(gameRepository, times(1)).save(game); // Verify if Game is updated
        verify(promotionRepository, times(1)).save(updatedPromotion); // Verify Promotion is updated
    }
    
    @Test
    public void testCreatePromotion_InvalidDiscount() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            promotionService.createPromotion("Invalid Discount", 110, game)
        );
        assertEquals("Invalid discount percentage!", exception.getMessage());
    }
    
    @Test
    public void testCreatePromotion_NullGame() {
        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            promotionService.createPromotion("No Game", 20, null)
        );
        assertEquals("Game cannot be null!", exception.getMessage());
    }
    
    @Test
    public void testUpdatePromotion_InvalidId() {
        when(promotionRepository.findPromotionById(999)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            promotionService.updatePromotion(999, "Invalid", 20, game)
        );
        assertEquals("Promotion does not exist!", exception.getMessage());
    }

    @Test
    public void testUpdatePromotion_InvalidDiscount() {
        when(promotionRepository.findPromotionById(promotion.getId())).thenReturn(promotion);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            promotionService.updatePromotion(promotion.getId(), "Invalid Discount", -10, game)
        );
        assertEquals("Invalid discount percentage!", exception.getMessage());
    }

    @Test
    public void testDeletePromotion_InvalidId() {
        when(promotionRepository.findById(999)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            promotionService.deletePromotion(999)
        );
        assertEquals("Promotion not found.", exception.getMessage());
    }

    @Test
    public void testGetPromotion_Valid() {
        when(promotionRepository.findPromotionById(promotion.getId())).thenReturn(promotion);

        Promotion retrievedPromotion = promotionService.getPromotion(promotion.getId());

        assertNotNull(retrievedPromotion);
        assertEquals(promotion, retrievedPromotion);
    }

    @Test
    public void testGetPromotion_InvalidId() {
        when(promotionRepository.findPromotionById(999)).thenReturn(null);

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            promotionService.getPromotion(999)
        );
        assertEquals("Promotion does not exist!", exception.getMessage());
    }

    @Test
    public void testGetAllPromotions_Valid() {
        List<Promotion> promotions = List.of(promotion);
        when(promotionRepository.findAll()).thenReturn(promotions);

        List<Promotion> retrievedPromotions = promotionService.getAllPromotions();

        assertNotNull(retrievedPromotions);
        assertEquals(1, retrievedPromotions.size());
        assertEquals(promotion, retrievedPromotions.get(0));
    }

    @Test
    public void testGetAllPromotions_Empty() {
        when(promotionRepository.findAll()).thenReturn(new ArrayList<>());

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
            promotionService.getAllPromotions()
        );
        assertEquals("No promotions found!", exception.getMessage());
    }



}
