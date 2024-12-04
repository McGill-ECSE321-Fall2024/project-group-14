package ca.mcgill.ecse321_group14.GameShop.service;

import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Promotion;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.PromotionRepository;
import jakarta.transaction.Transactional;
@Service
public class PromotionService {
    @Autowired
    PromotionRepository promotionRepository;
    @Autowired
    GameRepository gameRepository;



    @Transactional
    public void deletePromotion(int id) {
        Promotion promotion = promotionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Promotion not found."));

        Game game = promotion.getGame();
        game.setDiscountedprice(0);; // Reset to original price
        gameRepository.save(game);

        promotionRepository.delete(promotion);
    }

    @Transactional
    public Promotion getPromotion(int id) {
        Promotion promotion = promotionRepository.findPromotionById(id);
        if (promotion == null) {
            throw new IllegalArgumentException("Promotion does not exist!");
        }
        return promotion;
    }

    @Transactional
    public Promotion updatePromotion(int id, String description, int discount, Game game) {
        Promotion promotion = promotionRepository.findPromotionById(id);
        if (promotion == null) {
            throw new IllegalArgumentException("Promotion does not exist!");
        }
    
        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Invalid discount percentage!");
        }
    
        // Update promotion details
        promotion.setDescription(description);
        promotion.setDiscount(discount);
        promotion.setGame(game);
    
        if (game != null) {
            // Calculate the new discounted price and update the Game entity
            int discountedPrice = (int)(game.getPrice() * (1 - discount / 100.0));
            game.setDiscountedprice(discountedPrice);
            gameRepository.save(game);
        }
    
        return promotionRepository.save(promotion);
    }
    @Transactional
    public Promotion createPromotion(String description, int discount, Game game) {
        if (game == null) {
            throw new IllegalArgumentException("Game cannot be null!");
        }

        if (discount < 0 || discount > 100) {
            throw new IllegalArgumentException("Invalid discount percentage!");
        }

        // Calculate the discounted price
        int discountedPrice = (int) (game.getPrice() * (1 - discount / 100.0));

        // Update the discounted price in the Game entity
        game.setDiscountedprice(discountedPrice);
        gameRepository.save(game); // Save the updated Game entity

        // Create and save the promotion
        Promotion promotion = new Promotion(description, discount, game);
        return promotionRepository.save(promotion);
    }
    @Transactional
    public List<Promotion> getAllPromotions(){
       List<Promotion> promotions = promotionRepository.findAll();
       if(promotions.isEmpty()){
           throw new IllegalArgumentException("No promotions found!");
       }
         return promotions;
    }
}
