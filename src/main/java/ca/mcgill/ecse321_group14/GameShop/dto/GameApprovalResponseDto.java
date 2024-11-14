package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.GameApprovalRequest.Status;

public class GameApprovalResponseDto {
    private int id;
    private String name;
    private String description;
    private String category;
    private String picture;
    private Status status;
    private Integer requestCreatorId;
    private Integer requestApproverId;

    // Constructor to populate response fields from model data
    public GameApprovalResponseDto(int id, String name, String description, String category, String picture,
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

    public GameApprovalResponseDto(String name, String description, String category, String picture,
                                          Status status, Integer requestCreatorId, Integer requestApproverId) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.picture = picture;
        this.status = status;
        this.requestCreatorId = requestCreatorId;
        this.requestApproverId = requestApproverId;
    }

    public GameApprovalResponseDto() {}

    // Getters only (setters not usually needed for response DTOs)
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public String getPicture() { return picture; }
    public Status getStatus() { return status; }
    public Integer getRequestCreatorId() { return requestCreatorId; }
    public Integer getRequestApproverId() { return requestApproverId; }
}
