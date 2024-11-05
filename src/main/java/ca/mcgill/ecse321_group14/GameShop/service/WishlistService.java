package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Wishlist;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;
    @Autowired
    private CustomerRepository customerRepository;


    @Transactional
    public void addGameToWishlist(Wishlist.Key id, Game game, Customer customer) {
        Wishlist wishlist = wishlistRepository.findById(id).orElse(null);

        String error = "";
        if (!customerRepository.existsById(customer.getId())) {
            error += "Customer does not exist!";
        }
        if (wishlist == null) {
            wishlist = new Wishlist(id);
        }
        Wishlist.Key key = new Wishlist.Key(game, customer);
        wishlistRepository.save(wishlist);
    }
    @Transactional
    public void deleteGameFromWishlist(Wishlist.Key id, Game game, Customer customer) {
        Wishlist wishlist = wishlistRepository.findById(id).orElse(null);
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist does not exist!");
        }
        Wishlist.Key key = new Wishlist.Key(game, customer);
        wishlistRepository.deleteById(key);
    }


}
