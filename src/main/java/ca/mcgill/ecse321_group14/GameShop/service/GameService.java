package ca.mcgill.ecse321_group14.GameShop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ca.mcgill.ecse321_group14.GameShop.model.Employee;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Manager;
import ca.mcgill.ecse321_group14.GameShop.model.Person;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import jakarta.transaction.Transactional;

@Service
public class GameService {
    @Autowired
    GameRepository gameRepository;

    /**
     * createGame: creating a new game entry
     * @param aName: name of the game
     * @param aDescription: description of the game
     * @param aCategory: category of the game
     * @param aPrice: price of the game
     * @param aQuantity: quantity available for the game
     * @param aRating: rating of the game
     * @param aPicture: URL or path of the game picture
     * @return the created game object
     *
     * @throws IllegalArgumentException if any field is null or invalid, or if the game already exists
     */

    @Transactional
    public Game createGame(String aName, String aDescription, String aCategory, int aPrice, int aQuantity, Game.Rating aRating, String aPicture) {
        if (aName == null || aDescription == null || aCategory == null || aPrice < 0 || aQuantity < 0 || aRating == null || aPicture == null) {
            throw new IllegalArgumentException("All fields must be filled!");
            }
        if (gameRepository.findGameByName(aName) != null) {
            throw new IllegalArgumentException("Game already exists!");
        }
        Game game = new Game(aName, aDescription, aCategory, aPrice, aQuantity, aRating, aPicture);
        return gameRepository.save(game);

    }

    /**
     * updateGame: updating an existing game entry
     * @param id: ID of the game to be updated
     * @param aName: updated name of the game
     * @param aDescription: updated description of the game
     * @param aCategory: updated category of the game
     * @param aPrice: updated price of the game
     * @param aQuantity: updated quantity available for the game
     * @param aRating: updated rating of the game
     * @param aPicture: updated URL or path of the game picture
     * @return the updated game object
     *
     * @throws IllegalArgumentException if any field is null or invalid, or if the game does not exist
     */
    @Transactional
    public Game updateGame(int id, String aName, String aDescription, String aCategory, int aPrice, int aQuantity, Game.Rating aRating, String aPicture) {
        if (aName == null || aDescription == null || aCategory == null || aPrice < 0 || aQuantity < 0 || aRating == null || aPicture == null) {
            throw new IllegalArgumentException("All fields must be filled!");
        }
        Game game = gameRepository.findGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("Game does not exist!");
        }
        game.setName(aName);
        game.setDescription(aDescription);
        game.setCategory(aCategory);
        game.setPrice(aPrice);
        game.setQuantity(aQuantity);
        game.setRating(aRating);
        game.setPicture(aPicture);
        return gameRepository.save(game);
    }

    /**
     * getGameById: getting a game by its ID
     * @param id: ID of the game
     * @return the game object
     *
     * @throws IllegalArgumentException if the ID is null or if the game does not exist
     */
    @Transactional
    public Game getGameById(int id){
        if (id < 0) {
            throw new IllegalArgumentException("ID cannot be null!");
        }
        Game game = gameRepository.findGameById(id);
        if (game == null) {
            throw new IllegalArgumentException("Game does not exist!");
        }
        return game;
    }

    @Transactional
    public Game getGameByName(String aName){
        if (aName == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
        Game game = gameRepository.findGameByName(aName);
        if (game == null) {
            throw new IllegalArgumentException("Game does not exist!");
        }
        return game;
    }

    @Transactional
    public List<Game> getAllGames(){
        List<Game> games = gameRepository.findAll();
        if (games.isEmpty()) {
            throw new IllegalArgumentException("No games found!");
        }
        return games;
    }

    @Transactional
    public void deleteGame(String aName, Person person) {
        if (aName == null) {
            throw new IllegalArgumentException("Name cannot be null!");
        }
        Game game = gameRepository.findGameByName(aName);
        if (game == null) {
            throw new IllegalArgumentException("Game does not exist!");
        }
        if (!(person instanceof Employee) || !(person instanceof Manager)){
            throw new IllegalArgumentException("Only Staff can delete games!");
        }
        gameRepository.deleteGameByName(aName);
    }
}

