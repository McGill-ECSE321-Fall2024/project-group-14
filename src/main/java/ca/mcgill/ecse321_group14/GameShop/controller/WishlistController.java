package ca.mcgill.ecse321_group14.GameShop.controller;

import ca.mcgill.ecse321_group14.GameShop.dto.WishlistDto;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Wishlist;
import ca.mcgill.ecse321_group14.GameShop.service.CustomerService;
import ca.mcgill.ecse321_group14.GameShop.service.GameService;
import ca.mcgill.ecse321_group14.GameShop.service.WishlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class WishlistController {
    @Autowired
    WishlistService wishlistService;
    @Autowired
    GameService gameServivce;
    @Autowired
    CustomerService customerService;

    @GetMapping("/wishlist/{gameId}/{customerId}")
    public ResponseEntity<WishlistDto> getWishlist(@PathVariable("gameId") int gameId, @PathVariable("customerId") int customerId) {

        Game game = gameServivce.getGameById(gameId);
        Customer customer = customerService.getCustomerById(customerId);
        try {
            Wishlist wishlist = wishlistService.getWishlist(game, customer);
            return new ResponseEntity<>(new WishlistDto(wishlist), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new WishlistDto(),HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/wishlist/{gameId}/{customerId}")
    public ResponseEntity<WishlistDto> addGameToWishlist(@PathVariable("gameId") int gameId, @PathVariable("customerId") int customerId) {
        Game game = gameServivce.getGameById(gameId);
        Customer customer = customerService.getCustomerById(customerId);
        try {
            Wishlist wishlist = wishlistService.addGameToWishlist(game, customer);
            return new ResponseEntity<>(new WishlistDto(wishlist), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new WishlistDto(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/wishlist/{gameId}/{customerId}")
    public ResponseEntity<WishlistDto> deleteGameFromWishlist(@PathVariable("gameId") int gameId, @PathVariable("customerId") int customerId) {
        Game game = gameServivce.getGameById(gameId);
        Customer customer = customerService.getCustomerById(customerId);
        try {
            Wishlist wishlist = wishlistService.deleteGameFromWishlist(game, customer);
            return new ResponseEntity<>(new WishlistDto(wishlist), HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new WishlistDto(),HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/wishlist/{customerId}/{gameId}")
    public ResponseEntity<?> clearWishlist(@PathVariable("customerId") int customerId, @PathVariable("gameId") int gameId) {
        Customer customer = customerService.getCustomerById(customerId);
        Game game = gameServivce.getGameById(gameId);
        try {
            wishlistService.clearWishlist(customer, game);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new WishlistDto(),HttpStatus.NOT_FOUND);
        }
    }

}
