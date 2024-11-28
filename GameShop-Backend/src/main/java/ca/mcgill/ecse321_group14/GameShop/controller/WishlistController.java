package ca.mcgill.ecse321_group14.GameShop.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.mcgill.ecse321_group14.GameShop.dto.WishlistDto;
import ca.mcgill.ecse321_group14.GameShop.dto.WishlistListDto;
import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Wishlist;
import ca.mcgill.ecse321_group14.GameShop.service.CustomerService;
import ca.mcgill.ecse321_group14.GameShop.service.GameService;
import ca.mcgill.ecse321_group14.GameShop.service.WishlistService;

@CrossOrigin(origins = "*")
@RestController
public class WishlistController {
    @Autowired
    WishlistService wishlistService;
    @Autowired
    GameService gameServivce;
    @Autowired
    CustomerService customerService;

    @PostMapping("/wishlist/{gameId}/{customerId}")
    public ResponseEntity<WishlistDto> createWishlist(@PathVariable("gameId") int gameId, @PathVariable("customerId") int customerId) {
        try {
            Game game = gameServivce.getGameById(gameId);
            Customer customer = customerService.getCustomerById(customerId);

            Wishlist wishlist = wishlistService.createWishlist(game, customer);
            return new ResponseEntity<>(new WishlistDto(wishlist), HttpStatus.CREATED);
        }
        catch (IllegalArgumentException e) {
            return new ResponseEntity<>(new WishlistDto(), HttpStatus.BAD_REQUEST);
        }
    }

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

    @DeleteMapping("/wishlist/{gameId}/{customerId}")
    public ResponseEntity<Void> clearWishlist(@PathVariable("gameId") int gameId, @PathVariable("customerId") int customerId) {
        try {
            Game game = gameServivce.getGameById(gameId);
            Customer customer = customerService.getCustomerById(customerId);
            wishlistService.clearWishlist(customer, game);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
             // Returns 204 NO_CONTENT if successful
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 if wishlist not found
        }
    }

    @GetMapping("/wishlist/{customerId}")
    public ResponseEntity<WishlistListDto> getWishlistByCustomerId(@PathVariable("customerId") int customerId) {
        try {
            List<Wishlist> wishlists = wishlistService.findWishlistsByCustomerId(customerId);
            List<WishlistDto> wishlistDtos = wishlists.stream()
                    .map(WishlistDto::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new WishlistListDto(wishlistDtos)); // Returns 200 OK with list of wishlists
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Returns 404 if customerId not found
        }
    }

}
