package ca.mcgill.ecse321_group14.GameShop.dto;

public class PromotionDto {

    private int id;
    private String description;
    private int discount;
    private Integer gameId;

    // Default constructor
    public PromotionDto() {}

    // Parameterized constructor
    public PromotionDto(int id, String description, int discount, Integer gameId) {
        this.id = id;
        this.description = description;
        this.discount = discount;
        this.gameId = gameId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    @Override
    public String toString() {
        return "PromotionDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", discount=" + discount +
                ", gameId=" + gameId +
                '}';
    }
}
