package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Game.Rating;
import ca.mcgill.ecse321_group14.GameShop.model.Review;

import java.util.List;

public class GameResponseDto {

    private int id;
    private String name;
    private String description;
    private String category;
    private int price;
    private int quantity;
    private Rating rating;
    private String picture;
    private Integer promotionId;
    private List<Review> reviews;

    // Default constructor
    public GameResponseDto() {}

    // Parameterized constructor
    public GameResponseDto(Game game) {
        this.id = game.getId();
        this.name = game.getName();
        this.description = game.getDescription();
        this.category = game.getCategory();
        this.price = game.getPrice();
        this.quantity = game.getQuantity();
        this.rating = game.getRating();
        this.picture = game.getPicture();
        this.promotionId = (game.getPromotion() != null) ? game.getPromotion().getPromotionId() : null;
        this.reviews = game.getReviews();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Integer getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Integer promotionId) {
        this.promotionId = promotionId;
    }

    public List<Review> getReviews() {
        return reviews;
    }




}
