package ca.mcgill.ecse321_group14.GameShop.dto;
import ca.mcgill.ecse321_group14.GameShop.model.Reply;

public class ReplyResponseDto {
    private int id;
    private String message;
    private String author;

    public ReplyResponseDto() {
    }

    public ReplyResponseDto(int id, String message, String author) {
        this.id = id;
        this.message = message;
        this.author = author;
    }

    public ReplyResponseDto(Reply reply) {

        this.id = reply.getId();
        this.message = reply.getDescription();
        this.author = reply.getManager().getUsername();
    }

    public int getId() {
        return id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}