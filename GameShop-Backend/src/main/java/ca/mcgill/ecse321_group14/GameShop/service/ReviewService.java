package ca.mcgill.ecse321_group14.GameShop.service;

import ca.mcgill.ecse321_group14.GameShop.model.Customer;
import ca.mcgill.ecse321_group14.GameShop.model.Game;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.repository.CustomerRepository;
import ca.mcgill.ecse321_group14.GameShop.repository.GameRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ca.mcgill.ecse321_group14.GameShop.repository.ReviewRepository;

@Service
public class ReviewService {

    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    GameRepository gameRepository;


    @Transactional
    public Review createReview(Review.Ranking aRanking, String aDescription, int customerId, int gameId) {
        if (aDescription == null || aDescription.trim().length() == 0) {
            throw new IllegalArgumentException("Description cannot be empty!");
        }

        if (aRanking == null) {
            throw new IllegalArgumentException("Ranking cannot be empty!");
        }


        Customer aCustomer = customerRepository.findCustomerById(customerId);
        if (aCustomer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }
        Game aGame = gameRepository.findGameById(gameId);
        if (aGame == null) {
            throw new IllegalArgumentException("Game does not exist!");
        }


        Review review = new Review(aRanking, aDescription, aCustomer, aGame);
        reviewRepository.save(review);
        return review;
    }

    @Transactional
    public void deleteReview(int id) {
        if (!reviewRepository.existsById(id)) {
            throw new IllegalArgumentException("Review does not exist!");
        }
        reviewRepository.deleteById(id);
    }

    @Transactional
    public Review getReview(int id) {
        Review review = reviewRepository.findReviewById(id);
        if (review == null) {
            throw new IllegalArgumentException("Review does not exist!");
        }
        return review;
    }

    @Transactional
    public Review updateReview(int id, Review.Ranking aRanking, String aDescription, int customerId, int gameId) {
        Review review = reviewRepository.findReviewById(id);

        if (review == null) {
            throw new IllegalArgumentException("Review does not exist!");
        }

        Customer aCustomer = customerRepository.findCustomerById(customerId);
        Game aGame = gameRepository.findGameById(gameId);
        if (aRanking == null) {
            throw new IllegalArgumentException("Ranking cannot be empty!");
        }
        if (aDescription == null || aDescription.trim().length() == 0) {
            throw new IllegalArgumentException("Description cannot be empty!");
        }

        if (aCustomer == null) {
            throw new IllegalArgumentException("Customer does not exist!");
        }

        if (aGame == null) {
            throw new IllegalArgumentException("Game does not exist!");
        }
        
        try {
            review.setRanking(aRanking);
            review.setDescription(aDescription);
            review.setCustomer(aCustomer);
            review.setGame(aGame);
            reviewRepository.save(review);
            return review;
        } catch (Exception e) {
            String errorMessage = "Error updating review: " + e.getMessage();
            throw new IllegalArgumentException(errorMessage, e);
        }

    }
}
