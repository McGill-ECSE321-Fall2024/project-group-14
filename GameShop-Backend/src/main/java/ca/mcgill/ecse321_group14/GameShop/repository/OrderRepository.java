package ca.mcgill.ecse321_group14.GameShop.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321_group14.GameShop.model.Order;
public interface OrderRepository extends CrudRepository<Order, Integer> {
    Order findOrderById(int id);
    void deleteOrderById(int id);
    List<Order> findOrderByCustomerId(int customerId);
    
    
}
