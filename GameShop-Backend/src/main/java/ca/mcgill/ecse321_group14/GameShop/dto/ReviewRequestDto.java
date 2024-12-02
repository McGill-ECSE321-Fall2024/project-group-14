package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Review.Ranking;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.model.Customer; //is this allowed?

public class ReviewRequestDto {
    private Ranking ranking;
    private String description;
    private int customerId;
    private int gameId;

    public ReviewRequestDto() {
    }

    public ReviewRequestDto(String ranking, String description, int customerId, int gameId) {
        if (ranking.equals("FiveStar")) {
            this.ranking = Ranking.FiveStar;
        } else if (ranking.equals("FourStar")) {
            this.ranking = Ranking.FourStar;
        } else if (ranking.equals("ThreeStar")) {
            this.ranking = Ranking.ThreeStar;
        } else if (ranking.equals("TwoStar")) {
            this.ranking = Ranking.TwoStar;
        } else if (ranking.equals("OneStar")) {
            this.ranking = Ranking.OneStar;
        }
        this.description = description;
        this.customerId = customerId;
        this.gameId = gameId;
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