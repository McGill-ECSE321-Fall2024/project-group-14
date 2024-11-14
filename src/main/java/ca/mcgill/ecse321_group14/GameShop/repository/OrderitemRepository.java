package ca.mcgill.ecse321_group14.GameShop.repository;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321_group14.GameShop.model.Order;
import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;

public interface OrderitemRepository extends CrudRepository<Orderitem, Orderitem.Key> {
    Orderitem findOrderitemByKey(Orderitem.Key key);

    void deleteOrderitemByKey(Orderitem.Key key);

    List<Orderitem> findByKey_Order(Order order);
}
