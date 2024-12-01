package ca.mcgill.ecse321_group14.GameShop.dto;

import java.util.List;
import ca.mcgill.ecse321_group14.GameShop.model.Review;

public class ReviewListDtos {
    // Default constructor
    public ReviewListDtos() {
    }

    private List<ReviewResponseDto> reviews;

    public ReviewListDtos(List<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }

    public List<ReviewResponseDto> getReviews() {
        return reviews;
    }

    public void setReviews(List<ReviewResponseDto> reviews) {
        this.reviews = reviews;
    }
}
