package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Game;

import java.util.List;

public class GameListDtos {
    // Default constructor
    public GameListDtos() {
    }

    private List<GameResponseDto> games;

    public GameListDtos(List<GameResponseDto> games) {
        this.games = games;
    }

    public List<GameResponseDto> getGames() {
        return games;
    }

    public void setGames(List<GameResponseDto> games) {
        this.games = games;
    }
}
