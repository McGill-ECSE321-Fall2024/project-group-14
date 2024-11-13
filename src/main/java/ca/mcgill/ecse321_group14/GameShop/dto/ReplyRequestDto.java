package ca.mcgill.ecse321_group14.GameShop.dto;

public class ReplyRequestDto{
    private String description;
    private int reviewId;
    private int managerId;

    public ReplyRequestDto() {
    }

    public ReplyRequestDto(String description, int reviewId, int managerId) {
        this.description = description;
        this.reviewId = reviewId;
        this.managerId = managerId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}