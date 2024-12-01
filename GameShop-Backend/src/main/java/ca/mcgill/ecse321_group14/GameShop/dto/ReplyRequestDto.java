package ca.mcgill.ecse321_group14.GameShop.dto;

public class ReplyRequestDto {
    private String message;
    private int id; // reviewId
    private int managerId;

    public ReplyRequestDto() {
    }

    public ReplyRequestDto(int id, String message, int managerId) {
        if (message == null) {
            message = "No description provided.";
        }
        if (id == 0) {
            id = 4;
        }
        this.message = message;
        this.id = id;
        this.managerId = managerId;
    }

    public String getDescription() {
        return message;
    }

    public void setDescription(String description) {
        this.message = description;
    }

    public int getReviewId() {
        return id;
    }

    public void setReviewId(int id) {
        this.id = id;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }
}