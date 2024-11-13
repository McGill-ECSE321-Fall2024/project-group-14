package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Game.Rating;
import java.util.List;

public class GameDto {

    private int id;
    private String name;
    private String description;
    private String category;
    private int price;
    private int quantity;
    private Rating rating;
    private String picture;
    private Integer promotionId;
    private List<Integer> reviewIds;

    // Default constructor
    public GameDto(){}

    // Parameterized constructor
    public GameDto(int id, String name, String description, String category, int price, int quantity,
                   Rating rating, String picture, Integer promotionId, List<Integer> reviewIds) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.rating = rating;
        this.picture = picture;
        this.promotionId = promotionId;
        this.reviewIds = reviewIds;
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

    public List<Integer> getReviewIds() {
        return reviewIds;
    }

    public void setReviewIds(List<Integer> reviewIds) {
        this.reviewIds = reviewIds;
    }


}
