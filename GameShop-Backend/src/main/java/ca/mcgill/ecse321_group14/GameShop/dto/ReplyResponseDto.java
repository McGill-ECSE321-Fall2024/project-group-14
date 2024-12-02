package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Reply;

public class ReplyResponseDto {
    private int replyId;
    private int id;
    private String message;
    private int managerId;

    public ReplyResponseDto() {
    }

    public ReplyResponseDto(int replyId, int id, String message, int managerId) {
        this.replyId = replyId;
        this.message = message;
        this.managerId = managerId;
        this.id = id; // review id
    }

    public ReplyResponseDto(Reply reply) {

        this.replyId = reply.getId();
        this.id = reply.getReview().getId();
        this.message = reply.getDescription();
        this.managerId = reply.getManager().getId();
    }

    public int getId() {
        return replyId;
    }

    public int getReviewId() {
        return id;
    }

    public void setId(int replyId) {
        this.replyId = replyId;
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