package ca.mcgill.ecse321_group14.GameShop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.repository.ManagerRepository;
import ca.mcgill.ecse321_group14.GameShop.service.GameService;
import ca.mcgill.ecse321_group14.GameShop.service.ManagerService;

@SpringBootApplication
public class GameShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(GameShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner demoData(@Autowired ManagerService managerService, @Autowired GameService gameService , @Autowired ManagerRepository managerRepository) {
        return (args) -> {
			if (managerRepository.count() > 0) {
				return;
			}
            // Create a manager account
            managerService.createManager("manager", "manager@gameshop.com", "password");

            // Create the first game
            gameService.createGame("Mario", "A fun game", "Action", 69, 200, Game.Rating.PG, "mario.png");

            // Create the second game
            gameService.createGame("Zelda", "A fun game", "Action", 69, 200, Game.Rating.PG, "zelda.png");
        };
    }
}
