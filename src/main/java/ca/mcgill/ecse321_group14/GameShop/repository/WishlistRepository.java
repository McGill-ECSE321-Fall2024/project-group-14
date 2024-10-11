package ca.mcgill.ecse321_group14.GameShop.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321_group14.GameShop.model.Wishlist;
import ca.mcgill.ecse321_group14.GameShop.model.Wishlist.Key;

public interface WishlistRepository extends CrudRepository<Wishlist, Wishlist.Key> {
    Wishlist findWishlistByKey(Key key);
    
}
