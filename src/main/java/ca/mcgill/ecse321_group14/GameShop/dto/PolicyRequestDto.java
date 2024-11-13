package ca.mcgill.ecse321_group14.GameShop.dto;
import ca.mcgill.ecse321_group14.GameShop.model.Policy;


public class PolicyRequestDto {
    private String description;

    public PolicyRequestDto(){
    }

    public PolicyRequestDto(String description) {
        this.description = description;
    }

    public Policy toModel(){
        Policy policy = new Policy();
        policy.setDescription(this.description);
        return policy;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}