package ca.mcgill.ecse321_group14.GameShop.dto;

public class WishlistDto{

    private Integer gameId;
    private Integer customerId;

    // Default constructor
    public WishlistDto() {}

    // Parameterized constructor
    public WishlistDto(Integer gameId, Integer customerId) {
        this.gameId = gameId;
        this.customerId = customerId;
    }

    // Getters and Setters
    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

}
