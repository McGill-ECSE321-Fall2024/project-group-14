package ca.mcgill.ecse321_group14.GameShop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321_group14.GameShop.dto.OrderListDto;
import ca.mcgill.ecse321_group14.GameShop.dto.OrderRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.OrderResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.service.OrderService;
@CrossOrigin(origins = "*")
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
            if (order == null) {
                // Log the issue and return a 404 response
                System.err.println("Order with ID " + id + " not found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            return ResponseEntity.ok(new OrderResponseDto(order)); // 200 OK with order data
        } catch (IllegalArgumentException e) {
            // Log the exception and return a 404 response
            System.err.println("Error retrieving order: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            // Catch unexpected exceptions and return a 500 response
            System.err.println("Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
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

    /**
     * Get all orders with a specific customer id
     * 
     * @param customerId
     * @return ResponseEntity<List<OrderResponseDto>>
     * 
     */
    @GetMapping("/order/customer/{customerId}")
    public ResponseEntity<OrderListDto> getOrderByCustomerId(@PathVariable int customerId){
        try {
           List<Order> orders = orderService.findOrderByCustomerId(customerId);
           List<OrderResponseDto> dtos = orders.stream().map(order -> new OrderResponseDto(order)).collect(Collectors.toList());
           return ResponseEntity.ok(new OrderListDto(dtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    /**
     * Mark an order as paid
     *
     * @param orderId
     * @return ResponseEntity<OrderResponseDto>
     */
    @PutMapping("/order/{orderId}/pay")
    public ResponseEntity<OrderResponseDto> markOrderAsPaid(@PathVariable int orderId) {
        Order updatedOrder = orderService.markOrderAsPaid(orderId);
        return ResponseEntity.ok(new OrderResponseDto(updatedOrder));
    }

    /**
     * Refund an order
     *
     * @param orderId
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/order/{orderId}/refund")
    public ResponseEntity<Void> refundOrder(@PathVariable int orderId) {
        try {
            orderService.refundOrder(orderId);
            return ResponseEntity.noContent().build(); // 204 No Content
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400 Bad Request for invalid refund
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found if the order doesn't exist
        }
    }
}
