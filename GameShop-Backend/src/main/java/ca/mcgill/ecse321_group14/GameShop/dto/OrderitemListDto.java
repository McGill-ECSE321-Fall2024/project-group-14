package ca.mcgill.ecse321_group14.GameShop.dto;

import java.util.List;


public class OrderitemListDto {
    private List<OrderitemResponseDto> orderitems;

    public OrderitemListDto(List<OrderitemResponseDto> orderitems) {
        this.orderitems = orderitems;
    }

    public List<OrderitemResponseDto> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(List<OrderitemResponseDto> orderitems) {
        this.orderitems = orderitems;
    }

    public OrderitemListDto() {
    }

}
