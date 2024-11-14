package ca.mcgill.ecse321_group14.GameShop.service;

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
        Promotion promotion = promotionRepository.findPromotionById(id);
        if (promotion == null) {
            throw new IllegalArgumentException("Promotion does not exist!");
        }
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
    public Promotion updatePromotion(int id, String aDescription, int aDiscount, Game aGame) {
        Promotion promotion = promotionRepository.findPromotionById(id);
        if (promotion == null) {
            throw new IllegalArgumentException("Promotion does not exist!");
        }
        try {
            promotion.setDescription(aDescription);
            promotion.setDiscount(aDiscount);
            promotion.setGame(aGame);
            promotionRepository.save(promotion);
            return promotion;
        }
        catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
    @Transactional
    public Promotion createPromotion(String aDescription, int aDiscount, Game aGame) {
        String error = "";
        if (gameRepository.findGameById(aGame.getId()) == null) {
            error += "Game does not exist!";
        }
        if (!error.isEmpty()) {
            throw new IllegalArgumentException(error);
        }
        try {
            Promotion promotion = new Promotion(aDescription, aDiscount, aGame);
            promotionRepository.save(promotion);
            return promotion;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }


}
