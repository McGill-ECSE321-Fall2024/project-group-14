package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;

public class OrderitemResponseDto {
    private int orderitemId;
    private int orderId;

    public OrderitemResponseDto(Orderitem orderitem) {
        this.orderitemId = orderitem.getKey().getGame().getId();
        this.orderId = orderitem.getKey().getOrder().getId();
    }

    public int getOrderitemId() {
        return orderitemId;
    }

    public int getOrderId() {
        return orderId;
    }
}
