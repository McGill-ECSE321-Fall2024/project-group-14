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
    public Wishlist addGameToWishlist(Game game, Customer customer) {
        Wishlist wishlist = wishlistRepository.findWishlistByKey(new Wishlist.Key(game, customer));
        if (game == null){
            throw new IllegalArgumentException("Game does not exist!");
        }
        if (customer == null){
            throw new IllegalArgumentException("Customer does not exist!");
        }
        if (!customerRepository.existsById(customer.getId())) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        if (!gameRepository.existsById(game.getId())){
            throw new IllegalArgumentException("Game does not exist!");
        }
        if (wishlist == null) {
            throw new IllegalArgumentException("Wishlist does not exist!");
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
        if(!(wishlist.getKey().getWish().getName().equals(game.getName()))){
            throw new IllegalArgumentException("Game is not in the wishlist!");
        }
        if (!(wishlist.getKey().getBuyer().getUsername().equals(customer.getUsername()))) {
            throw new IllegalArgumentException("Customer does not own the wishlist!");
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
