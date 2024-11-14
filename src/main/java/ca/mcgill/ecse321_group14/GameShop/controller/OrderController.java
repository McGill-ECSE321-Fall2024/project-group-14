package ca.mcgill.ecse321_group14.GameShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321_group14.GameShop.dto.OrderRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.OrderResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.service.OrderService;

@RestController
public class OrderController {
    
    @Autowired
    private OrderService orderService;

    /**
     * Create an order
     * 
     * @param orderRequestDto
     * @return OrderResponseDto
     */
    @PostMapping("/order")
    @ResponseStatus(org.springframework.http.HttpStatus.CREATED)
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Order order = orderService.createOrder(orderRequestDto.getCustomerId());
        return new OrderResponseDto(order);
    }

    /**
     * Get an order by id
     * 
     * @param id
     * @return OrderResponseDto
     */
    @GetMapping("/order/{id}")
    public OrderResponseDto getOrder(@PathVariable int id) {
        Order order = orderService.getOrder(id);
        return new OrderResponseDto(order);
    }

  
    @DeleteMapping("/order/{id}")
    public OrderResponseDto deleteOrder(@PathVariable int id) {
        Order order = orderService.getOrder(id);
        orderService.deleteOrder(id);
        return new OrderResponseDto(order);
    }

    /**
     * Delete all orders
     */
    @DeleteMapping("/order/deleteAll")
    public void deleteAllOrders() {
        orderService.deleteAllOrders();
    }

    

}

