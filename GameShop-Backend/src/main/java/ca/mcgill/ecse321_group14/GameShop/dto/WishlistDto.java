package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Wishlist;

public class WishlistDto {

    private Integer gameId;
    private Integer customerId;
    private String gameName;
    private int price;

    // Default constructor
    public WishlistDto() {}

    // Parameterized constructor
    public WishlistDto(Integer gameId, Integer customerId, String gameName, int price) {
        this.gameId = gameId;
        this.customerId = customerId;
        this.gameName = gameName;
    }
    public WishlistDto(Wishlist wishlist){
        this.gameId = wishlist.getKey().getWish().getId();
        this.customerId = wishlist.getKey().getBuyer().getId();
        this.gameName = wishlist.getKey().getWish().getName();
        this.price = wishlist.getKey().getWish().getPrice();
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

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
