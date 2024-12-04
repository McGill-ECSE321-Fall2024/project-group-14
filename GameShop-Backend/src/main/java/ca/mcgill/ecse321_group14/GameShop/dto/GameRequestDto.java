package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Person;


public class GameRequestDto {
    private int id;
    //@NotNull(message = "Name cannot be empty")
    private String name;
    //@NotNull(message = "Description cannot be empty")
    private String description;
//    @Notnull(message = "Category cannot be empty")
    private String category;
//    @NotNull(message = "Price cannot be empty")
    private Integer price;
//    @NotNull(message = "Quantity cannot be empty")
    private Integer quantity;
//    @NotNull(message = "Rating cannot be empty")
    private Game.Rating rating;
//    @NotNull(message = "Picture cannot be empty")
    private String picture;

    private Integer discountedprice;

    private Integer personId;

    public GameRequestDto() {
    }

    public GameRequestDto(String name, Integer personId) {
        this.name = name;
        this.personId = personId;
    }

    public GameRequestDto(Integer id, String name, String description, String category, Integer price, Integer quantity, Game.Rating rating, String picture) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.rating = rating;
        this.picture = picture;
    }

    public GameRequestDto(String name, String description, String category, Integer price, Integer quantity, Game.Rating rating, String picture) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.rating = rating;
        this.picture = picture;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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

    public Integer getDiscountedprice() {
        return discountedprice;
    }

    public void setDiscountedprice(Integer discountedprice) {
        this.discountedprice = discountedprice;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Game.Rating getRating() {
        return rating;
    }

    public void setRating(Game.Rating rating) {
        this.rating = rating;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}




