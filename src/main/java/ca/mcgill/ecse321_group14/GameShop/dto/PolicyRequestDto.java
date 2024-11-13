package ca.mcgill.ecse321_group14.GameShop.dto;

public class PolicyRequestDto {
    private String description;

    // Default constructor
    public PolicyRequestDto() {
    }

    // Parameterized constructor
    public PolicyRequestDto(String description) {
        this.description = description;
    }

    // Getters and Setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}