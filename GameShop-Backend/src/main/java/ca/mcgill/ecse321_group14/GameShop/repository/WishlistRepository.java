package ca.mcgill.ecse321_group14.GameShop.repository;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321_group14.GameShop.model.Wishlist;
import ca.mcgill.ecse321_group14.GameShop.model.Wishlist.Key;

import java.util.List;

public interface WishlistRepository extends CrudRepository<Wishlist, Wishlist.Key> {
    Wishlist findWishlistByKey(Wishlist.Key key);
    List<Wishlist> findByKey_Buyer(Customer customer);
    
}
