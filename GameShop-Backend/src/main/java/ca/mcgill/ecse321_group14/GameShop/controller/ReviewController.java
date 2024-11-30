package ca.mcgill.ecse321_group14.GameShop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ReviewResponseDto> createReview(@RequestBody ReviewRequestDto reviewRequestDto) {
        Review review = reviewService.createReview(reviewRequestDto.getRanking(), reviewRequestDto.getDescription(), reviewRequestDto.getCustomerId(), reviewRequestDto.getGameId());
        return new ResponseEntity<>(new ReviewResponseDto(review), HttpStatus.CREATED);
    }

    /**
     * Get a review by id
     * 
     * @param id
     * @return ReviewResponseDto
     */
    @GetMapping("/review/{id}")
    public ResponseEntity<ReviewResponseDto> getReview(@PathVariable int id) {
        Review review = reviewService.getReview(id);
        return new ResponseEntity<>(new ReviewResponseDto(review), HttpStatus.OK);
    }

    /**
     * Delete a review by id
     * 
     * @param id
     */
    @GetMapping("/review/delete/{id}")
    public ResponseEntity<Void>  deleteReview(@PathVariable int id) {

        reviewService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}