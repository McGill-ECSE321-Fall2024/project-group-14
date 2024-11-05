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
    public Review createReview(Review.Ranking aRanking, String aDescription, Customer aCustomer, Game aGame) {
        String error = "";
        if (customerRepository.findCustomerById(aCustomer.getId()) == null) {
            error += "Customer does not exist!";
        }
        if (gameRepository.findGameById(aGame.getId()) == null) {
            error += "Game does not exist!";
        }
        if (!error.isEmpty()) {
            throw new IllegalArgumentException(error);
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
    public Review updateReview(int id, Review.Ranking aRanking, String aDescription, Customer aCustomer, Game aGame) {
        Review review = reviewRepository.findReviewById(id);
        String error = "";
        if (review == null) {
            error += "Review does not exist!";
        }
        if (customerRepository.findCustomerById(aCustomer.getId()) == null) {
            error += "Customer does not exist!";
        }
        if (gameRepository.findGameById(aGame.getId()) == null) {
            error += "Game does not exist!";
        }
        if (!error.isEmpty()) {
            throw new IllegalArgumentException(error);
        }
        try{
            review.setRanking(aRanking);
            review.setDescription(aDescription);
            review.setCustomer(aCustomer);
            review.setGame(aGame);
            reviewRepository.save(review);
            return review;
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }

    }
}
