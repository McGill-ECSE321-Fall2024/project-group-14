package ca.mcgill.ecse321_group14.GameShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ca.mcgill.ecse321_group14.GameShop.dto.ReviewRequestDto;
import ca.mcgill.ecse321_group14.GameShop.dto.ReviewResponseDto;
import ca.mcgill.ecse321_group14.GameShop.model.Review;
import ca.mcgill.ecse321_group14.GameShop.service.ReviewService;




@RestController
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;

    /**
     * Create a review
     * 
     * @param reviewRequestDto
     * @return ReviewResponseDto
     */
    @PostMapping("/review")
    public ReviewResponseDto createReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        Review review = reviewService.createReview(reviewRequestDto.getRanking(), reviewRequestDto.getDescription(), reviewRequestDto.getCustomerId(), reviewRequestDto.getGameId());
        return new ReviewResponseDto(review);
    }

    /**
     * Get a review by id
     * 
     * @param id
     * @return ReviewResponseDto
     */
    @GetMapping("/review/{id}")
    public ReviewResponseDto getReview(@PathVariable int id) {
        Review review = reviewService.getReview(id);
        return new ReviewResponseDto(review);
    }

    /**
     * Delete a review by id
     * 
     * @param id
     */
    @GetMapping("/review/delete/{id}")
    public void deleteReview(@PathVariable int id) {
        reviewService.deleteReview(id);
    }

}