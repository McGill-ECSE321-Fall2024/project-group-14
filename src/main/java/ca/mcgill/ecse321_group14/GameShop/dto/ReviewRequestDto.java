package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Review.Ranking;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.model.Customer; //is this allowed?

public class ReviewRequestDto{
    private Ranking ranking;
    private String description;
    private int customerId;
    private int gameId;

    public ReviewRequestDto() {
    }

    public ReviewRequestDto(Ranking ranking, String description, int customerId, int gameId) {
        this.ranking = ranking;
        this.description = description;
        this.customerId = customerId;
        this.gameId = gameId;
    }

    public Ranking getRanking() {
        return ranking;
    }

    public void setRanking(String ranking) {
        this.ranking = Ranking.valueOf(ranking);
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