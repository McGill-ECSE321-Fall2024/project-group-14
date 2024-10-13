package ca.mcgill.ecse321_group14.GameShop.repository;


import ca.mcgill.ecse321_group14.GameShop.model.Promotion;
import org.springframework.data.repository.CrudRepository;

public interface PromotionRepository extends CrudRepository<Promotion, Integer> {
    Promotion findPromotionById(int id);
}
