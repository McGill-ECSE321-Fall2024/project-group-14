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
    public Wishlist addGameToWishlist(Game game, Customer customer) {
        Wishlist wishlist = wishlistRepository.findWishlistByKey(new Wishlist.Key(game, customer));

        String error = "";
        if (!customerRepository.existsById(customer.getId())) {
            error += "Customer does not exist!";
        }
        if (wishlist == null) {
            wishlist = new Wishlist(new Wishlist.Key(game, customer));
        }
        Wishlist.Key key = new Wishlist.Key(game, customer);
        wishlistRepository.save(wishlist);
        return wishlist;
    }
    @Transactional
    public Wishlist deleteGameFromWishlist(Game game, Customer customer) {
        Wishlist wishlist = wishlistRepository.findWishlistByKey(new Wishlist.Key(game, customer));
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist does not exist!");
        }
        Wishlist.Key key = new Wishlist.Key(game, customer);
        wishlistRepository.deleteById(key);
        return wishlist;
    }
    @Transactional
    public void clearWishlist(Customer customer, Game game) {

        Wishlist wishlist = wishlistRepository.findWishlistByKey(new Wishlist.Key(game, customer));
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist does not exist!");
        }
        wishlistRepository.deleteAll();
    }
    @Transactional
    public Wishlist getWishlist(Game game, Customer customer) {
        Wishlist wishlist = wishlistRepository.findWishlistByKey(new Wishlist.Key(game, customer));
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist does not exist!");
        }
        return wishlist;
    }


}
