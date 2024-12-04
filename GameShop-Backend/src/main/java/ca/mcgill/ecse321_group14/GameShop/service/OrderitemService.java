package ca.mcgill.ecse321_group14.GameShop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderitemRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderitemService {
    
    @Autowired
    private OrderitemRepository orderitemRepository;
    @Autowired
    private OrderRepository orderRepository;
    

    @Transactional
    public Orderitem createOrderitem(Game game, Order order) {
        
        Orderitem orderitem = finOrderitem(order, game);
        if (orderitem != null) {
            return orderitem;
        }
        else{
            Orderitem newOrderitem = new Orderitem(new Orderitem.Key(game, order));
            System.out.println("Game Name: " + game.getName());
            System.out.println("Game Discounted Price: " + game.getDiscountedprice());
            System.out.println("Game Original Price: " + game.getPrice());
            if (game.getDiscountedprice() != 0){
                newOrderitem.setPriceAtPurchase(game.getDiscountedprice());
            } else {
                newOrderitem.setPriceAtPurchase(game.getPrice());
            };
            return orderitemRepository.save(newOrderitem);
        }
    }

    @Transactional
    public void deleteOrderitem(Game game, Order order) {
        Orderitem orderitem = finOrderitem(order, game);
        if (orderitem == null) {
            throw new IllegalArgumentException("Orderitem does not exist!");
        }
        orderitemRepository.delete(orderitem);
    }

    public Orderitem finOrderitem(Order order, Game game) {
        Orderitem orderitem = orderitemRepository.findOrderitemByKey(new Orderitem.Key(game, order));
        return orderitem;
    }

    @Transactional
    public Orderitem getOrderitem(Game game, Order order) {
        Orderitem orderitem = finOrderitem(order, game);
        if (orderitem == null) {
            throw new IllegalArgumentException("Orderitem does not exist!");
        }
        return orderitem;
    }

    @Transactional
    public List<Orderitem> findOrderItemsByOrderId(int orderid){
        Order order = orderRepository.findOrderById(orderid);
        return orderitemRepository.findByKey_Order(order);
    }

}
