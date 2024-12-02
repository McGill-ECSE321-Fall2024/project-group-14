package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Reply;

public class ReplyResponseDto {
    private int id;
    private int reviewId;
    private String message;
    private int managerId;

    public ReplyResponseDto() {
    }

    public ReplyResponseDto(int id, int reviewId, String message, int managerId) {
        this.id = id;
        this.message = message;
        this.managerId = managerId;
        this.reviewId = reviewId;
    }

    public ReplyResponseDto(Reply reply) {

        this.id = reply.getId();
        this.reviewId = reply.getReview().getId();
        this.message = reply.getDescription();
        this.managerId = reply.getManager().getId();
    }

    public int getId() {
        return id;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}