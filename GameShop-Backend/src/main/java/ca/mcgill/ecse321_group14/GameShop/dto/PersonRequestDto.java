package ca.mcgill.ecse321_group14.GameShop.dto;

import ca.mcgill.ecse321_group14.GameShop.model.Person;

public class PersonRequestDto {
    private int id;
    private String username;
    private String email;
    private String password;

    public PersonRequestDto() {
    }
    public PersonRequestDto(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public PersonRequestDto(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public PersonRequestDto(Person person){
        this.id = person.getId();
        this.username = person.getUsername();
        this.email = person.getEmail();
        this.password = person.getPassword();
    }
    public int getPersonId() {
        return id;
    }
    public void setPersonId(int id) {
        this.id = id;
    }
    public String getPersonUsername() {
        return username;
    }
    public void setPersonUsername(String username) {
        this.username = username;
    }
    public String getPersonEmail() {
        return email;
    }
    public void setPersonEmail(String email) {
        this.email = email;
    }
    public String getPersonPassword() {
        return password;
    }
    public void setPersonPassword(String password) {
        this.password = password;
    }
}
