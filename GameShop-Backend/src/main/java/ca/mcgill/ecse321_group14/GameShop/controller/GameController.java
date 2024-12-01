package ca.mcgill.ecse321_group14.GameShop.controller;

import ca.mcgill.ecse321_group14.GameShop.dto.GameRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.GameResponseDto;
import ca.mcgill.ecse321_group14.GameShop.dto.ReviewResponseDto;
import ca.mcgill.ecse321_group14.GameShop.dto.GameListDtos;
import ca.mcgill.ecse321_group14.GameShop.dto.ReviewListDtos;
import ca.mcgill.ecse321_group14.GameShop.dto.GameResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.service.GameService;
import ca.mcgill.ecse321_group14.GameShop.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
public class GameController {
    @Autowired
    private GameService gameService;
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/game")
    public GameResponseDto createGame(@RequestBody GameRequestDto gameRequestDto) {
        Game game = gameService.createGame(gameRequestDto.getName(), gameRequestDto.getDescription(),
                gameRequestDto.getCategory(), gameRequestDto.getPrice(), gameRequestDto.getQuantity(),
                gameRequestDto.getRating(), gameRequestDto.getPicture());
        return new GameResponseDto(game);
    }

    @PostMapping("/game/updatebyid")
    public GameResponseDto updateGame(@RequestBody GameRequestDto gameRequestDto) {
        Game game = gameService.updateGameById(gameRequestDto.getId(), gameRequestDto.getName(),
                gameRequestDto.getDescription(), gameRequestDto.getCategory(), gameRequestDto.getPrice(),
                gameRequestDto.getQuantity(), gameRequestDto.getRating(), gameRequestDto.getPicture());
        return new GameResponseDto(game);
    }

    @GetMapping("/game/{id}")
    public GameResponseDto getGameById(@PathVariable int id) {
        Game game = gameService.getGameById(id);
        return new GameResponseDto(game);
    }

    @GetMapping("/games")
    public GameListDtos getAllGames() {
        List<GameResponseDto> dtos = new ArrayList<GameResponseDto>();
        for (Game game : gameService.getAllGames()) {
            dtos.add(new GameResponseDto(game));
        }
        return new GameListDtos(dtos);
    }

    @DeleteMapping("/game")
    public ResponseEntity<Void> deleteGame(@RequestBody GameRequestDto gameRequestDto) {
        try {
            // Attempt to delete the game
            gameService.deleteGame(gameRequestDto.getName(), gameRequestDto.getPersonId());

            // Return 204 No Content if deletion is successful
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        } catch (IllegalArgumentException e) {
            // Log the exception message (optional)
            System.out.println(e.getMessage());

            // Return 404 Not Found if the game does not exist
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/game/{id}/reviews")
    public ReviewListDtos getReviewsForGame(@PathVariable int id) {
        List<ReviewResponseDto> dtos = new ArrayList<ReviewResponseDto>();
        for (Review review : reviewService.getAllReviews()) {
            if (review.getGame().getId() == id) {
                dtos.add(new ReviewResponseDto(review));
            }
        }
        return new ReviewListDtos(dtos);
    }

}
