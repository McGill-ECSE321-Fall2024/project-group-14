package ca.mcgill.ecse321_group14.GameShop.dto;

import java.time.LocalDate;

import ca.mcgill.ecse321_group14.GameShop.model.Order;

public class OrderResponseDto {
    
    private int orderId;
    private LocalDate date;
    private int customerId;
    private boolean isPaid;

    public OrderResponseDto() {
    }

    public OrderResponseDto(Order order) {
        this.customerId = order.getCustomer().getId();
        this.date = order.getOrderDate();
        this.orderId = order.getId();
        this.isPaid = order.getIsPaid();
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean isPaid) {
        this.isPaid = isPaid;
    }
    
}
