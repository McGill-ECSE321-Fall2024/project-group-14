package ca.mcgill.ecse321_group14.GameShop.dto;

public class ReplyRequestDto {
    private int id;
    private String message;
    private int managerId;

    public ReplyRequestDto() {
    }

    public ReplyRequestDto(int id, String message, int managerId) {
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