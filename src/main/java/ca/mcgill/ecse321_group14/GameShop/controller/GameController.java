package ca.mcgill.ecse321_group14.GameShop.controller;

import ca.mcgill.ecse321_group14.GameShop.dto.GameRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.GameResponseDto;
import ca.mcgill.ecse321_group14.GameShop.dto.GameListDtos;
import ca.mcgill.ecse321_group14.GameShop.dto.GameResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
public class GameController {
    @Autowired
    private GameService gameService;

    @PostMapping("/game")
    public GameResponseDto createGame(@RequestBody GameRequestDto gameRequestDto) {
        Game game = gameService.createGame(gameRequestDto.getName(), gameRequestDto.getDescription(), gameRequestDto.getCategory(), gameRequestDto.getPrice(), gameRequestDto.getQuantity(), gameRequestDto.getRating(), gameRequestDto.getPicture());
        return new GameResponseDto(game);
    }

    @PostMapping("/game/updatebyid")
    public GameResponseDto updateGame(@RequestBody GameRequestDto gameRequestDto) {
        Game game = gameService.updateGameById(gameRequestDto.getId(), gameRequestDto.getName(), gameRequestDto.getDescription(), gameRequestDto.getCategory(), gameRequestDto.getPrice(), gameRequestDto.getQuantity(), gameRequestDto.getRating(), gameRequestDto.getPicture());
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
    public void deleteGame(@RequestBody GameRequestDto gameRequestDto) {
        gameService.deleteGame(gameRequestDto.getName(), gameRequestDto.getPersonId());
    }
}
