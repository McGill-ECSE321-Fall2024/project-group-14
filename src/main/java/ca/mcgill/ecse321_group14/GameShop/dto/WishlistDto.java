package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Wishlist;

public class WishlistDto {

    private Integer gameId;
    private Integer customerId;

    // Default constructor
    public WishlistDto() {}

    // Parameterized constructor
    public WishlistDto(Integer gameId, Integer customerId) {
        this.gameId = gameId;
        this.customerId = customerId;
    }
    public WishlistDto(Wishlist wishlist){
        this.gameId = wishlist.getKey().getWish().getId();
        this.customerId = wishlist.getKey().getBuyer().getId();
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
