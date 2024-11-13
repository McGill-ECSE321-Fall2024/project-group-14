package ca.mcgill.ecse321_group14.GameShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * Delete an order by id
     * 
     * @param id
     */
    @GetMapping("/order/delete/{id}")
    public void deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
    }

    /**
     * Delete all orders
     */
    @GetMapping("/order/deleteAll")
    public void deleteAllOrders() {
        orderService.deleteAllOrders();
    }

    

}
