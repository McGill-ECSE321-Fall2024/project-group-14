package ca.mcgill.ecse321_group14.GameShop.controller;

import ca.mcgill.ecse321_group14.GameShop.dto.GameDto;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping("/game")
    public Game createGame(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String category,
            @RequestParam int price,
            @RequestParam int quantity,
            @RequestParam Game.Rating rating,
            @RequestParam String picture) {

        return gameService.createGame(name, description, category, price, quantity, rating, picture);
    }

    @PostMapping("/game/updatebyid")
    public Game updateGame(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String category,
            @RequestParam int price,
            @RequestParam int quantity,
            @RequestParam Game.Rating rating,
            @RequestParam String picture) {

        return gameService.updateGameById(id, name, description, category, price, quantity, rating, picture);
    }

    @GetMapping("/game/{id}")
    public Game getGameById(@PathVariable int id) {
        return gameService.getGameById(id);
    }

    @GetMapping("/games")
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }
}
