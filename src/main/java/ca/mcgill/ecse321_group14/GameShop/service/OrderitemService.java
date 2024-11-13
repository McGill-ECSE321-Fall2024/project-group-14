package ca.mcgill.ecse321_group14.GameShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;
import ca.mcgill.ecse321_group14.GameShop.repository.OrderitemRepository;
import jakarta.transaction.Transactional;

@Service
public class OrderitemService {
    
    @Autowired
    private OrderitemRepository orderitemRepository;

    @Transactional
    public Orderitem createOrderitem(Game game, Order order) {
        Orderitem orderitem = finOrderitem(order, game);
        if (orderitem != null) {
            return orderitem;
        }
        else{
            Orderitem newOrderitem = new Orderitem(new Orderitem.Key(game, order));
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
}
