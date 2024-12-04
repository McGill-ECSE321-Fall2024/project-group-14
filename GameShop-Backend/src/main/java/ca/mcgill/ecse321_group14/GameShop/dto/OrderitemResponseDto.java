package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;

public class OrderitemResponseDto {
    private int orderitemId;
    private int orderId;
    private String gameTitle;
    private int priceAtPurchase;

    public OrderitemResponseDto() {
    }
    
    public OrderitemResponseDto(Orderitem orderitem) {
        this.orderitemId = orderitem.getKey().getGame().getId();
        this.orderId = orderitem.getKey().getOrder().getId();
        this.gameTitle = orderitem.getKey().getGame().getName();
        this.priceAtPurchase = orderitem.getPriceAtPurchase();
    }

    public int getOrderitemId() {
        return orderitemId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderitemId(int orderitemId) {
        this.orderitemId = orderitemId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getGameTitle(){
        return gameTitle;
    }

    public int getpriceAtPurchase(){
        return priceAtPurchase;
    }
    


}
