package ca.mcgill.ecse321_group14.GameShop.dto;


public class OrderRequestDto {

    private int customerId;


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public OrderRequestDto() {
    }

    public OrderRequestDto(int customerId) {
        this.customerId = customerId;
    }

    

}
