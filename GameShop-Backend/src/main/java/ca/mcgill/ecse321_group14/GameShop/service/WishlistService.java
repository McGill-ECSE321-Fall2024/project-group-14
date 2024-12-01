package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Wishlist;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class WishlistService {
    @Autowired
    WishlistRepository wishlistRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private GameRepository gameRepository;


    @Transactional
    public Wishlist createWishlist(Game game, Customer customer) {
        Wishlist wishlist = wishlistRepository.findWishlistByKey(new Wishlist.Key(game, customer));

        if (wishlist != null) {
            throw new IllegalArgumentException("Wishlist already exists!");
        }

        wishlist = new Wishlist(new Wishlist.Key(game, customer));
        wishlistRepository.save(wishlist);
        return wishlist;
    }


    @Transactional
    public void clearWishlist(Customer customer, Game game) {

        Wishlist wishlist = wishlistRepository.findWishlistByKey(new Wishlist.Key(game, customer));
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist does not exist!");
        }
        wishlistRepository.delete(wishlist);
    }
    @Transactional
    public Wishlist getWishlist(Game game, Customer customer) {
        Wishlist wishlist = wishlistRepository.findWishlistByKey(new Wishlist.Key(game, customer));
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist does not exist!");
        }
        return wishlist;
    }

    @Transactional
    public List<Wishlist> findWishlistsByCustomerId(int customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new IllegalArgumentException("Customer does not exist!"));
        return wishlistRepository.findByKey_Buyer(customer);

    }


}
