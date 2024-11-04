package ca.mcgill.ecse321_group14.GameShop.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321_group14.GameShop.model.Orderitem;

public interface OrderitemRepository extends CrudRepository<Orderitem, Orderitem.Key> {
    Orderitem findOrderitemByKey(Orderitem.Key key);
    
}
