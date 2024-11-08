package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Review.Ranking;

public class ReviewDto {

    private int id;
    private Ranking ranking;
    private String description;
    private Integer customerId;
    private Integer gameId;

    // Default constructor
    public ReviewDto() {}

    // Parameterized constructor
    public ReviewDto(int id, Ranking ranking, String description, Integer customerId, Integer gameId) {
        this.id = id;
        this.ranking = ranking;
        this.description = description;
        this.customerId = customerId;
        this.gameId = gameId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(Ranking ranking) {
        this.ranking = ranking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }


}
