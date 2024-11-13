package ca.mcgill.ecse321_group14.GameShop.dto;

public class PromotionRequestDto {
    private String description;
    private int discount;
    private int gameId;

    // Default constructor
    public PromotionRequestDto() {
    }

    // Parameterized constructor
    public PromotionRequestDto(String description, int discount, int gameId) {
        this.description = description;
        this.discount = discount;
        this.gameId = gameId;
    }

    // Getters and Setters
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