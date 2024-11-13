package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Promotion;

public class PromotionResponseDto{
    private int id;
    private String description;
    private int discount;
    private int gameId;

    public PromotionResponseDto() {
    }

    public PromotionResponseDto(int id, String description, int discount, int gameId) {
        this.id = id;
        this.description = description;
        this.discount = discount;
        this.gameId = gameId;
    }

    public PromotionResponseDto(Promotion promotion) {
        this.id = promotion.getPromotionId();
        this.description = promotion.getDescription();
        this.discount = promotion.getDiscount();
        this.gameId = promotion.getGame().getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}