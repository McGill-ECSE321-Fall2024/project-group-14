package ca.mcgill.ecse321_group14.GameShop.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321_group14.GameShop.model.Game;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Integer> {
    Game findGameById(int id);

    Game findGameByName(String aName);
    List<Game> findAll();

    void deleteGameByName(String aName);
}
