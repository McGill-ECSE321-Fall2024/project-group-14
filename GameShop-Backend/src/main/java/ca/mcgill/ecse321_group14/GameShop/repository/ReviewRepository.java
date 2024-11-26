package ca.mcgill.ecse321_group14.GameShop.repository;

import ca.mcgill.ecse321_group14.GameShop.model.Review;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
    Review findReviewById(int id);
}
