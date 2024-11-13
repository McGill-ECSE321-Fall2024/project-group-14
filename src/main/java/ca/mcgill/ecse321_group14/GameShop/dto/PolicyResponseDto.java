package ca.mcgill.ecse321_group14.GameShop.dto;

public class PolicyResponseDto {
    private int policyId;
    private String description;

    public PolicyResponseDto(){
    }

    public PolicyResponseDto(int policyId, String description) {
        this.policyId = policyId;
        this.description = description;
    }

    public PolicyResponseDto(ca.mcgill.ecse321_group14.GameShop.model.Policy policy) {
        this.policyId = policy.getPolicyId();
        this.description = policy.getDescription();
    }

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