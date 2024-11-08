package ca.mcgill.ecse321_group14.GameShop.dto;

public class PolicyDto {

    private int policyId;
    private String description;

    // Default constructor
    public PolicyDto() {}

    // Parameterized constructor
    public PolicyDto(int policyId, String description) {
        this.policyId = policyId;
        this.description = description;
    }

    // Getters and Setters
    public int getPolicyId() {
        return policyId;
    }

    public void setPolicyId(int policyId) {
        this.policyId = policyId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
