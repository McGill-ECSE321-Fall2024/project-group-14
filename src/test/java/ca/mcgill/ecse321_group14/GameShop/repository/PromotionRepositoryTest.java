package ca.mcgill.ecse321_group14.GameShop.repository;

import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Promotion;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class PromotionRepositoryTest {
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    GameRepository gameRepository;

    @BeforeEach
    @AfterEach
    public void ClearDatabase(){
        promotionRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadPromotion(){
        Game game = new Game("Mario", null, null, 0, 0, null, null );
        game = gameRepository.save(game);
        Promotion promotion = new Promotion("description", 0, game);
        promotion = promotionRepository.save(promotion);

        int id = promotion.getPromotionId();

        Promotion readPromotion = promotionRepository.findPromotionById(id);

        assertNotNull(readPromotion);
        assertEquals(promotion.getPromotionId(), readPromotion.getPromotionId());
        assertEquals(promotion.getGame().getId(), readPromotion.getGame().getId());
        assertEquals(promotion.getDescription(), readPromotion.getDescription());
        assertEquals(promotion.getDiscount(), readPromotion.getDiscount());

    }

}
