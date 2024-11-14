package ca.mcgill.ecse321_group14.GameShop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321_group14.GameShop.dto.OrderitemListDto;
import ca.mcgill.ecse321_group14.GameShop.dto.OrderitemResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;
import ca.mcgill.ecse321_group14.GameShop.service.GameService;
import ca.mcgill.ecse321_group14.GameShop.service.OrderService;
import ca.mcgill.ecse321_group14.GameShop.service.OrderitemService;

@RestController
public class OrderitemController {
    
    @Autowired
    private OrderitemService orderitemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private GameService gameService;

    /**
     * Create an orderitem
     *
     * @param orderId
     * @param gameId
     * @return ResponseEntity<OrderitemResponseDto>
     */
    @PutMapping("/orderitem/{orderId}/{gameId}")
    public ResponseEntity<OrderitemResponseDto> createOrderitem(@PathVariable int orderId, @PathVariable int gameId) {
        try {
            Order order = orderService.getOrder(orderId);
            Game game = gameService.getGameById(gameId);
            Orderitem orderitem = orderitemService.createOrderitem(game, order);
            return ResponseEntity.status(HttpStatus.CREATED).body(new OrderitemResponseDto(orderitem));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // Returns 400 for bad requests
        }
    }

    /**
     * Delete an orderitem
     *
     * @param orderId
     * @param gameId
     * @return ResponseEntity<Void>
     */
    @DeleteMapping("/orderitem/{orderId}/{gameId}")
    public ResponseEntity<Void> deleteOrderitem(@PathVariable int orderId, @PathVariable int gameId) {
        try {
            Order order = orderService.getOrder(orderId);
            Game game = gameService.getGameById(gameId);
            orderitemService.deleteOrderitem(game, order);
            return ResponseEntity.noContent().build(); // Returns 204 No Content on successful deletion
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 if not found
        }
    }

    /**
     * Get an orderitem
     *
     * @param orderId
     * @param gameId
     * @return ResponseEntity<OrderitemResponseDto>
     */
    @GetMapping("/orderitem/{orderId}/{gameId}")
    public ResponseEntity<OrderitemResponseDto> getOrderitem(@PathVariable int orderId, @PathVariable int gameId) {
        try {
            Order order = orderService.getOrder(orderId);
            Game game = gameService.getGameById(gameId);
            Orderitem orderitem = orderitemService.finOrderitem(order, game);
            return ResponseEntity.ok(new OrderitemResponseDto(orderitem)); // Returns 200 OK if found
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 if not found
        }
    }

    /**
     * Get all orderitems in an order
     *
     * @param orderId
     * @return ResponseEntity<OrderitemListDto>
     */
    @GetMapping("/orderitems/{orderId}")
    public ResponseEntity<OrderitemListDto> getOrderitems(@PathVariable int orderId) {
        try {
            List<Orderitem> orderitems = orderitemService.findOrderItemsByOrderId(orderId);
            List<OrderitemResponseDto> orderitemResponseDtos = orderitems.stream()
                .map(OrderitemResponseDto::new)
                .collect(Collectors.toList());
            return ResponseEntity.ok(new OrderitemListDto(orderitemResponseDtos)); // Returns 200 OK with list of items
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 if orderId not found
        }
    }
}
