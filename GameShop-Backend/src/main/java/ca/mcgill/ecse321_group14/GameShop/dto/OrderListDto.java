package ca.mcgill.ecse321_group14.GameShop.dto;

import java.util.List;
public class OrderListDto {
    public OrderListDto() {
    }
    private List<OrderResponseDto> orders;

    public OrderListDto(List<OrderResponseDto> orders) {
        this.orders = orders;
    }

    public List<OrderResponseDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderResponseDto> orders) {
        this.orders = orders;
    }
    
}
