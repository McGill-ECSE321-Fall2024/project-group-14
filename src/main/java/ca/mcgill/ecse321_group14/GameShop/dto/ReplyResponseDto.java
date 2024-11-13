package ca.mcgill.ecse321_group14.GameShop.dto;

public class ReplyResponseDto {
    private Long id;
    private String message;
    private String author;
    private String timestamp;

    public ReplyResponseDto() {
    }

    public ReplyResponseDto(Long id, String message, String author, String timestamp) {
        this.id = id;
        this.message = message;
        this.author = author;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}