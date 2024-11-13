package ca.mcgill.ecse321_group14.GameShop.dto;

public class ReviewRequestDto {
    private String ranking;
    private String description;
    private int customerId;
    private int gameId;

    // Default constructor
    public ReviewRequestDto() {
    }

    // Parameterized constructor
    public ReviewRequestDto(String ranking, String description, int customerId, int gameId) {
        this.ranking = ranking;
        this.description = description;
        this.customerId = customerId;
        this.gameId = gameId;
    }

    // Getters and Setters
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