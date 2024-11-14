package ca.mcgill.ecse321_group14.GameShop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
     * @return OrderitemResponseDto
     */
    @PutMapping("/orderitem/{orderId}/{gameId}")
    public OrderitemResponseDto createOrderitem(@PathVariable int orderId, @PathVariable int gameId) {
        Order order = orderService.getOrder(orderId);
        Game game = gameService.getGameById(gameId);
        Orderitem orderitem = orderitemService.createOrderitem(game, order);
        return new OrderitemResponseDto(orderitem);
    }

    /**
     * Delete an orderitem
     * 
     * @param orderId
     * @param gameId
     */

    @DeleteMapping("/orderitem/{orderId}/{gameId}")
    public void deleteOrderitem(@PathVariable int orderId, @PathVariable int gameId) {
        Order order = orderService.getOrder(orderId);
        Game game = gameService.getGameById(gameId);
        orderitemService.deleteOrderitem(game, order);
    }

    /**
     * Get an orderitem
     * 
     * @param orderId
     * @param gameId
     * @return OrderitemResponseDto
     */
    @GetMapping("/orderitem/{orderId}/{gameId}")
    public OrderitemResponseDto getOrderitem(@PathVariable int orderId, @PathVariable int gameId) {
        Order order = orderService.getOrder(orderId);
        Game game = gameService.getGameById(gameId);
        Orderitem orderitem = orderitemService.finOrderitem(order, game);
        return new OrderitemResponseDto(orderitem);
    }

    /**
     * Get all orderitems in an order
     * 
     * @param orderId
     * @return List<OrderitemResponseDto>
     * 
     */
    @GetMapping("/orderitems/{orderId}")
    public OrderitemListDto getOrderitems(@PathVariable int orderId) {
        List<Orderitem> orderitems = orderitemService.findOrderItemsByOrderId(orderId);
        List<OrderitemResponseDto> orderitemResponseDtos = orderitems.stream().map(OrderitemResponseDto::new).collect(Collectors.toList());
        return new OrderitemListDto(orderitemResponseDtos);
    }
}
