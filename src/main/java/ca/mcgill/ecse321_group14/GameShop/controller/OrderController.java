package ca.mcgill.ecse321_group14.GameShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
     * @return ResponseEntity<OrderResponseDto>
     */
    @PostMapping("/order")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequestDto) {
        Order order = orderService.createOrder(orderRequestDto.getCustomerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderResponseDto(order));
    }

    /**
     * Get an order by id
     *
     * @param id
     * @return ResponseEntity<OrderResponseDto>
     */
    @GetMapping("/order/{id}")
    public ResponseEntity<OrderResponseDto> getOrder(@PathVariable int id) {
        try {
            Order order = orderService.getOrder(id);
            return ResponseEntity.ok(new OrderResponseDto(order)); // 200 OK with order data
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found if order doesn't exist
        }
    }

    /**
     * Delete an order by id
     *
     * @param id
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build(); // 204 No Content on successful deletion
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found if order doesn't exist
        }
    }

    /**
     * Delete all orders
     *
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/order/deleteAll")
    public ResponseEntity<Void> deleteAllOrders() {
        orderService.deleteAllOrders();
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
