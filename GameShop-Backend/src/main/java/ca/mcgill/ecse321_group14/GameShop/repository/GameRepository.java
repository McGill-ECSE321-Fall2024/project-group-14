package ca.mcgill.ecse321_group14.GameShop.repository;
import org.springframework.data.repository.CrudRepository;
import ca.mcgill.ecse321_group14.GameShop.model.Game;

public interface GameRepository extends CrudRepository<Game, Integer> {
    Game findGameById(int id);   
}
