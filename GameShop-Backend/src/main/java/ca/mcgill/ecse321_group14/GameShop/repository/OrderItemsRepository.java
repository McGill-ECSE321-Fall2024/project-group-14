package ca.mcgill.ecse321_group14.GameShop.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321_group14.GameShop.model.OrderItems;

public interface OrderItemsRepository extends CrudRepository<OrderItems, OrderItems.Key> {
    OrderItems findOrderItemsByKey(OrderItems.Key key);
    
}
