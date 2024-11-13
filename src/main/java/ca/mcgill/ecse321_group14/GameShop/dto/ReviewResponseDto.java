package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Review;

public class ReviewResponseDto {
    private int id;
    private String ranking;
    private String description;
    private int customerId;
    private int gameId;

    // Default constructor
    public ReviewResponseDto() {
    }

    // Parameterized constructor
    public ReviewResponseDto(int id, String ranking, String description, int customerId, int gameId) {
        this.id = id;
        this.ranking = ranking;
        this.description = description;
        this.customerId = customerId;
        this.gameId = gameId;
    }

    // Constructor to create DTO from Review object
    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.ranking = review.getRanking().name(); // Assuming Ranking is an enum
        this.description = review.getDescription();
        this.customerId = review.getCustomer().getId(); // Assuming Customer has a getId() method
        this.gameId = review.getGame().getId(); // Assuming Game has a getId() method
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = ranking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }
}