package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.GameApprovalRequest.Status;

public class GameApprovalRequestDto {

    private int id;
    private String name;
    private String description;
    private String category;
    private String picture;
    private Status status;
    private Integer requestCreatorId;
    private Integer requestApproverId;

    // Default constructor
    public GameApprovalRequestDto() {}

    // Parameterized constructor
    public GameApprovalRequestDto(int id, String name, String description, String category, String picture,
                                  Status status, Integer requestCreatorId, Integer requestApproverId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.category = category;
        this.picture = picture;
        this.status = status;
        this.requestCreatorId = requestCreatorId;
        this.requestApproverId = requestApproverId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getRequestCreatorId() {
        return requestCreatorId;
    }

    public void setRequestCreatorId(Integer requestCreatorId) {
        this.requestCreatorId = requestCreatorId;
    }

    public Integer getRequestApproverId() {
        return requestApproverId;
    }

    public void setRequestApproverId(Integer requestApproverId) {
        this.requestApproverId = requestApproverId;
    }

}
